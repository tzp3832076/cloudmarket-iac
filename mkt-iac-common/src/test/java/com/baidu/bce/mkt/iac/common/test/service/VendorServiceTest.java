/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;
import com.baidu.bce.mkt.iac.common.service.VendorService;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
public class VendorServiceTest extends BaseCommonServiceTest {
    @Autowired
    private VendorService vendorService;

    @Test
    public void saveShopDraft() throws Exception {
        String vendorId = "test";
        VendorShopDraft draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNull(draft);
        // first add
        vendorService.saveShopDraft(vendorId, "test");
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        Assert.assertEquals(draft.getContent(), "test");
        // second
        vendorService.saveShopDraft(vendorId, "test_2");
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        Assert.assertEquals(draft.getContent(), "test_2");
    }

    @Test
    public void getVendorShopDraft() throws Exception {
        String vendorId = "vendor_1";
        VendorShopDraft draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        draft = vendorService.getVendorShopDraft("XXXXX");
        Assert.assertNull(draft);
    }

}