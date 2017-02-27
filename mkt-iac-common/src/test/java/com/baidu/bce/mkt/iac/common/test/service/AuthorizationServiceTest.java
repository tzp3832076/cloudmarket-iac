// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.client.model.CheckInstanceOwnerRequest;
import com.baidu.bce.mkt.iac.common.client.model.CheckInstanceOwnerResponse;
import com.baidu.bce.mkt.iac.common.model.AuthorizeCommand;
import com.baidu.bce.mkt.iac.common.model.UserIdentity;
import com.baidu.bce.mkt.iac.common.service.AuthorizationService;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;
import com.baidu.bce.plat.webframework.exception.BceException;

/**
 * authorization service test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class AuthorizationServiceTest extends BaseCommonServiceTest {
    @Autowired
    private AuthorizationService authorizationService;

    @Test
    public void testAuthorizeNormalUserSuccess() {
        AuthorizeCommand authorizeCommand = new AuthorizeCommand("test", "normal_vendor_1",
                null, false, null, "audit",
                "read", null);
        UserIdentity userIdentity = authorizationService.authorize(authorizeCommand);
        Assert.assertEquals("normal_vendor_1", userIdentity.getUserId());
        Assert.assertEquals("vendor111", userIdentity.getVendorId());
        Assert.assertEquals("VENDOR", userIdentity.getRole());
    }

    @Test
    public void testAuthorizeOpUserSuccess() {
        AuthorizeCommand authorizeCommand = new AuthorizeCommand("test", "test",
                null, true, "op_user_1", "audit",
                "read", null);
        UserIdentity userIdentity = authorizationService.authorize(authorizeCommand);
        Assert.assertEquals("op_user_1", userIdentity.getUserId());
        Assert.assertNull(userIdentity.getVendorId());
        Assert.assertEquals("OP", userIdentity.getRole());
    }

    @Test
    public void testAuthorizeNormalUserWithResourceSuccess() {
        AuthorizeCommand authorizeCommand = new AuthorizeCommand("test", "normal_vendor_1",
                null, false, null, "audit",
                "read", Arrays.asList("audit_id_1"));
        when(authClient.checkInstanceOwner(any(CheckInstanceOwnerRequest.class)))
                .thenReturn(new CheckInstanceOwnerResponse(true));
        UserIdentity userIdentity = authorizationService.authorize(authorizeCommand);
        Assert.assertEquals("normal_vendor_1", userIdentity.getUserId());
        Assert.assertEquals("vendor111", userIdentity.getVendorId());
        Assert.assertEquals("VENDOR", userIdentity.getRole());
    }

    @Test
    public void testAuthorizeOpUserWithResourceSuccess() {
        AuthorizeCommand authorizeCommand = new AuthorizeCommand("test", "test",
                null, true, "op_user_1", "audit",
                "read", Arrays.asList("audit_id_1"));
        UserIdentity userIdentity = authorizationService.authorize(authorizeCommand);
        Assert.assertEquals("op_user_1", userIdentity.getUserId());
        Assert.assertNull(userIdentity.getVendorId());
        Assert.assertEquals("OP", userIdentity.getRole());
        Assert.assertTrue(outputCapture.toString()
                .contains("current user has op role, check resource instance pass directly"));
    }

    @Test
    public void testAuthorizeNormalUserFailed() {
        AuthorizeCommand authorizeCommand = new AuthorizeCommand("test", "normal_user_not_exists",
                null, false, null, "audit",
                "read", null);
        try {
            authorizationService.authorize(authorizeCommand);
            Assert.fail();
        } catch (BceException e) {
            Assert.assertEquals("NoPermission", e.getCode());
            Assert.assertTrue(outputCapture.toString()
                    .contains("reject by role permission"));
        }
    }

    @Test
    public void testAuthorizeNormalUserWithResourceFailed() {
        AuthorizeCommand authorizeCommand = new AuthorizeCommand("test", "normal_vendor_1",
                null, false, null, "audit",
                "read", Arrays.asList("audit_id_1"));
        when(authClient.checkInstanceOwner(any(CheckInstanceOwnerRequest.class)))
                .thenReturn(new CheckInstanceOwnerResponse(false));
        try {
            authorizationService.authorize(authorizeCommand);
            Assert.fail();
        } catch (BceException e) {
            Assert.assertEquals("NoPermission", e.getCode());
            Assert.assertTrue(outputCapture.toString()
                    .contains("user is not owner of resource = audit and instances = [audit_id_1]"));
        }
    }

    @Test
    public void testAuthorizeOpUserFailed() {
        AuthorizeCommand authorizeCommand = new AuthorizeCommand("test", "test",
                null, true, "op_user_1", "order",
                "create", null);
        try {
            authorizationService.authorize(authorizeCommand);
            Assert.fail();
        } catch (BceException e) {
            Assert.assertEquals("NoPermission", e.getCode());
            Assert.assertTrue(outputCapture.toString()
                    .contains("reject by role permission"));
        }
    }
}
