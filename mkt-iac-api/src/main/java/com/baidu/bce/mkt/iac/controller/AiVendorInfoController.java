/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.controller;

import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorListResponse;
import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.model.AiVendorListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorInfoResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.IdResponse;
import com.baidu.bce.mkt.framework.exception.UnknownExceptionResponse;
import com.baidu.bce.mkt.iac.common.service.AiVendorInfoService;
import com.baidu.bce.mkt.iac.helper.AiVendorInfoControllerHelper;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Min;

/**
 * Created by chenxiang05@baidu.com on 2018/8/21.
 */

@RestController
@RequestMapping("/v1/ai/vendor")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AiVendorInfoController {

    @Autowired
    private AiVendorInfoService aiVendorInfoService;

    @Autowired
    private AiVendorInfoControllerHelper controllerHelper;

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value = "AI服务商信息同步接口")
    @UnknownExceptionResponse(message = "AI服务商信息同步失败")
    public IdResponse addAiVendor(@RequestBody AiVendorInfoRequest aiVendorInfoRequest) {
        String vendorId = aiVendorInfoService.newVendor(controllerHelper.fromAiVendorInfoRequest(aiVendorInfoRequest));
        IdResponse response = new IdResponse();
        response.setId(vendorId);
        return response;
    }

    @RequestMapping(value = "/{vendorId}", method = RequestMethod.GET)
    @ApiOperation(value = "AI服务商信息获取")
    @UnknownExceptionResponse(message = "AI服务商信息获取 失败")
    public AiVendorInfoResponse getAiVendor(@PathVariable String vendorId) {
        return controllerHelper.toAiVendorInfoResponse(aiVendorInfoService.getByVendorId(vendorId));
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiOperation(value = "AI服务商信息更新")
    @UnknownExceptionResponse(message = "AI服务商信息更新失败")
    public void updateAiVendor(@RequestBody AiVendorInfoRequest aiVendorInfoRequest) {
        aiVendorInfoService.updateVendorInfo(controllerHelper.fromAiVendorInfoRequest(aiVendorInfoRequest));
    }

    @RequestMapping(value = "/api_vendor_list", method = RequestMethod.GET)
    @ApiOperation(value = "osp端AI服务商信息列表获取")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read")
    @UnknownExceptionResponse(message = "AI服务商信息列表获取失败")
    public AiVendorListResponse getAiVendorList(@RequestParam(value = "keyword", required = false)
                                                        String keyword,
                                                @RequestParam(value = "order", required = false)
                                                        String order,
                                                @RequestParam(value = "orderBy", required = true)
                                                        String orderBy,
                                                @Min(1) @RequestParam int pageNo,
                                                @Min(1) @RequestParam int pageSize) {

        AiVendorListModel applicantList = aiVendorInfoService.getApplicantList(keyword, order, orderBy, pageNo, pageSize);
        return controllerHelper.toAiVendorListResponse(applicantList, order, orderBy, pageNo, pageSize);
    }

    @ApiOperation(value = "osp上服务商list页面中服务商信息获取接口")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read")
    @UnknownExceptionResponse(message = "服务商list页面获取失败")
    public AiVendorListResponse getVendorList( @RequestParam(value = "companyName", required = false)
                                                     String companyName,
                                             @Min(1) @RequestParam int pageNo,
                                             @Min(1) @RequestParam int pageSize) {
        AiVendorListModel vendorListModel = aiVendorInfoService.getApplicantList(companyName, null, null, pageNo, pageSize);
        return controllerHelper.toAiVendorListResponse(vendorListModel, null, null, pageNo, pageSize);
    }

}
