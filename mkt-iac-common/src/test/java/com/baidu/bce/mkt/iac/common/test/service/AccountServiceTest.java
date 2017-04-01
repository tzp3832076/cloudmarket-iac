/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.model.db.Account;
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
        Account account = accountService.getAccount("normal_vendor_1");
        Assert.assertEquals(true, account.isHostedVendor());
        Assert.assertEquals(true, account.isVendor());
        account = accountService.getAccount("XX_1");
        Assert.assertNull(account);
        account = accountService.getAccount("op_user_1");
        Assert.assertEquals(false, account.isHostedVendor());
        Assert.assertEquals(false, account.isVendor());
    }

}