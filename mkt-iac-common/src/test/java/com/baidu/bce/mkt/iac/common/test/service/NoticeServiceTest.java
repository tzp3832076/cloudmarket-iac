/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.test.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.service.NoticeService;
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
        VendorInfo vendorInfo = new VendorInfo("test", "test", VendorStatus.OFFLINE,
                                                      "test", "website", "1000", "address",
                                                      "tel", "test-test", "hotline", "othermarket",
                                                      "contact_info");
        noticeService.auditNoticeApplication("PASS", vendorInfo);
        VendorInfo test = vendorInfoMapper.getVendorInfoByVendorId("vendor_test_1");
        Assert.assertNull(test);
    }

}