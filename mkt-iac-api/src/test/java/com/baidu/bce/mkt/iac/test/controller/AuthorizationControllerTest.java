// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationResponse;
import com.baidu.bce.mkt.framework.test.mvc.BaseMockMvcTest;
import com.baidu.bce.mkt.iac.common.client.model.CheckInstanceOwnerRequest;
import com.baidu.bce.mkt.iac.common.client.model.CheckInstanceOwnerResponse;
import com.baidu.bce.mkt.iac.common.model.AuthorizeCommand;
import com.baidu.bce.mkt.iac.common.model.UserIdentity;
import com.baidu.bce.mkt.iac.common.model.db.Account;
import com.baidu.bce.mkt.iac.common.model.db.AccountType;
import com.baidu.bce.mkt.iac.common.service.AuthorizationService;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

/**
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class AuthorizationControllerTest extends ApiMockMvcTest {
    @MockBean(name = "authorizationService")
    private AuthorizationService authorizationService;

    @Test
    public void testNormalUserAuthorizeSuccess() {
        String userId = "normal_vendor_1";
        Account account = new Account();
        account.setAccountId(userId);
        account.setAccountType(AccountType.BCE);
        account.setRole("VENDOR");
        account.setVendorId("vendor_1");
        UserIdentity userIdentity = new UserIdentity(userId, account);
        when(authorizationService.authorize(any(AuthorizeCommand.class))).thenReturn(userIdentity);
        AuthorizationRequest request = new AuthorizationRequest();
        AuthorizationRequest.BceToken bceToken = new AuthorizationRequest.BceToken();
        bceToken.setUsername("user");
        bceToken.setUserId("normal_vendor_1");
        bceToken.setServiceAccount(false);
        request.setBceToken(bceToken);
        request.setResource("audit");
        request.setOperation("read");
        request.setInstances(Arrays.asList("audit_1"));
        AuthorizationResponse response = mktIacAuthorizationClient.checkAuth(request);
        Assert.assertEquals("normal_vendor_1", response.getToken().getUserId());
        Assert.assertEquals("VENDOR", response.getToken().getRole());
    }
}
