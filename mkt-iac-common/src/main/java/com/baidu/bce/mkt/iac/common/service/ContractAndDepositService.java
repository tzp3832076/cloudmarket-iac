/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.handler.SyncHandler;
import com.baidu.bce.mkt.iac.common.mapper.VendorContractMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorDepositMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import scala.collection.parallel.ParIterableLike;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractAndDepositService {
    private final VendorContractMapper contractMapper;
    private final VendorDepositMapper depositMapper;
    private final VendorInfoMapper vendorInfoMapper;
    private final SyncHandler syncHandler;

    @Transactional
    public void updateDepositAndContractList(String vendorId, BigDecimal payValue,
                                             List<VendorContract> vendorContractList) {
        updateVendorDeposit(vendorId, payValue);
        updateVendorContentList(vendorId, vendorContractList);
    }

    /**
     * vendor Deposit
     */
    @Transactional
    public void updateVendorDeposit(String vendorId, BigDecimal payValue) {
        VendorDeposit vendorDeposit = new VendorDeposit(vendorId, IacConstants.DEFAULT_MARGIN,
                payValue);
        if (getVendorDeposit(vendorId) == null) {
            depositMapper.add(vendorDeposit);
        } else {
            depositMapper.update(vendorDeposit);
        }
        if (needSyncStatus(vendorId)) {
            syncHandler.noticeAuditDepositPayOff(vendorId, vendorDeposit.isPayOff());
        }
    }

    public VendorDeposit getVendorDeposit(String vendorId) {
        return depositMapper.getVendorDeposit(vendorId);
    }

    @Transactional
    public void updateVendorContentList(String vendorId, List<VendorContract> vendorContractList) {
        for (VendorContract contract : vendorContractList) {
            if (contract.isDelete()) {
                contractMapper.delete(contract.getId());
            } else {
                addContract(contract);
            }
        }
    }

    public List<VendorContract> getVendorContractList(String vendorId) {
        return contractMapper.getVendorContractListById(vendorId);
    }

    public List<VendorContract> getVendorContractList(List<String> vendorIds) {
        if (CollectionUtils.isEmpty(vendorIds)) {
            return new ArrayList<>();
        }
        return contractMapper.getVendorContractListByIds(vendorIds);
    }

    // 根据输入的vendorIds获取已经填写协议号的服务商ID
    public List<String> getContractedVendorIdList(List<String> vendorIds) {
        List<VendorContract> contractList = getVendorContractList(vendorIds);
        return contractList.stream().map(vendorContract -> vendorContract.getVendorId())
                .distinct().collect(Collectors.toList());
    }

    // 添加协议号时校验服务商的是否通过审核
    @Transactional
    public void addContract(String vendorId, String contract, Timestamp beginTime, Timestamp endTime) {
        VendorContract vendorContract = new VendorContract(vendorId, contract, "", beginTime, endTime);
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        if (ObjectUtils.isEmpty(vendorInfo)) {
            log.warn("vendor is not valid vendor,vendorId:{}", vendorId);
            throw MktIacExceptions.inValidVendorStatus();
        }
        addContract(vendorContract);
    }

    @Transactional
    public void addContract(VendorContract contract) {
        VendorContract vendorContract = contractMapper.getVendorContract(
                contract.getVendorId(), contract.getContractNum());
        if (vendorContract == null) {
            contractMapper.add(contract);
        }
    }

    private boolean needSyncStatus(String vendorId) {
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        if (vendorInfo == null) {
            throw MktIacExceptions.noVendorInfo();
        }
        return VendorStatus.PROCESSING.equals(vendorInfo.getStatus());
    }
}
