/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.VendorStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.service.notice.model.ApplicationNoticeBody;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.m .
 */
@Slf4j
@Service
public class NoticeService {
    @Autowired
    private VendorInfoMapper vendorInfoMapper;
    private static String TYPE_APPLICATION = "APPLICATION";
    private static String TYPE_VENDOR_SHOP = "VENDOR_SHOP";

    public void auditNotice(String type, String id, String status, String content) {
        if (TYPE_APPLICATION.equals(type)) {
            if (InfoStatus.PASS.name().equals(status)) {
                addVendorInfo(id, content);
            }
        }
        if (TYPE_VENDOR_SHOP.equals(type)) {
            // todo update vendor_draft 状态
            if (InfoStatus.PASS.name().equals(status)) {
                // todo update mkt_vendor_shop
                log.info("todo");
            }
        }
    }

    private void addVendorInfo(String vendorId, String content) {
        ApplicationNoticeBody application = JsonUtils.fromJson(content, ApplicationNoticeBody
                                                                                .class);
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        if (vendorInfo == null) {
            vendorInfoMapper.add(new VendorInfo(vendorId, application.getBceUserId(),
                                                       VendorStatus.INIT,
                                                       application.getCompany(),
                                                       application.getWebsite(),
                                                       application.getCapital(),
                                                       application.getAddress(),
                                                       application.getTelephone(),
                                                       application.getServiceCategory(),
                                                       application.getHotline(),
                                                       application.getMarket(),
                                                       application.getContactInfo()));
        } else {
            log.warn("addVendorInfo vendor info exist, vendorId {}", vendorId);
        }
    }
}
