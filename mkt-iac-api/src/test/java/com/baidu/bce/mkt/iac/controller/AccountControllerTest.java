/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.baidu.bce.internalsdk.mkt.iac.model.GetAccountRoleResponse;
import com.baidu.bce.mkt.framework.test.iam.CurrentUser;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

/**
 * Created on 2017/3/24 by sunfangyuan@baidu.com .
 */
public class AccountControllerTest extends ApiMockMvcTest {

    @Test
    @CurrentUser(isServiceAccount = true)
    public void getAccountRole() throws Exception {
        when(accountService.getAccountRole(anyString())).thenReturn("USER");
        GetAccountRoleResponse roleResponse = mktIacClient.getAccountRole("test");
        Assert.assertEquals(roleResponse.getRole(), "USER");
    }
}