/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.helper;

import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorListResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.model.AiVendorListModel;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;
import com.baidu.bce.mkt.iac.common.utils.MBeanUtils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public AiVendorListResponse toAiVendorListResponse(AiVendorListModel listModel, String order,
                                                       String orderBy, int pageNo, int pageSize) {
        AiVendorListResponse response = new AiVendorListResponse();
        if (listModel == null || CollectionUtils.isEmpty(listModel.getAiVendorInfo())) {
            response.setTotalCount(0);
            response.setAiVendorInfoListModel(new ArrayList<>());
            return response;
        }

        response.setTotalCount(listModel.getTotalCount());

        List<AiVendorListResponse.AiVendorInfoListModel> result =listModel.getAiVendorInfo().stream().
                map(aiVendorInfo -> new AiVendorListResponse.
                        AiVendorInfoListModel(aiVendorInfo.getVendorId(), aiVendorInfo.getCompany(),
                        aiVendorInfo.getUserId(), aiVendorInfo.getCreateTime(),
                        IacConstants.AI_VENDOR_APPLICATION_AREA, IacConstants.AI_VENDOR_STATUS,
                        false)).collect(Collectors.toList());

        response.setResult(result);
        response.setOrder(order);
        response.setOrderBy(orderBy);
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        return response;
    }

}
