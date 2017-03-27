/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.bce.mkt.iac.common.config.ConfigProperties;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.model.RoleMenu;
import com.baidu.bce.mkt.iac.common.model.db.Account;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/24 by sunfangyuan@baidu.com .
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {
    private final AccountMapper accountMapper;
    private final ConfigProperties configProperties;

    public RoleMenu getShowMenu(String bceUserId) {
        Account account = accountMapper.getByAccountId(bceUserId);
        if (account == null) {
            return RoleMenu.USER;
        } else {
            RoleMenu roleMenu = RoleMenu.getRoleMenu(account.getRole());
            if (!configProperties.getShowProduct()) {
                roleMenu.getMenuShowModel().setShowProduct(false);
            }
            return roleMenu;
        }
    }
}
