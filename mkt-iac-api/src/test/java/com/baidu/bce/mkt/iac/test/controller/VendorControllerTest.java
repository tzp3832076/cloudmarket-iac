/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;
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
        request.setContent("test");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("saveVendorShopDraft success.");
                return null;
            }
        }).when(vendorService).saveShopDraft(anyString(), anyString());
        mktIacClient.saveVendorShopDraft("test", request);
    }

    @Test
    public void getVendorShopDraft() throws Exception {
        VendorShopDraft shopDraft = new VendorShopDraft("test", "test");
        when(vendorService.getVendorShopDraft(anyString())).thenReturn(shopDraft);
        mktIacClient.getShopDraftDetail("test");
    }
}