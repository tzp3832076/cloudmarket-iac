/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.bce.mkt.iac.common.mapper.VendorPayeeMapper;
import com.baidu.bce.mkt.iac.common.model.db.VendorPayee;

import lombok.RequiredArgsConstructor;

/**
 * VendorPayeeService
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendorPayeeService {

    private final VendorPayeeMapper vendorPayeeMapper;

    /**
     * 将服务商收款人信息同步到数据库中
     * @param newPayee
     * 需要同步的收款人信息
     */
    public void syncPayeeInfoToDb(final VendorPayee newPayee) {
        if ( vendorPayeeMapper.getVendorPayee(newPayee.getVendorId()) == null) {
            vendorPayeeMapper.save(newPayee);
        } else {
            vendorPayeeMapper.updateVendorPayee(newPayee);
        }
    }

    /**
     * 将收款人信息置于无效状态
     * @param vendorId
     * 服务商id
     */
    public void doInvalid(String vendorId) {
        vendorPayeeMapper.updateValidFlag(false, vendorId);
    }


    public void updateTaxFlag(String taxFlag, String vendorId) {
        vendorPayeeMapper.updateTaxFlag(taxFlag, vendorId);
    }


    public VendorPayee getVendorPayee(String vendorId) {
        return vendorPayeeMapper.getVendorPayee(vendorId);
    }



}
