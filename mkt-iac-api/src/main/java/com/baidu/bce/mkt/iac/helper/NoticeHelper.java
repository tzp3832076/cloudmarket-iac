/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import com.baidu.bce.internalsdk.mkt.iac.model.ApplicationNoticeBody;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@ControllerHelper
@Slf4j
public class NoticeHelper {

    public VendorInfo toVendorInfo(String vendorId, String content) {
        // done 在审核系统通过后返回的是 json(application)
        ApplicationNoticeBody application = JsonUtils.fromJson(content, ApplicationNoticeBody
                                                                                .class);
        if (application == null) {
            log.error("ApplicationNoticeBody fromJson is null");
            throw MktIacExceptions.notJsonFormat();
        }
        return new VendorInfo(vendorId, application.getBceUserId(),
                                                       VendorStatus.PROCESSING,
                                                       application.getCompany(),
                                                       application.getWebsite(),
                                                       application.getCapital(),
                                                       application.getHeadcount(),
                                                       application.getAddress(),
                                                       application.getTelephone(),
                                                       application.getEmail(),
                                                       application.getServiceIllustration(),
                                                       application.getServiceCategory(),
                                                       application.getHotline(),
                                                       application.getMarket(),
                                                       application.getContactInfo());
    }
}
