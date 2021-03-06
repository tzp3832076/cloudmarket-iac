/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baidu.bce.internalsdk.mkt.iac.model.ApplicationNoticeBody;
import com.baidu.bce.internalsdk.mkt.iac.model.AuditNoticeRequest;
import com.baidu.bce.mkt.framework.test.iam.CurrentUser;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Slf4j
public class NoticeControllerTest extends ApiMockMvcTest {

    @Test
    @CurrentUser(isServiceAccount = true)
    public void auditNotice() throws Exception {
        ApplicationNoticeBody body = new ApplicationNoticeBody();
        body.setAuditId("test");
        body.setCompany("test");
        body.setAddress("test");
        body.setBceUserId("test");
        body.setCapital(100);
        body.setContactInfo("test");
        body.setEmail("test");
        body.setHeadcount(100);
        body.setHotline("test");
        body.setMarket("test");
        body.setSale("test");
        body.setServiceCategory("test-test");
        body.setBceUserId("test");
        body.setServiceIllustration("test");
        body.setTelephone("test");
        AuditNoticeRequest request = new AuditNoticeRequest();
        request.setContent(JsonUtils.toJson(body));
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("auditNoticeApplication success.");
                return null;
            }
        }).when(noticeService).auditNoticeApplication(anyString(), any());
        mktIacClient.auditResultNotice("APPLICATION", "id", "PASS", request);
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void auditVendorShop() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("auditNoticeVendorShop success.");
                return null;
            }
        }).when(noticeService).auditNoticeVendorShop(anyString(), any());
        mktIacClient.auditResultNotice("VENDOR_SHOP", "vendor_id", "PASS", null);
    }
}