/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.test;

import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;

/**
 * Created by chenxiang05@baidu.com on 2018/8/21.
 */
public class TestDataGenerator {

    public static AiVendorInfo generateAiVendorInfo() {
        AiVendorInfo aiVendorInfo = new AiVendorInfo();
        aiVendorInfo.setVendorId("AI_VENDOR");
        aiVendorInfo.setApplicationArea("APPLICATION_AREA");
        aiVendorInfo.setBusinessContact("BUSINESS_CONTACT");
        aiVendorInfo.setBusinessIntroduction("BUSINESS_INTRODUCTION");
        aiVendorInfo.setCapital(123456L);
        aiVendorInfo.setHeadcount(123L);
        aiVendorInfo.setEmail("EMAIL");
        aiVendorInfo.setEnterpriseEmail("EEMAIL");
        aiVendorInfo.setEmergencyContact("ECONTACT");
        aiVendorInfo.setCompany("COMPANY");
        aiVendorInfo.setMobile("MOBILE");
        aiVendorInfo.setUserId("USER_ID");
        aiVendorInfo.setUserType("USER_TYPE");
        aiVendorInfo.setTelephone("TELEPHONE");
        aiVendorInfo.setWebsite("WEBSITE");
        aiVendorInfo.setServiceType("SERVICE_TYPE");
        return aiVendorInfo;
    }
}
