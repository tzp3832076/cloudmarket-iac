/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@InitDatabase(tables = "mkt_vendor_shop_draft")
public class VendorShopDraftMapperTest extends BaseMapperTest {
    @Autowired
    private VendorShopDraftMapper shopDraftMapper;

    @Test
    public void add() throws Exception {
        VendorShopDraft shopDraft = new VendorShopDraft("test", "test");
        int res = shopDraftMapper.add(shopDraft);
        Assert.assertEquals(1, res);
    }

    @Test
    public void getVendorInfoByVendorId() throws Exception {
        VendorShopDraft draft = shopDraftMapper.getShopDraftByVendorId("vendor_1");
        Assert.assertNotNull(draft);
    }

    @Test
    public void updateShopDraftContentAndStatus() throws Exception {
        String vendorId = "test_vendor";
        VendorShopDraft shopDraft = new VendorShopDraft(vendorId, "test");
        shopDraftMapper.add(shopDraft);
        shopDraft = shopDraftMapper.getShopDraftByVendorId(vendorId);
        Assert.assertEquals(shopDraft.getStatus(), InfoStatus.EDIT);
        Assert.assertEquals(shopDraft.getContent(), "test");
        shopDraftMapper.updateShopDraftContentAndStatus(vendorId, "test_update", InfoStatus.AUDIT);
        shopDraft = shopDraftMapper.getShopDraftByVendorId(vendorId);
        Assert.assertEquals(shopDraft.getStatus(), InfoStatus.AUDIT);
        Assert.assertEquals(shopDraft.getContent(), "test_update");
    }

    @Test
    public void updateShopAuditIdAndStatus() throws Exception {
        String vendorId = "test_vendor";
        VendorShopDraft shopDraft = new VendorShopDraft(vendorId, "test");
        shopDraftMapper.add(shopDraft);
        shopDraft = shopDraftMapper.getShopDraftByVendorId(vendorId);
        Assert.assertEquals(shopDraft.getStatus(), InfoStatus.EDIT);
        Assert.assertEquals(shopDraft.getAuditId(), "");
        shopDraftMapper.updateShopAuditIdAndStatus(vendorId, "auditId", InfoStatus.PASS);
        shopDraft = shopDraftMapper.getShopDraftByVendorId(vendorId);
        Assert.assertEquals(shopDraft.getStatus(), InfoStatus.PASS);
        Assert.assertEquals(shopDraft.getAuditId(), "auditId");
    }

}