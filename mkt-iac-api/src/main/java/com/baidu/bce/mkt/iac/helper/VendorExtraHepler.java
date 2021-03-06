/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractVendorIdListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorContractResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorPhoneAndEmailResponse;
import com.baidu.bce.mkt.framework.iac.client.model.response.SensitiveListResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.framework.utils.SecurityUtils;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@ControllerHelper
public class VendorExtraHepler {
    public List<VendorContract> toVendorContractList(String vendorId,
                                                     List<ContractAndDepositSubmitRequest
                                                             .Contract> contractList) {
        List<VendorContract> vendorContractList = new ArrayList<>();
        for (ContractAndDepositSubmitRequest.Contract contract : contractList) {
            vendorContractList.add(new VendorContract(vendorId,
                    SecurityUtils.stripSqlAndXss(contract.getNumber()), "",
                    SecurityUtils.stripSqlAndXss(contract.getDigest()),
                    contract.getBeginTime(),
                    contract.getEndTime(),
                    contract.isDelete()));
        }
        return vendorContractList;
    }

    public VendorContractResponse toVendorContractResponse(VendorInfo vendorInfo,
                                                           List<VendorContract> contracts) {
        VendorContractResponse response = new VendorContractResponse();
        if (vendorInfo == null) {
            return response;
        }
        response.setVendorName(vendorInfo.getCompany());
        List<VendorContractResponse.ContractInfo> contractInfoNumList = new ArrayList<>();
        for (VendorContract contract : contracts) {
            contractInfoNumList.add(new VendorContractResponse.ContractInfo(
                    contract.getContractNum(), contract.getCustomerNum(),
                    contract.getBeginTime(), contract.getEndTime()));
        }
        response.setContractInfoList(contractInfoNumList);
        return response;
    }

    public VendorPhoneAndEmailResponse toVendorPhoneAndEmailResponse(VendorShop vendorShop,
                                                                     SensitiveListResponse sensitiveListResponse) {
        VendorPhoneAndEmailResponse response = new VendorPhoneAndEmailResponse();
        response.setEmail(vendorShop.getEmail());
        List<SensitiveListResponse.CustomerResponse> customerResponses = sensitiveListResponse.getCustomerResponses();
        if (!CollectionUtils.isEmpty(customerResponses)) {
            SensitiveListResponse.CustomerResponse customerResponse = customerResponses.get(0);
            response.setPhone(customerResponse.getMobilePhone());
        }
        return response;
    }

    public ContractVendorIdListResponse toVendorContractListResponse(List<String> contractVendorIds) {
        ContractVendorIdListResponse response = new ContractVendorIdListResponse();
        List<String> vendorIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(contractVendorIds)) {
            vendorIds = contractVendorIds;
        }
        response.setVendorIds(vendorIds);
        return response;
    }

    public ContractAndDepositResponse toContractAndDepositResponse(VendorInfo vendorInfo,
                                                                   List<VendorContract> contracts,
                                                                   VendorDeposit deposit) {
        ContractAndDepositResponse response = new ContractAndDepositResponse();
        response.setBceAccount(vendorInfo.getBceUserId());
        response.setVendorName(vendorInfo.getCompany());
        List<ContractAndDepositResponse.ContractInfo> contractInfos = new ArrayList<>();
        for (VendorContract contract : contracts) {
            contractInfos.add(new ContractAndDepositResponse.ContractInfo(
                    contract.getContractNum(), contract.getContractDigest()));
        }
        response.setContractInfoList(contractInfos);
        response.setDeposit(deposit == null ? BigDecimal.ZERO : deposit.getPayValue());
        return response;
    }
}
