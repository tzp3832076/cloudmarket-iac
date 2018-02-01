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

import com.baidu.bce.internalsdk.mkt.iac.model.VendorPayeeSyncRequest;
import com.baidu.bce.mkt.framework.exception.UnknownExceptionResponse;
import com.baidu.bce.mkt.iac.common.model.db.VendorPayee;
import com.baidu.bce.mkt.iac.common.service.VendorPayeeService;
import com.baidu.bce.mkt.iac.helper.VendorPayeeControllerHelper;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */
@RestController
@RequestMapping("/v1/vendor/payee")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendorPayeeController {

    private final VendorPayeeService vendorPayeeService;
    private final VendorPayeeControllerHelper controllerHelper;

    @RequestMapping(method = RequestMethod.POST, params = {"sync"})
    @ApiOperation(value = "服务商收款人信息同步接口")
    @UnknownExceptionResponse(message = "服务商收款人信息同步失败")
    public void syncVendorPayeeInfo(@RequestBody VendorPayeeSyncRequest request) {
        VendorPayee vendorPayee = controllerHelper.toVendorPayee(request);
        vendorPayeeService.syncPayeeInfoToDb(vendorPayee);
    }


    @RequestMapping(value = "/{vendorId}", method = RequestMethod.POST, params = {"invalid"})
    @ApiOperation(value = "服务商重新提交审核是调用，将收款人信息置为不可用")
    @UnknownExceptionResponse(message = "服务商收款人信息同步失败")
    public void doInvalid(@PathVariable("vendorId") String vendorId) {
        vendorPayeeService.doInvalid(vendorId);
    }

}
