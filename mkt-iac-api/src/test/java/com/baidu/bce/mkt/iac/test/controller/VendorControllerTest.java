/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baidu.bce.internalsdk.core.BceInternalResponseException;
import com.baidu.bce.internalsdk.mkt.iac.model.OnlineSupport;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Slf4j
public class VendorControllerTest extends ApiMockMvcTest {
    @Test
    public void saveVendorShopDraft() throws Exception {
        ShopDraftSaveRequest request = new ShopDraftSaveRequest();
        List<OnlineSupport> onlineSupports = new ArrayList<>();
        onlineSupports.add(new OnlineSupport("test", "test"));
        request.setOnlineSupports(onlineSupports);
        request.setServiceTime("test");
        request.setIntro("test");
        request.setCellphone("12345678901");
        request.setEmail("sfy@baidu.com");
        request.setWalletId("test");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("saveVendorShopDraft success.");
                return null;
            }
        }).when(vendorService).saveShopDraft(anyString(), any());
        try {
            mktIacClient.saveVendorShopDraft("test", request);
            Assert.fail("no exception");
        } catch (BceInternalResponseException e) {
            Assert.assertEquals(e.getCode(), "CellphoneNotValid");
        }

        request.setCellphone("17710655544");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("saveVendorShopDraft success.");
                return null;
            }
        }).when(vendorService).saveShopDraft(anyString(), any());
        mktIacClient.saveVendorShopDraft("test", request);
    }

    @Test
    public void getVendorShopDraft() throws Exception {
        ShopDraftContentAndStatus shopDraft = new ShopDraftContentAndStatus();
        shopDraft.setStatus(InfoStatus.AUDIT);
        VendorShopAuditContent content = new VendorShopAuditContent();
        content.setCellphone("test");
        List<VendorShopAuditContent.CustomerService> customerServices = new ArrayList<>();
        customerServices.add(new VendorShopAuditContent.CustomerService("test", "test"));
        content.setCustomerServices(customerServices);
        content.setWalletId("test");
        content.setEmail("test");
        content.setIntro("test");
        content.setName("test");
        content.setServiceTime("test");
        content.setVendorId("test");
        shopDraft.setContent(content);
        when(vendorService.getShopDraftContentAndStatus(anyString())).thenReturn(shopDraft);
        mktIacClient.getShopDraftDetail("test");
    }
}