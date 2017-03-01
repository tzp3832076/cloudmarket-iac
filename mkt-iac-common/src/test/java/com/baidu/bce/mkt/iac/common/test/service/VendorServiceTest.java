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
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;
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
        content.setServiceTime("test");
        content.setIntro("test");
        content.setEmail("test");
        content.setWalletId("test");
        content.setCellphone("test");
        content.setCustomerServices(new ArrayList<>());
        // first add
        vendorService.saveShopDraft(vendorId, content);
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        log.info("content {}", draft.getContent());
        Assert.assertEquals(draft.getContent().contains("\"cellphone\":\"test\""), true);
        // second
        content.setCellphone("111111");
        vendorService.saveShopDraft(vendorId, content);
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        Assert.assertEquals(draft.getContent().contains("\"cellphone\":\"111111\""), true);
        // exception StatusInAudit
        vendorShopDraftMapper.updateShopAuditIdAndStatus(vendorId, "test", InfoStatus.PASS);
        try {
            vendorService.saveShopDraft("XXX", content);
            Assert.fail("no exception");
        } catch (BceException e) {
            Assert.assertEquals(e.getCode(), "NoVendorInfo");
        }
        // exception NoVendorInfo
        try {
            vendorService.saveShopDraft("XXX", content);
            Assert.fail("no exception");
        } catch (BceException e) {
            Assert.assertEquals(e.getCode(), "NoVendorInfo");
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
        content.setServiceTime("test");
        content.setIntro("test");
        content.setEmail("test");
        content.setWalletId("test");
        content.setCellphone("test");
        content.setCustomerServices(new ArrayList<>());
        // first add
        vendorService.saveShopDraft(vendorId, content);
        ShopDraftContentAndStatus contentAndStatus = vendorService.getShopDraftContentAndStatus(
                vendorId);
        Assert.assertEquals(contentAndStatus.getStatus(), InfoStatus.EDIT);
        Assert.assertEquals(contentAndStatus.getContent().getCellphone(), "test");
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
        Assert.assertNotNull(vendorOverview.getVendorShop());
        Assert.assertEquals(vendorOverview.getQualityStatus(), AuditStatus.PASS);
        Assert.assertNotNull(vendorOverview.getVendorContractList());
        Assert.assertNotNull(vendorOverview.getVendorDeposit());
    }

}