// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.mapper.RolePermissionMapper;
import com.baidu.bce.mkt.iac.common.model.CurrentForAuthUser;
import com.baidu.bce.mkt.iac.common.model.db.Account;
import com.baidu.bce.mkt.iac.common.model.db.PermissionAction;
import com.baidu.bce.mkt.iac.common.model.db.RolePermission;

import lombok.extern.slf4j.Slf4j;

/**
 * authorization service
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class AuthorizationService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public void authorize(CurrentForAuthUser currentForAuthUser, String resource, String operation,
                          List<String> instances) {
        Account account = accountMapper.getByAccountId(currentForAuthUser.getUserId());
        String role = account == null ? IacConstants.DEFAULT_ROLE : account.getRole();
        RolePermission rolePermission = rolePermissionMapper.getByRoleResourceOperation(role, resource, operation);
        if (PermissionAction.ALLOW != rolePermission.getAction()) {
            log.info("reject by role permission = {}", rolePermission);
            throw MktIacExceptions.noPermission();
        }
        // TODO 继续完善
    }
}
