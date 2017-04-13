// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.handler.RemoteInstanceCheckHandler;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.mapper.RolePermissionMapper;
import com.baidu.bce.mkt.iac.common.model.AuthorizeCommand;
import com.baidu.bce.mkt.iac.common.model.CurrentForAuthUser;
import com.baidu.bce.mkt.iac.common.model.UserIdentity;
import com.baidu.bce.mkt.iac.common.model.db.Account;
import com.baidu.bce.mkt.iac.common.model.db.PermissionAction;
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
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private RemoteInstanceCheckHandler remoteInstanceCheckHandler;

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
        RolePermission rolePermission =
                rolePermissionMapper.getByRoleResourceOperation(userIdentity.getRole(), resource, operation);
        if (rolePermission == null || PermissionAction.ALLOW != rolePermission.getAction()) {
            log.info("reject by role permission = {}", rolePermission);
            throw MktIacExceptions.noPermission();
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
            LocalInstanceChecker localInstanceChecker = checkerMap.get(resource);
            boolean owner = localInstanceChecker == null
                    ? remoteInstanceCheckHandler.check(userIdentity.getUserId(), userIdentity.getVendorId(),
                            resource, instances) :
                    localInstanceChecker.doCheck(userIdentity, instances);
            if (!owner) {
                log.info("user is not owner of resource = {} and instances = {}", resource, instances);
                throw MktIacExceptions.noPermission();
            }
        }
    }
}
