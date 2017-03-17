/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.baidu.bce.internalsdk.core.BceInternalResponseException;
import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;
import com.baidu.bce.internalsdk.mkt.iac.model.OnlineSupport;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorAmountResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorBaseContactResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;
import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.ReceivedAuthorizedToken;
import com.baidu.bce.mkt.framework.test.iam.CurrentUser;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.model.ProcessStatus;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorInfoContacts;
import com.baidu.bce.mkt.iac.common.model.VendorListModel;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Slf4j
public class VendorControllerTest extends ApiMockMvcTest {

    @Before
    public void  initVendorId() {
        MktToken mktToken = new MktToken();
        mktToken.setUserId(IdUtils.generateUUID());
        mktToken.setVendorId(IdUtils.generateUUID());
        AuthorizedToken authorizedToken = new ReceivedAuthorizedToken(new Token(), mktToken);
        when(checkAuthService.checkAuth(any(), any(), anyString(), anyString(), anyList())).thenReturn(authorizedToken);
    }

    @Test
    public void saveVendorShopDraft() throws Exception {
        ShopDraftSaveRequest request = new ShopDraftSaveRequest();
        List<OnlineSupport> onlineSupports = new ArrayList<>();
        onlineSupports.add(new OnlineSupport("test", "test"));
        request.setBaiduQiaos(onlineSupports);
        request.setCompanyDescription("test");
        request.setServicePhone("12345678901");
        request.setServiceEmail("sfy@baidu.com");
        request.setBaiduWalletAccount("test");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("saveVendorShopDraft success.");
                return null;
            }
        }).when(vendorService).saveShopDraft(anyString(), any());
        try {
            mktIacClient.saveVendorShopDraft(request);
            Assert.fail("no exception");
        } catch (BceInternalResponseException e) {
            log.info("exception {}", e.getMessage());
            Assert.assertEquals(e.getCode(), "BceValidationException");
            Assert.assertTrue(e.getMessage().contains("servicePhone="
                                                              + IacConstants.FORMAT_ERROR));
        }

        request.setServicePhone("17710655544");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("saveVendorShopDraft success.");
                return null;
            }
        }).when(vendorService).saveShopDraft(anyString(), any());
        mktIacClient.saveVendorShopDraft(request);
    }

    @Test
    public void submitShopDraft() throws Exception {
        ShopDraftSaveRequest request = new ShopDraftSaveRequest();
        List<OnlineSupport> onlineSupports = new ArrayList<>();
        onlineSupports.add(new OnlineSupport("test", "test"));
        request.setCompanyDescription("");
        request.setServicePhone("17710655544");
        request.setServiceEmail("sfy@baidu.com");
        request.setBaiduWalletAccount("test");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("saveVendorShopDraft success.");
                return null;
            }
        }).when(vendorService).saveShopDraft(anyString(), any());
        try {
            mktIacClient.submitVendorShopDraft(request);
            Assert.fail("no exception");
        } catch (BceInternalResponseException e) {
            log.info("exception {}", e.getMessage());
            Assert.assertEquals(e.getCode(), "BceValidationException");
            Assert.assertTrue(e.getMessage().contains("companyDescription="
                                                              + IacConstants.INFO_EMPTY));
            Assert.assertTrue(e.getMessage().contains("baiduQiaos="
                                                              + IacConstants.INFO_EMPTY));
            Assert.assertTrue(e.getMessage().contains("serviceAvailTime="
                                                              + IacConstants.INFO_EMPTY));
        }
        request.setServiceAvailTime("test");
        request.setBaiduQiaos(onlineSupports);
        request.setCompanyDescription("test");
        mktIacClient.submitVendorShopDraft(request);

    }

    @Test
    public void getVendorShopDraft() throws Exception {
        ShopDraftContentAndStatus shopDraftContentAndStatus = new ShopDraftContentAndStatus();
        shopDraftContentAndStatus.setStatus(InfoStatus.AUDIT);
        VendorShopAuditContent.ShopDraft shopDraft = new VendorShopAuditContent.ShopDraft();
        shopDraft.setCompanyName("test");
        shopDraft.setBceAccount("test");
        shopDraft.setBaiduWalletAccount("test");
        List<OnlineSupport> onlineSupportList = new ArrayList<>();
        onlineSupportList.add(new OnlineSupport("test", "test"));
        shopDraft.setBaiduQiaos(onlineSupportList);
        shopDraft.setCompanyDescription("test");
        shopDraft.setServiceEmail("test");
        shopDraft.setServiceAvailTime("test");
        shopDraft.setServicePhone("test");
        shopDraftContentAndStatus.setContent(shopDraft);
        when(vendorService.getShopDraftContentAndStatus(anyString())).thenReturn(shopDraftContentAndStatus);
        ShopDraftDetailResponse response = mktIacClient.getShopDraftDetail();
        log.info("getVendorShopDraft = {}", JsonUtils.toJson(response));
    }

    @Test
    public void getVendorInfoDetail() throws Exception {
        VendorInfoContacts contacts = new VendorInfoContacts();
        List<VendorInfoContacts.ContactWay> contactWays = new ArrayList<>();
        contactWays.add(new VendorInfoContacts.ContactWay(VendorInfoContacts.ContactType.Business,
                                                                 "name", "1111"));
        contactWays.add(new VendorInfoContacts.ContactWay(VendorInfoContacts.ContactType.Emergency,
                                                                 "name", "1111"));
        contactWays.add(new VendorInfoContacts.ContactWay(VendorInfoContacts.ContactType.Technical,
                                                                 "name", "1111"));
        contacts.setContractList(contactWays);
        when(vendorService.getVendorInfoByVendorId(anyString())).thenReturn(
                new VendorInfo("test", "test", VendorStatus.FROZEN,
                                      "test", "companySite", 1000, "address", "tel", "test-test",
                                      "hotline", "othermarket", JsonUtils.toJson(contacts)));
        VendorInfoDetailResponse detailResponse = mktIacClient.getVendorInfoDetail();
        log.info("getVendorInfoDetail {}", JsonUtils.toJson(detailResponse));
    }

    @Test
    public void getVendorOverview() {
        VendorOverview vendorOverview =  new VendorOverview();
        vendorOverview.setVendorInfo( new VendorInfo("test", "test", VendorStatus.FROZEN,
                                                            "test", "companySite", 1000, "address", "tel", "test-test",
                                                            "hotline", "othermarket", "contact_info"));
        vendorOverview.setVendorAuditStatus(ProcessStatus.DONE);
        vendorOverview.setDepositAuditStatus(ProcessStatus.TODO);
        vendorOverview.setVendorShopAuditStatus(ProcessStatus.AUDITINIG);
        vendorOverview.setAgreementAuditStatus(ProcessStatus.DONE);
        vendorOverview.setProductsOnSale(0);
        vendorOverview.setProductsAuditing(0);
        vendorOverview.setQualityStatus(AuditStatus.NONE);
        when(vendorService.getVendorOverview(anyString())).thenReturn(vendorOverview);
        VendorOverviewResponse response = mktIacClient.getVendorOverview();
        log.info("getVendorOverview {} ", response);
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void updateVendorStatus() {
        MktToken mktToken = new MktToken();
        mktToken.setRole("OP");
        mktToken.setUserId(IdUtils.generateShortUUID());
        AuthorizedToken authorizedToken = new ReceivedAuthorizedToken(new Token(), mktToken);
        when(checkAuthService.checkAuth(any(), any(), anyString(), anyString(), anyList())).thenReturn(authorizedToken);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("update vendor status success");
                return null;
            }
        }).when(vendorService).updateVendorStatus(anyString(), anyString());
        mktIacClient.updateVendorStatus("test", "HOSTED");
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void getVendorBaseContactByBceId() {
        VendorShop vendorShop = new VendorShop();
        vendorShop.setEmail("test");
        when(vendorService.getVendorShopByBceUserId(anyString())).thenReturn(vendorShop);
        VendorBaseContactResponse response = mktIacClient.getVendorBaseContactByBceId("test");
        log.info("getVendorBaseContactByBceId {} ", response);
    }

    @Test
    public void getVendorAmount() {
        Map<VendorStatus, Integer> countMap = new HashMap<>();
        countMap.put(VendorStatus.HOSTED, 2);
        countMap.put(VendorStatus.PROCESSING, 0);
        when(vendorService.statisticsVendorAmount()).thenReturn(countMap);
        VendorAmountResponse response = mktIacClient.getVendorAmountStatistics();
        log.info("getVendorAmount {}", response);
    }

    @Test
    public void getVendorList() {
        VendorListModel vendorListModel = new VendorListModel();
        vendorListModel.setTotalCount(10);
        List<VendorInfo> vendorInfoList = new ArrayList<>();
        vendorInfoList.add(new VendorInfo("test", "test", VendorStatus.FROZEN,
                                                 "test", "website", 1000, "address",
                                                 "tel", "test-test", "hotline", "othermarket",
                                                 "contact_info"));
        vendorListModel.setVendorInfoList(vendorInfoList);
        when(vendorService.getVendorList(anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(vendorListModel);
        VendorListResponse response = mktIacClient.getVendorList("test", "test", 1, 1);
        log.info("getVendorList {}", response);
    }

    @Test
    public void cancelAuditShopDraft() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("cancel success");
                return null;
            }
        }).when(vendorService).cancelAuditShopDraft(anyString());
        mktIacClient.cancelShopDraftAudit();
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void getVendorInfoByUserId() {
        when(vendorService.getVendorInfoByVendorId(anyString())).thenReturn(
                new VendorInfo("test", "test", VendorStatus.FROZEN,
                                      "test", "companySite", 1000, "address", "tel", "test-test",
                                      "hotline", "othermarket", JsonUtils.toJson("")));
        mktIacClient.getVendorInfoByUserId("test");
    }
}