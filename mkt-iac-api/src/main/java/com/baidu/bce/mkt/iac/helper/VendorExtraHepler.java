/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import java.util.ArrayList;
import java.util.List;

import com.baidu.bce.internalsdk.mkt.iac.model.ContractAndDepositSubmitRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorContractResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;

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
            vendorContractList.add(new VendorContract(vendorId, contract.getNumber(),
                                                             contract.getDigest(),
                                                             contract.isDelete()));
        }
        return vendorContractList;
    }

    public VendorContractResponse toVendorContractResponse(VendorInfo vendorInfo,
                                                           List<VendorContract> contracts) {
        VendorContractResponse response = new VendorContractResponse();
        response.setVendorName(vendorInfo.getCompany());
        List<VendorContractResponse.ContractInfo> contractInfoNumList = new ArrayList<>();
        for (VendorContract contract : contracts) {
            contractInfoNumList.add(new VendorContractResponse.ContractInfo(contract.getContractNum()));
        }
        response.setContractInfoList(contractInfoNumList);
        return response;
    }
}
