package com.baidu.bce.mkt.iac.helper;

import com.baidu.bce.internalsdk.mkt.iac.model.AiVendorContractResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ContractAiVendorIdListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorContractResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorContract;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-25.
 */

@ControllerHelper
public class AiVendorExtraHelper {

    public ContractAiVendorIdListResponse toAiVendorContractListResponse(List<String> contractVendorIds) {
        ContractAiVendorIdListResponse response = new ContractAiVendorIdListResponse();
        List<String> vendorIds = new ArrayList<>();
        if (!CollectionUtils.isEmpty(contractVendorIds)) {
            vendorIds = contractVendorIds;
        }
        response.setVendorIds(vendorIds);
        return response;
    }

    public AiVendorContractResponse toVendorContractResponse(AiVendorInfo vendorInfo,
                                                             List<AiVendorContract> contracts) {
        AiVendorContractResponse response = new AiVendorContractResponse();
        if (vendorInfo == null) {
            return response;
        }
        response.setVendorName(vendorInfo.getCompany());
        List<AiVendorContractResponse.AiContractInfo> contractInfoNumList = new ArrayList<>();
        for (AiVendorContract contract : contracts) {
            contractInfoNumList.add(new AiVendorContractResponse.AiContractInfo(
                    contract.getContractNum(), contract.getCustomerNum(),
                    contract.getBeginTime(), contract.getEndTime()));
        }
        response.setContractInfoList(contractInfoNumList);
        return response;
    }
}
