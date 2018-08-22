/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.baidu.bce.internalsdk.qualify.model.EnterpriseInfoResponse;
import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.internalsdk.qualify.model.finance.RealNameTypeForFinance;
import com.baidu.bce.mkt.audit.internal.sdk.model.response.SubmitAuditResponse;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.model.ProcessStatus;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorListModel;
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
        vendorService.saveShopDraft(vendorId, shopDraft);
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        log.info("content {}", draft.getContent());
        Assert.assertEquals(draft.getContent().contains("\"servicePhone\":\"test\""), true);
        // second
        shopDraft.setServicePhone("111111");
        vendorService.saveShopDraft(vendorId, shopDraft);
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        Assert.assertEquals(draft.getContent().contains("\"servicePhone\":\"111111\""), true);
        // exception StatusInAudit
        vendorShopDraftMapper.updateShopAuditIdAndStatus(vendorId, "test", InfoStatus.AUDIT);
        try {
            vendorService.saveShopDraft(vendorId, shopDraft);
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
        VendorShopAuditContent.ShopDraft shopDraft = new VendorShopAuditContent.ShopDraft();
        shopDraft.setServicePhone("test");
        shopDraft.setServiceAvailTime("test");
        shopDraft.setServiceEmail("test");
        shopDraft.setCompanyDescription("test");
        shopDraft.setBaiduQiaos(new ArrayList<>());
        shopDraft.setBaiduWalletAccount("test");
        // first add
        vendorService.saveShopDraft(vendorId, shopDraft);
        ShopDraftContentAndStatus contentAndStatus = vendorService.getShopDraftContentAndStatus(vendorId);
        Assert.assertEquals(contentAndStatus.getStatus(), InfoStatus.EDIT);
        Assert.assertEquals(contentAndStatus.getContent().getBaiduWalletAccount(), "test");
        Assert.assertEquals(contentAndStatus.getContent().getServiceEmail(), "test");
    }

    @Test
    public void getVendorInfo() {
        VendorInfo vendorInfo = vendorService.getVendorInfoByVendorId("vendor_1");
        Assert.assertNotNull(vendorInfo);
        log.info("vendorInfo:{}", vendorInfo);
        vendorInfo = vendorService.getVendorInfoByVendorId("XXXXX");
        Assert.assertNull(vendorInfo);

    }

    @Test
    public void getAllSearchVendor() {
        VendorListModel vendorInfos = vendorService.getVendorList("", "", 0, 0, null);
        Assert.assertNotNull(vendorInfos.getVendorInfoList());
        Assert.assertFalse(vendorInfos.getVendorInfoList().isEmpty());
        log.info("getAllSearchVendor {}", vendorInfos);
        vendorInfos = vendorService.getVendorList(null, "2", 0, 0, null);
        Assert.assertNotNull(vendorInfos.getVendorInfoList());
        Assert.assertFalse(vendorInfos.getVendorInfoList().isEmpty());
        vendorInfos = vendorService.getVendorList(null, "XX", 0, 0, null);
        Assert.assertNotNull(vendorInfos);
        Assert.assertTrue(CollectionUtils.isEmpty(vendorInfos.getVendorInfoList()));
    }

    @Test
    public void getVendorListV2Test() {
        VendorListModel vendorInfos = vendorService.getVendorListV2("", "", 0, 0, "BIG_DATA", null);
        Assert.assertNotNull(vendorInfos.getVendorInfoList());
        Assert.assertFalse(vendorInfos.getVendorInfoList().isEmpty());
        log.info("getAllSearchVendor {}", vendorInfos);
        vendorInfos = vendorService.getVendorListV2("", "", 0, 100, "BIG_DATA", null);
        Assert.assertEquals(1, vendorInfos.getTotalCount());
    }

    @Test
    public void getVendorListByIds() {
        List<String> vendorIds = new ArrayList<>();
        vendorIds.add("vendor_1");
        VendorListModel listModel = vendorService.getVendorListByIds(vendorIds);
        Assert.assertEquals(listModel.getVendorInfoList().size(), 1);
        vendorIds.add("vendor_2");
        listModel = vendorService.getVendorListByIds(vendorIds);
        Assert.assertEquals(listModel.getVendorInfoList().size(), 2);
        vendorIds.add("vendor_XXX");
        listModel = vendorService.getVendorListByIds(vendorIds);
        Assert.assertEquals(listModel.getVendorInfoList().size(), 2);
    }

    @Test
    public void getVendorShopByVendorId() {
        VendorShop vendorShop = vendorService.getVendorShopByVendorId("vendor_1");
        Assert.assertNotNull(vendorShop);
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
        Assert.assertEquals(vendorOverview.getAgreementAuditStatus(), ProcessStatus.TODO);
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

    @Test
    public void statisticsVendorAmount() {
        Map<VendorStatus, Integer> countMap = vendorService.statisticsVendorAmount();
        Assert.assertEquals(countMap.get(VendorStatus.HOSTED).intValue(), 0);
        Assert.assertEquals(countMap.get(VendorStatus.PROCESSING).intValue(), 2);
    }

    @Test
    public void cancelAuditShopDraft() {
        String vendorId = "vendor_2";
        VendorShopAuditContent auditContent = new VendorShopAuditContent();
        VendorShopAuditContent.ShopDraft shopDraft = new VendorShopAuditContent.ShopDraft();
        auditContent.setData(shopDraft);
        when(auditClient.auditSubmit(any())).thenReturn(new SubmitAuditResponse("test_audit_id"));
        vendorService.submitShopDraft(vendorId, auditContent);
        VendorShopDraft draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        Assert.assertEquals(draft.getStatus(), InfoStatus.AUDIT);
        Assert.assertEquals(draft.getAuditId(), "test_audit_id");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("cancel audit success");
                return null;
            }
        }).when(auditClient).cancelAudit(anyString());
        vendorService.cancelAuditShopDraft(vendorId);
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertNotNull(draft);
        Assert.assertEquals(draft.getStatus(), InfoStatus.EDIT);
        Assert.assertEquals(draft.getAuditId(), "");
    }

    @Test
    public void editShopDraft() {
        String vendorId = "vendor_pass";
        VendorShopDraft draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertEquals(draft.getStatus(), InfoStatus.PASS);
        vendorService.editAuditShopDraft(vendorId);
        draft = vendorService.getVendorShopDraft(vendorId);
        Assert.assertEquals(draft.getStatus(), InfoStatus.EDIT);
    }

    @Test
    public void getVendorInfoByUserId() {
        VendorInfo info = vendorService.getVendorInfoByUserId("bce_user_1");
        Assert.assertNotNull(info);
    }

    @Test
    public void signAgreement() {
        String vendorId = "vendor_1";
        VendorInfo vendorInfo = vendorService.getVendorInfoByVendorId(vendorId);
        Assert.assertEquals(vendorInfo.getAgreementStatus(), ProcessStatus.TODO);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("syncVendorContract yes");
                return null;
            }
        }).when(auditClient).syncVendorContract(any(), anyString());
        vendorService.signAgreement("vendor_1");
        vendorInfo = vendorService.getVendorInfoByVendorId(vendorId);
        Assert.assertEquals(vendorInfo.getAgreementStatus(), ProcessStatus.DONE);
    }


}