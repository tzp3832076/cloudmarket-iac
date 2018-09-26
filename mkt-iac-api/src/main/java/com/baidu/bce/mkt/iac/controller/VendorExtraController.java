/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractVendorIdListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorContractResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorPhoneAndEmailResponse;
import com.baidu.bce.mkt.framework.crm.CrmService;
import com.baidu.bce.mkt.framework.exception.UnknownExceptionResponse;
import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.framework.iac.annotation.VendorId;
import com.baidu.bce.mkt.iac.common.client.IacClientFactory;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.service.ContractAndDepositService;
import com.baidu.bce.mkt.iac.common.service.VendorService;
import com.baidu.bce.mkt.iac.helper.VendorExtraHepler;
import com.baidu.bce.plat.webframework.iam.config.access.annotation.BceAuth;
import com.baidu.bce.plat.webframework.iam.model.BceRole;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@RestController
@RequestMapping("/v1/vendor")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendorExtraController {
    private final ContractAndDepositService service;
    private final VendorService vendorService;
    private final VendorExtraHepler helper;
    private final CrmService crmService;
    private final IacClientFactory iacClientFactory;

    @ApiOperation(value = "合同list和保证金更新接口 -- osp调用")
    @RequestMapping(value = "/{vendorId}/contractAndDeposit", method = RequestMethod.POST)
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_CONTRACT_DEPOSIT, operation = "update",
            instanceParameterName = "vendorId")
    @UnknownExceptionResponse(message = "合同保证金更新错误")
    public void contractAndDepositSubmit(@PathVariable("vendorId") String vendorId,
                                         @RequestBody ContractAndDepositSubmitRequest request) {
        service.updateDepositAndContractList(vendorId, request.getDeposit(),
                helper.toVendorContractList(vendorId, request.getContractList()));
    }

    @ApiOperation(value = "获取服务商合同信息和保证金信息")
    @RequestMapping(value = "/{vendorId}/contractAndDeposit", method = RequestMethod.GET)
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_CONTRACT_DEPOSIT, operation = "read",
            instanceParameterName = "vendorId")
    @UnknownExceptionResponse(message = "获取服务商合同和保证金信息失败")
    public ContractAndDepositResponse getVendorContractsAndDeposit(@PathVariable("vendorId") String
                                                                           vendorId) {
        VendorInfo vendorInfo = vendorService.getVendorInfoByVendorId(vendorId);
        List<VendorContract> vendorContractList = service.getVendorContractList(vendorId);
        VendorDeposit deposit = service.getVendorDeposit(vendorId);
        return helper.toContractAndDepositResponse(vendorInfo, vendorContractList, deposit);
    }

    @ApiOperation(value = "获取服务商电话和邮箱供发送短信和邮件")
    @RequestMapping(value = "/{vendorId}/phoneAndEmail", method = RequestMethod.GET)
    @BceAuth(role = {BceRole.SERVICE})
    @UnknownExceptionResponse(message = "获取信息失败")
    // TODO 考虑和vendorController中的接口合并处理
    public VendorPhoneAndEmailResponse getPhoneAndEmail(@PathVariable("vendorId") String vendorId) {
        VendorInfo vendorInfo = vendorService.getValidVendorInfo(vendorId);
        VendorShop vendorShop = vendorService.getVendorShopByVendorId(vendorId);
        VendorPhoneAndEmailResponse response =
                helper.toVendorPhoneAndEmailResponse(vendorShop, iacClientFactory.createCrmSensitiveClient()
                        .listSensitiveByAccountIds(Arrays.asList(vendorInfo.getBceUserId())));
        return response;
    }

    @ApiOperation(value = "获取服务商&合同号list")
    @RequestMapping(value = "/{vendorId}/contract", method = RequestMethod.GET)
//    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_CONTRACT_DEPOSIT, operation = "read",
//            instanceParameterName = "vendorId")
    @BceAuth(role = {BceRole.SERVICE})
    @UnknownExceptionResponse(message = "获取服务商合同号失败")
    public VendorContractResponse getVendorContracts(@PathVariable("vendorId") String vendorId) {
        VendorInfo vendorInfo = vendorService.getVendorInfoByVendorId(vendorId);
        List<VendorContract> vendorContractList = service.getVendorContractList(vendorId);
        return helper.toVendorContractResponse(vendorInfo, vendorContractList);
    }

    @ApiOperation(value = "获取已经填写协议号的服务商Ids")
    @RequestMapping(value = "/contract", method = RequestMethod.GET)
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_CONTRACT_DEPOSIT, operation = "getContractVendorIds")
    @UnknownExceptionResponse(message = "获取服务商Ids失败")
    public ContractVendorIdListResponse getContractVendorIds(@VendorId(required = false) List<String> vendorIds) {
        List<String> contractIds = service.getContractedVendorIdList(vendorIds);
        return helper.toVendorContractListResponse(contractIds);
    }

    @ApiOperation(value = "添加协议号")
    @RequestMapping(value = "/contract", method = RequestMethod.POST)
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_CONTRACT_DEPOSIT, operation = "addContract")
    @UnknownExceptionResponse(message = "添加协议号失败")
    public void addContract(@RequestBody ContractRequest request) {
        if (!request.getContractBeginTime().before(request.getContractEndTime())) {
            log.info("contract time is illegal");
            throw MktIacExceptions.inValidContractTime();
        }
        service.addContract(request.getVendorId(), request.getContract(),
                request.getContractBeginTime(), request.getContractEndTime());
    }
}
