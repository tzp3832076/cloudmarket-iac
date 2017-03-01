/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.handler.NoticeSenderHandler;
import com.baidu.bce.mkt.iac.common.mapper.VendorContractMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorDepositMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractAndDepositService {
    private final VendorContractMapper contractMapper;
    private final VendorDepositMapper depositMapper;
    private final NoticeSenderHandler noticeSenderHandler;

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
        noticeSenderHandler.noticeAuditDepositPayOff(vendorId, vendorDeposit.isPayOff());
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
        List<VendorContract> contractList = contractMapper.getVendorContractList(vendorId);
        noticeSenderHandler.noticeAuditContractStatus(vendorId, contractList.isEmpty());
    }

    public void addContract(VendorContract contract) {
        VendorContract vendorContract = contractMapper.getVendorContract(
                contract.getVendorId(), contract.getContractNum());
        if (vendorContract == null) {
            contractMapper.add(contract);
        }
    }
}
