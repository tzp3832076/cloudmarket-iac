/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import com.baidu.bce.internalsdk.mkt.iac.model.ApplicationNoticeBody;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@ControllerHelper
public class NoticeHelper {

    public VendorInfo toVendorInfo(String vendorId, String content) {
        // todo 修改bos 到 vendorInfo的部分接口
        ApplicationNoticeBody application = JsonUtils.fromJson(content, ApplicationNoticeBody
                                                                                .class);
        return new VendorInfo(vendorId, application.getBceUserId(),
                                                       VendorStatus.PROCESSING,
                                                       application.getCompany(),
                                                       application.getWebsite(),
                                                       application.getCapital(),
                                                       application.getAddress(),
                                                       application.getTelephone(),
                                                       application.getServiceCategory(),
                                                       application.getHotline(),
                                                       application.getMarket(),
                                                       application.getContactInfo());
    }
}
