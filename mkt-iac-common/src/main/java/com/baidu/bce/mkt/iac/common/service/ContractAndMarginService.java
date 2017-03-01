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
import com.baidu.bce.mkt.iac.common.mapper.VendorMarginMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorMargin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ContractAndMarginService {
    private final VendorContractMapper contractMapper;
    private final VendorMarginMapper marginMapper;
    private final NoticeSenderHandler noticeSenderHandler;

    /**
     * vendor Margin
     */
    @Transactional
    public void updateVendorMargin(String vendorId, BigDecimal payValue) {
        VendorMargin vendorMargin = new VendorMargin(vendorId, IacConstants.DEFAULT_MARGIN,
                                                            payValue);
        if (getVendorMargin(vendorId) == null) {
            marginMapper.add(vendorMargin);
        } else {
            marginMapper.update(vendorMargin);
        }
        noticeSenderHandler.noticeAuditMarginPayOff(vendorId, vendorMargin.isPayOff());
    }

    public VendorMargin getVendorMargin(String vendorId) {
        return marginMapper.getVendorMargin(vendorId);
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
