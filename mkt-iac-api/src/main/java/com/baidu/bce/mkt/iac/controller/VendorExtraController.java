/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.mkt.iac.common.service.ContractAndDepositService;
import com.baidu.bce.mkt.iac.helper.VendorExtraHepler;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@RestController
@RequestMapping("/v1/vendorExtra")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendorExtraController {
    private final ContractAndDepositService service;
    private final VendorExtraHepler hepler;

    @ApiOperation(value = "合同list和保证金更新接口")
    @RequestMapping(value = "/{vendorId}/allInfo", method = RequestMethod.POST)
    public void contractAndDepositSubmit(@PathVariable("vendorId") String vendorId,
                                        @RequestBody ContractAndDepositSubmitRequest request) {
        service.updateDepositAndContractList(vendorId, request.getDeposit(),
                hepler.toVendorContractList(vendorId, request.getContractList()));
    }
}
