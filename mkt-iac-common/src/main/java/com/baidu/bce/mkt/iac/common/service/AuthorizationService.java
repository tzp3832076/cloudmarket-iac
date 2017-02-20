// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.mapper.RolePermissionMapper;
import com.baidu.bce.mkt.iac.common.model.CurrentForAuthUser;
import com.baidu.bce.mkt.iac.common.model.db.Account;

/**
 * authorization service
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class AuthorizationService {
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    public void authorize(CurrentForAuthUser currentForAuthUser, String operation, String resource,
                          List<String> instances) {
        Account account = accountMapper.getByAccountId(currentForAuthUser.getUserId());
        if (account == null) {
            // 普通用户
        } else {
            // 特殊用户
        }
    }
}
