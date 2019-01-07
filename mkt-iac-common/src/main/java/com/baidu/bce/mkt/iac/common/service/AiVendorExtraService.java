package com.baidu.bce.mkt.iac.common.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baidu.bce.mkt.iac.common.mapper.AiVendorContractMapper;
import com.baidu.bce.mkt.iac.common.model.db.AiVendorContract;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by v_zhouhuikun@baidu.com on 2018-12-25.
 */

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AiVendorExtraService {

    private final AiVendorContractMapper contractMapper;


    // 根据输入的vendorIds获取已经填写协议号的服务商ID
    public List<String> getContractedAiVendorIdList(List<String> vendorIds) {

        if (CollectionUtils.isEmpty(vendorIds)) {
            return new ArrayList<>();
        }
        List<AiVendorContract> contractList = contractMapper.getAiVendorContractListByIds(vendorIds);

        return contractList.stream().map(aiVendorContract -> aiVendorContract.getVendorId())
                .distinct().collect(Collectors.toList());
    }

    // 根据ai服务商id获取其协议合同信息
    public List<AiVendorContract> getAiVendorContractList(String vendorId) {
        return contractMapper.getAiVendorContractListById(vendorId);
    }

    // 添加ai服务商协议合同信息
    @Transactional
    public void addContract(String vendorId, String contractNum, String customer,
                            Timestamp beginTime, Timestamp endTime) {
        AiVendorContract contract = new AiVendorContract(vendorId, contractNum, customer,
                "", beginTime, endTime);
        AiVendorContract vendorContract = contractMapper.getAiVendorContract(
                contract.getVendorId(), contract.getContractNum());
        if (vendorContract == null) {
            contractMapper.add(contract);
        }
    }
}
