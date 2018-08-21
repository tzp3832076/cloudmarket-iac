/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.helper;

import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;
import com.baidu.bce.mkt.iac.common.utils.MBeanUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenxiang05@baidu.com on 2018/8/21.
 */

@ControllerHelper
@Slf4j
public class AiVendorInfoControllerHelper {

    public AiVendorInfoResponse toAiVendorInfoResponse(AiVendorInfo aiVendorInfo) {
        AiVendorInfoResponse response = new AiVendorInfoResponse();
        MBeanUtils.applyProperties(response, aiVendorInfo);
        return response;
    }

    public AiVendorInfo fromAiVendorInfoRequest(AiVendorInfoRequest request) {
        AiVendorInfo aiVendorInfo = new AiVendorInfo();
        MBeanUtils.applyProperties(aiVendorInfo, request);
        return aiVendorInfo;
    }

}
