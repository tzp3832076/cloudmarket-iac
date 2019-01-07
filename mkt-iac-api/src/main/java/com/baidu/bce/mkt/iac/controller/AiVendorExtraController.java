package com.baidu.bce.mkt.iac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorContractResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAiVendorIdListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractRequest;
import com.baidu.bce.mkt.framework.exception.UnknownExceptionResponse;
import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.framework.iac.annotation.VendorId;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorContract;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;
import com.baidu.bce.mkt.iac.common.service.AiVendorExtraService;
import com.baidu.bce.mkt.iac.common.service.AiVendorInfoService;
import com.baidu.bce.mkt.iac.helper.AiVendorExtraHelper;
import com.baidu.bce.plat.webframework.iam.config.access.annotation.BceAuth;
import com.baidu.bce.plat.webframework.iam.model.BceRole;
import com.wordnik.swagger.annotations.ApiOperation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * ai服务商协议信息
 *
 * Created by v_zhouhuikun@baidu.com on 2018-12-25.
 */

@RestController
@RequestMapping("/v1/ai/vendor")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AiVendorExtraController {

    private final AiVendorExtraService aiVendorExtraService;
    private final AiVendorInfoService aiVendorInfoService;
    private final AiVendorExtraHelper helper;

    @ApiOperation(value = "获取Ai服务商&合同号list")
    @RequestMapping(value = "/{vendorId}/contract", method = RequestMethod.GET)
    @BceAuth(role = {BceRole.SERVICE})
    @UnknownExceptionResponse(message = "获取Ai服务商合同号失败")
    public AiVendorContractResponse getAiVendorContracts(@PathVariable("vendorId") String vendorId) {
        AiVendorInfo vendorInfo = aiVendorInfoService.getByVendorId(vendorId);
        List<AiVendorContract> vendorContractList = aiVendorExtraService.getAiVendorContractList(vendorId);
        return helper.toVendorContractResponse(vendorInfo, vendorContractList);
    }

    @ApiOperation(value = "获取已经填写合同协议的ai服务商ID列表")
    @RequestMapping(value = "/contract", method = RequestMethod.GET)
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_CONTRACT_DEPOSIT, operation = "getContractVendorIds")
    @UnknownExceptionResponse(message = "获取已经填写合同协议的ai服务商ID列表失败")
    public ContractAiVendorIdListResponse getContractAiVendorIds(@VendorId(required = false) List<String> vendorIds) {
        List<String> contractIds = aiVendorExtraService.getContractedAiVendorIdList(vendorIds);
        return helper.toAiVendorContractListResponse(contractIds);
    }

    @ApiOperation(value = "添加合同协议")
    @RequestMapping(value = "/contract", method = RequestMethod.POST)
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_CONTRACT_DEPOSIT, operation = "addContract")
    @UnknownExceptionResponse(message = "添加合同失败")
    public void addContract(@RequestBody ContractRequest request) {

        if (!request.getContractBeginTime().before(request.getContractEndTime())) {
            log.info("contract time is illegal");
            throw MktIacExceptions.inValidContractTime();

        }
        aiVendorExtraService.addContract(request.getVendorId(), request.getContract(), request.getCustomer(),
                request.getContractBeginTime(), request.getContractEndTime());
    }
}
