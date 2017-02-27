/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.service.notice.NoticeService;
import com.baidu.bce.mkt.iac.common.service.notice.model.ApplicationNoticeBody;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
public class NoticeServiceTest extends BaseCommonServiceTest {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private VendorInfoMapper vendorInfoMapper;

    @Test
    public void auditNotice() throws Exception {
        ApplicationNoticeBody body = new ApplicationNoticeBody();
        body.setAuditId("test");
        body.setCompany("test");
        body.setAddress("test");
        body.setBceUserId("test");
        body.setCapital("test");
        body.setContactInfo("test");
        body.setEmail("test");
        body.setHeadcount("test");
        body.setHotline("test");
        body.setMarket("test");
        body.setSale("test");
        body.setServiceCategory("test-test");
        body.setBceUserId("test");
        body.setServiceIllustration("test");
        body.setTelephone("test");
        noticeService.auditNotice("APPLICATION", "vendor_test_1", "PASS",
                JsonUtils.toJson(body));
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId("vendor_test_1");
        Assert.assertNull(vendorInfo);
    }

}