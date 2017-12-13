// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.handler.RedisHandler;
import com.baidu.bce.mkt.iac.common.handler.RemoteInstanceCheckHandler;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.mapper.RoleMapper;
import com.baidu.bce.mkt.iac.common.mapper.RolePermissionMapper;
import com.baidu.bce.mkt.iac.common.model.AuthorizeCommand;
import com.baidu.bce.mkt.iac.common.model.CurrentForAuthUser;
import com.baidu.bce.mkt.iac.common.model.OwnerInfoForRedis;
import com.baidu.bce.mkt.iac.common.model.UserIdentity;
import com.baidu.bce.mkt.iac.common.model.db.Account;
import com.baidu.bce.mkt.iac.common.model.db.PermissionAction;
import com.baidu.bce.mkt.iac.common.model.db.Role;
import com.baidu.bce.mkt.iac.common.model.db.RolePermission;
import com.baidu.bce.mkt.iac.common.service.checker.LocalInstanceChecker;

import lombok.extern.slf4j.Slf4j;

/**
 * authorization service
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
@Service
public class AuthorizationService {
    private static final long OWNER_INFO_EXPIRE_TIME = 3600 * 24;

    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RemoteInstanceCheckHandler remoteInstanceCheckHandler;
    @Autowired
    private RedisHandler redisHandler;

    private Map<String, LocalInstanceChecker> checkerMap;

    @Autowired
    public AuthorizationService(List<LocalInstanceChecker> checkers) {
        checkerMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(checkers)) {
            for (LocalInstanceChecker localInstanceChecker : checkers) {
                for (String supportResource : localInstanceChecker.supportResources()) {
                    checkerMap.put(supportResource, localInstanceChecker);
                }
            }
        }
    }

    public UserIdentity authorize(AuthorizeCommand authorizeCommand) {
        CurrentForAuthUser currentForAuthUser = new CurrentForAuthUser(authorizeCommand.getBceUserId(),
                authorizeCommand.getBceMainUserId(),
                authorizeCommand.isServiceAccount(),
                authorizeCommand.getRequestedUserId());
        String userId = currentForAuthUser.getUserId();
        Account account = accountMapper.getByAccountId(userId);
        UserIdentity userIdentity = new UserIdentity(userId, account);
        checkResourceOperation(userIdentity, authorizeCommand.getResource(), authorizeCommand.getOperation());
        checkTargetVendorList(userIdentity, authorizeCommand.getTargetVendorList());
        checkResourceInstances(userIdentity, authorizeCommand.getResource(), authorizeCommand.getInstances());
        return userIdentity;
    }

    private void checkResourceOperation(UserIdentity userIdentity, String resource, String operation) {
        Role role = roleMapper.getByRole(userIdentity.getRole());
        List<String> checkRoleList = role != null ? role.getSubRoleList() : null;
        if (CollectionUtils.isEmpty(checkRoleList)) {
            checkRoleList = Collections.singletonList(userIdentity.getRole());
        }
        List<RolePermission> rolePermissions =
                rolePermissionMapper.getByRoleListResourceOperation(checkRoleList, resource, operation);
        checkRolePermissions(rolePermissions);
    }

    private void checkRolePermissions(List<RolePermission> rolePermissions) {
        if (CollectionUtils.isEmpty(rolePermissions)) {
            log.info("reject by no role permission");
            throw MktIacExceptions.noPermission();
        }
        for (RolePermission rolePermission : rolePermissions) {
            if (PermissionAction.ALLOW != rolePermission.getAction()) {
                log.info("reject by role permission = {}", rolePermission);
                throw MktIacExceptions.noPermission();
            }
        }
    }

    private void checkTargetVendorList(UserIdentity userIdentity, List<String> targetVendorList) {
        if (CollectionUtils.isEmpty(targetVendorList)) {
            log.info("no target vendor list, skip");
            return;
        }
        if (userIdentity.hasPrivilege()) {
            log.info("current user has privilege, check target vendor list pass directly");
            return;
        }
        String vendorId = userIdentity.getVendorId();
        if (StringUtils.isBlank(vendorId)) {
            log.info("no vendor id in user identity");
            throw MktIacExceptions.noPermission();
        }
        if (targetVendorList.size() > 1 || !vendorId.equals(targetVendorList.get(0))) {
            log.info("target vendor list  = {} not valid when vendor id = {}", targetVendorList, vendorId);
            throw MktIacExceptions.noPermission();
        }
    }

    private void checkResourceInstances(UserIdentity userIdentity, String resource, List<String> instances) {
        if (!CollectionUtils.isEmpty(instances)) {
            if (userIdentity.hasPrivilege()) {
                log.info("current user has privilege, check resource instance pass directly");
                return;
            }
            Boolean result = checkResourceInstancesFromRedis(userIdentity, resource, instances);
            boolean owner = false;
            if (result == null) {
                LocalInstanceChecker localInstanceChecker = checkerMap.get(resource);
                owner = localInstanceChecker == null
                        ? remoteInstanceCheckHandler.check(userIdentity.getUserId(), userIdentity.getVendorId(),
                                resource, instances) :
                        localInstanceChecker.doCheck(userIdentity, instances);
                if (owner) {
                    saveOwnerInfoToRedis(userIdentity, resource, instances);
                }
            } else {
                owner = result;
            }
            if (!owner) {
                log.info("user is not owner of resource = {} and instances = {}", resource, instances);
                throw MktIacExceptions.noPermission();
            }
        }
    }

    private Boolean checkResourceInstancesFromRedis(UserIdentity userIdentity, String resource,
                                                    List<String> instances) {
        List<String> keys = generateResourceInstanceKeys(resource, instances);
        List<OwnerInfoForRedis> ownerList = redisHandler.multiGet(keys, OwnerInfoForRedis.class);
        if (CollectionUtils.isEmpty(ownerList)) {
            return null;
        } else {
            for (OwnerInfoForRedis ownerInfo : ownerList) {
                if (ownerInfo == null) {
                    log.info("check resource instances from redis return with null value, check skip");
                    return null;
                } else if (!ownerInfo.isOwnerEqualTo(userIdentity)) {
                    log.info("owner info not equal from redis");
                    return false;
                }
            }
            log.info("owner check success from redis");
            return true;
        }
    }

    private void saveOwnerInfoToRedis(UserIdentity userIdentity, String resource, List<String> instances) {
        List<String> keys = generateResourceInstanceKeys(resource, instances);
        redisHandler.multiSet(keys, new OwnerInfoForRedis(userIdentity), OWNER_INFO_EXPIRE_TIME);
    }

    private List<String> generateResourceInstanceKeys(String resource, List<String> instances) {
        List<String> keys = new ArrayList<>();
        for (String instance : instances) {
            keys.add(resource + "_" + instance);
        }
        return keys;
    }
}
