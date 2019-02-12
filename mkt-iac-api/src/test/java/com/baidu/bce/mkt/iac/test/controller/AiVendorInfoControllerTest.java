/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorListResponse;
import com.baidu.bce.mkt.iac.common.model.AiVendorListModel;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoRequest;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenxiang05@baidu.com on 2018/8/21.
 */

@Slf4j
public class AiVendorInfoControllerTest extends ApiMockMvcTest {

    @Test
    public void testAddAiVendorInfo() {
        AiVendorInfoRequest request = new AiVendorInfoRequest();
        request.setVendorId("AI_VENDOR");
        request.setApplicationArea("APPLICATION_AREA");
        request.setBusinessContact("BUSINESS_CONTACT");
        request.setBusinessIntroduction("BUSINESS_INTRODUCTION");
        request.setCapital(123456L);
        request.setHeadcount(123L);
        request.setEmail("EMAIL");
        request.setEnterpriseEmail("EEMAIL");
        request.setEmergencyContact("ECONTACT");
        request.setCompany("COMPANY");
        request.setMobile("MOBILE");
        request.setUserId("USER_ID");
        request.setUserType("USER_TYPE");
        request.setTelephone("TELEPHONE");
        request.setWebsite("WEBSITE");
        request.setServiceType("SERVICE_TYPE");

        doAnswer(invocationOnMock -> {
            AiVendorInfo received = invocationOnMock.getArgumentAt(0, AiVendorInfo.class);
            log.info("{}", received);
            return "hahaha";
        }).when(aiVendorInfoService).newVendor(any());

        mktIacClient.addAiVendorInfo(request);

        verify(aiVendorInfoService, times(1)).newVendor(any());

    }

    @Test
    public void testAiVendorList() {
        AiVendorListModel listModel = new AiVendorListModel();
        listModel.setTotalCount(1);

        List<AiVendorInfo> aiVendorInfoList = new ArrayList<>();
        AiVendorInfo aiVendorInfo = new AiVendorInfo();
        aiVendorInfo.setVendorId("vendor_1");
        aiVendorInfo.setUserId("user_id_1");
        aiVendorInfo.setCompany("test1");
        aiVendorInfoList.add(aiVendorInfo);

        listModel.setAiVendorInfo(aiVendorInfoList);
        when(aiVendorInfoService.getApplicantList(anyString(), anyString(), anyString(), anyInt(), anyInt()))
                .thenReturn(listModel);
        AiVendorListResponse response = mktIacClient.getAiVendorList("test1", 1, 10);
        log.info("getVendorList {}", response);
    }

    @Test
    public void testVendorListByIds() {
        AiVendorListModel listModel = new AiVendorListModel();
        listModel.setTotalCount(1);

        List<AiVendorInfo> aiVendorInfoList = new ArrayList<>();
        AiVendorInfo aiVendorInfo = new AiVendorInfo();
        aiVendorInfo.setVendorId("vendor_1");
        aiVendorInfo.setUserId("user_id_1");
        aiVendorInfo.setCompany("test1");
        aiVendorInfoList.add(aiVendorInfo);

        listModel.setAiVendorInfo(aiVendorInfoList);
        when(aiVendorInfoService.getVendorListByIds(any()))
                .thenReturn(listModel);

        List<String> vendorIds = new ArrayList<>();
        vendorIds.add("vendor_1");
        AiVendorListResponse aiVendorListByIds = mktIacClient.getAiVendorListByIds(vendorIds);

        Assert.assertEquals(1, aiVendorListByIds.getTotalCount());
        log.info("aiVendorListByIds {} ", aiVendorListByIds.getAiVendorInfoListModel());
    }

}
