/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.internalsdk.qualify.model.EnterpriseInfoResponse;
import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.internalsdk.qualify.model.finance.RealNameTypeForFinance;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.model.ProcessStatus;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;
import com.baidu.bce.mkt.iac.common.service.VendorService;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;
import com.baidu.bce.plat.webframework.exception.BceException;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Slf4j
public class VendorServiceTest extends BaseCommonServiceTest {
    @Autowired
    private VendorService vendorService;
    @Autowired
    private VendorShopDraftMapper vendorShopDraftMapper;

    @Test
    public void saveShopDraft() throws Exception {
        String vendorId = "vendor_2";
        VendorShopDraft draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNull(draft);
        VendorShopAuditContent content = new VendorShopAuditContent();
        VendorShopAuditContent.ShopDraft shopDraft = new VendorShopAuditContent.ShopDraft();
        shopDraft.setServicePhone("test");
        shopDraft.setServiceAvailTime("test");
        shopDraft.setServiceEmail("test");
        shopDraft.setCompanyDescription("test");
        shopDraft.setBaiduQiaos(new ArrayList<>());
        shopDraft.setBaiduWalletAccount("test");
        shopDraft.setBceAccount("test");
        shopDraft.setCompanyName("test");
        content.setData(shopDraft);
        // first add
        vendorService.saveShopDraft(vendorId, content);
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        log.info("content {}", draft.getContent());
        Assert.assertEquals(draft.getContent().contains("\"servicePhone\":\"test\""), true);
        // second
        shopDraft.setServicePhone("111111");
        vendorService.saveShopDraft(vendorId, content);
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        Assert.assertEquals(draft.getContent().contains("\"servicePhone\":\"111111\""), true);
        // exception StatusInAudit
        vendorShopDraftMapper.updateShopAuditIdAndStatus(vendorId, "test", InfoStatus.AUDIT);
        try {
            vendorService.saveShopDraft(vendorId, content);
            Assert.fail("no exception");
        } catch (BceException e) {
            Assert.assertEquals(e.getCode(), "StatusInAudit");
        }
    }

    @Test
    public void getVendorShopDraft() throws Exception {
        String vendorId = "vendor_1";
        VendorShopDraft draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        draft = vendorService.getVendorShopDraft("XXXXX");
        Assert.assertNull(draft);
    }

    @Test
    public void getVendorShopDraftContent() {
        String vendorId = "vendor_1";
        VendorShopAuditContent content = new VendorShopAuditContent();
        VendorShopAuditContent.ShopDraft shopDraft = new VendorShopAuditContent.ShopDraft();
        shopDraft.setServicePhone("test");
        shopDraft.setServiceAvailTime("test");
        shopDraft.setServiceEmail("test");
        shopDraft.setCompanyDescription("test");
        shopDraft.setBaiduQiaos(new ArrayList<>());
        shopDraft.setBaiduWalletAccount("test");
        content.setData(shopDraft);
        // first add
        vendorService.saveShopDraft(vendorId, content);
        ShopDraftContentAndStatus contentAndStatus = vendorService.getShopDraftContentAndStatus(vendorId);
        Assert.assertEquals(contentAndStatus.getStatus(), InfoStatus.EDIT);
        Assert.assertEquals(contentAndStatus.getContent().getData().getBaiduWalletAccount(), "test");
        Assert.assertEquals(contentAndStatus.getContent().getData().getServiceEmail(), "test");
    }

    @Test
    public void getVendorInfo() {
        VendorInfo vendorInfo = vendorService.getVendorInfoByVendorId("vendor_1");
        Assert.assertNotNull(vendorInfo);
        vendorInfo = vendorService.getVendorInfoByVendorId("XXXXX");
        Assert.assertNull(vendorInfo);
    }

    @Test
    public void getVendorOverview() {
        EnterpriseInfoResponse response = new EnterpriseInfoResponse();
        response.setStatus(AuditStatus.PASS);
        response.setType(RealNameTypeForFinance.ENTERPRISE);
        when(qualifyClient.getEnterpriseInfo(anyString(), anyBoolean())).thenReturn(response);
        VendorOverview vendorOverview = vendorService.getVendorOverview("vendor_1");
        log.info("getVendorOverview {}", vendorOverview);
        Assert.assertNotNull(vendorOverview.getVendorInfo());
        Assert.assertEquals(vendorOverview.getVendorAuditStatus(), ProcessStatus.DONE);
        Assert.assertEquals(vendorOverview.getVendorShopAuditStatus(), ProcessStatus.DONE);
        Assert.assertEquals(vendorOverview.getAgreementAuditStatus(), ProcessStatus.DONE);
        Assert.assertEquals(vendorOverview.getDepositAuditStatus(), ProcessStatus.DONE);
        Assert.assertEquals(vendorOverview.getQualityStatus(), AuditStatus.PASS);
    }

    @Test
    public void updateVendorStatus() {
        String vendorId = "vendor_1";
        VendorInfo vendorInfo = vendorService.getVendorInfoByVendorId(vendorId);
        Assert.assertEquals(vendorInfo.getStatus(), VendorStatus.PROCESSING);
        vendorService.updateVendorStatus(vendorId, VendorStatus.FROZEN.name());
        vendorInfo = vendorService.getVendorInfoByVendorId(vendorId);
        Assert.assertEquals(vendorInfo.getStatus(), VendorStatus.FROZEN);
    }

    @Test
    public void getVendorShopByBceUserId() {
        String bceUserId = "bce_user_1";
        VendorShop shop = vendorService.getVendorShopByBceUserId(bceUserId);
        Assert.assertNotNull(shop);
    }
}