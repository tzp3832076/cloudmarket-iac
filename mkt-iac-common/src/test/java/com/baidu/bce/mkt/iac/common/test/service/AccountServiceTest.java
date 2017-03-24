/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        String role = accountService.getAccountRole("normal_vendor_1");
        Assert.assertEquals(role, "VENDOR");
        role = accountService.getAccountRole("XX_1");
        Assert.assertEquals(role, "USER");
    }

}