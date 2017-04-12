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
import com.baidu.bce.internalsdk.mkt.iac.model.VendorShopInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorShopResponse;
import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.ReceivedAuthorizedToken;
import com.baidu.bce.mkt.framework.test.iam.CurrentUser;
import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.model.ProcessStatus;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorListModel;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorServiceInfoModel;
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
        request.setServicePhone("123");
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
            Assert.assertTrue(e.getMessage().contains("长度必须在"));
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
        request.setCompanyDescription("<script>alert(1)</script>");
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
        when(vendorService.getVendorInfoByVendorId(anyString())).thenReturn(generateVendorInfo());
        VendorInfoDetailResponse detailResponse = mktIacClient.getVendorInfoDetail();
        log.info("getVendorInfoDetail {}", JsonUtils.toJson(detailResponse));
    }

    @Test
    public void getVendorOverview() {
        VendorOverview vendorOverview =  new VendorOverview();
        vendorOverview.setVendorInfo(generateVendorInfo());
        vendorOverview.setVendorAuditStatus(ProcessStatus.DONE);
        vendorOverview.setDepositAuditStatus(ProcessStatus.TODO);
        vendorOverview.setVendorShopAuditStatus(ProcessStatus.AUDITING);
        vendorOverview.setAgreementAuditStatus(ProcessStatus.DONE);
        vendorOverview.setQualityStatus(AuditStatus.NONE);
        when(vendorService.getVendorOverview(anyString())).thenReturn(vendorOverview);
        VendorOverviewResponse response = mktIacClient.getVendorOverview();
        log.info("getVendorOverview {} ", response);
    }

    @Test
    public void getVendorOverviewVendorId() {
        VendorOverview vendorOverview =  new VendorOverview();
        vendorOverview.setVendorInfo(generateVendorInfo());
        vendorOverview.setVendorAuditStatus(ProcessStatus.DONE);
        vendorOverview.setDepositAuditStatus(ProcessStatus.TODO);
        vendorOverview.setVendorShopAuditStatus(ProcessStatus.AUDITING);
        vendorOverview.setAgreementAuditStatus(ProcessStatus.DONE);
        vendorOverview.setQualityStatus(AuditStatus.NONE);
        when(vendorService.getVendorOverview(anyString())).thenReturn(vendorOverview);
        String vendorId = "test";
        VendorOverviewResponse response = mktIacClient.getVendorOverview(vendorId);
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
    @CurrentUser(isServiceAccount = true)
    public void getVendorShopByVendorId() {
        when(vendorService.getVendorShopByVendorId(anyString())).thenReturn(generateVendorShop());
        VendorShopResponse response = mktIacClient.getVendorShopResponse("test");
        log.info("getVendorShopByVendorId {} ", response);
    }

    @Test
    public void getVendorShopOnlineInfo() {
        when(vendorService.getVendorShopByVendorId(anyString())).thenReturn(generateVendorShop());
        when(vendorService.getVendorInfoByVendorId(anyString())).thenReturn(generateVendorInfo());
        VendorShopInfoDetailResponse response = mktIacClient.getVendorShopOnlineInfo("test");
        Assert.assertNotNull(response);
        log.info("getVendorShopOnlineInfo {}", response);
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
        vendorInfoList.add(generateVendorInfo());
        vendorListModel.setVendorInfoList(vendorInfoList);
        when(vendorService.getVendorList(anyString(), anyString(), anyInt(), anyInt(), any()))
                .thenReturn(vendorListModel);
        VendorListResponse response = mktIacClient.getVendorList("test", "test", 1, 1);
        log.info("getVendorList {}", response);
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void getVendorSearchMap() {
        VendorListModel vendorListModel = new VendorListModel();
        vendorListModel.setTotalCount(10);
        List<VendorInfo> vendorInfoList = new ArrayList<>();
        vendorInfoList.add(generateVendorInfo());
        vendorListModel.setVendorInfoList(vendorInfoList);
        when(vendorService.getVendorList(anyString(), anyString(), anyInt(), anyInt(), any()))
                .thenReturn(vendorListModel);
        VendorListResponse response = mktIacClient.getSearchVendor("test");
        log.info("get vendor map {}", response);
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
    public void editShopDraft() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("edit  success");
                return null;
            }
        }).when(vendorService).editAuditShopDraft(anyString());
        mktIacClient.editShopDraft();
    }

    @Test
    @CurrentUser(isServiceAccount = true)
    public void getVendorInfoByUserId() {
        when(vendorService.getVendorInfoByUserId(anyString())).thenReturn(generateVendorInfo());

        mktIacClient.getVendorInfoByUserId("test");
    }

    @Test
    public void signAgreement() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                log.info("signAgreement");
                return null;
            }
        }).when(vendorService).signAgreement(any());
        mktIacClient.signAgreement();
    }

    private VendorInfo generateVendorInfo() {
        return new VendorInfo("test", "test", VendorStatus.FROZEN,
                                     "test", "companySite", 1000, "address", "tel", "test-test",
                                     "hotline", "othermarket", "{\"contactList\":"
                                                                       +
                                                                       "[{\"type\":\"Business\","
                                                                       + "\"name\":\"test\","
                                                                       + "\"phone\":\"17710655544\"},"
                                                                       +
                                                                       "{\"type\":\"Emergency\","
                                                                       + "\"name\":\"test\","
                                                                       + "\"phone\":\"17710655544\"},"
                                                                       +
                                                                       "{\"type\":\"Technical\","
                                                                       + "\"name\":\"test\","
                                                                       + "\"phone\":\"17710655544\"}]}");
    }

    private VendorShop generateVendorShop() {
        VendorShop vendorShop = new VendorShop();
        vendorShop.setEmail("test");
        vendorShop.setCellphone("test");
        vendorShop.setVendorId("test");
        vendorShop.setServiceInfo("test");
        vendorShop.setName("test");
        VendorServiceInfoModel serviceInfoModel = new VendorServiceInfoModel();
        List<OnlineSupport> onlineSupportList = new ArrayList<>();
        onlineSupportList.add(new OnlineSupport("test", "test"));
        serviceInfoModel.setOnlineSupports(onlineSupportList);
        serviceInfoModel.setServiceAvailTime("9:00-18:00");
        serviceInfoModel.setOnlineSupports(onlineSupportList);
        vendorShop.setServiceInfo(JsonUtils.toJson(serviceInfoModel));
        return vendorShop;
    }
}