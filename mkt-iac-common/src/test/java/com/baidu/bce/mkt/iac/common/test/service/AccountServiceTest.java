/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.model.RoleMenu;
import com.baidu.bce.mkt.iac.common.service.AccountService;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;

/**
 * Created on 2017/3/24 by sunfangyuan@baidu.com .
 */
public class AccountServiceTest extends BaseCommonServiceTest {
    @Autowired
    private AccountService accountService;

    @Test
    public void getAccountRole() {
        RoleMenu role = accountService.getShowMenu("normal_vendor_1");
        Assert.assertEquals(role.getMenuShowModel().isShowProduct(), true);
        Assert.assertEquals(role.getMenuShowModel().isShowVendor(), true);
        role = accountService.getShowMenu("XX_1");
        Assert.assertEquals(role.getMenuShowModel().isShowProduct(), false);
        Assert.assertEquals(role.getMenuShowModel().isShowVendor(), false);
        role = accountService.getShowMenu("op_user_1");
        Assert.assertEquals(role.getMenuShowModel().isShowProduct(), false);
        Assert.assertEquals(role.getMenuShowModel().isShowVendor(), false);
    }

}