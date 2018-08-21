/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

}
