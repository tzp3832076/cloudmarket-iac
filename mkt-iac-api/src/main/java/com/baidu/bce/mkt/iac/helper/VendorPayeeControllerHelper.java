/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.helper;

import com.baidu.bce.internalsdk.mkt.iac.model.VendorPayeeSyncRequest;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.iac.common.model.db.VendorPayee;

/**
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */

@ControllerHelper
public class VendorPayeeControllerHelper {

    public VendorPayee toVendorPayee(VendorPayeeSyncRequest request) {
        VendorPayee vendorPayee = new VendorPayee();
        vendorPayee.setVendorId(request.getVendorId());
        vendorPayee.setBankName(request.getBankName());
        vendorPayee.setBankCardNumber(request.getBankCardNumber());
        vendorPayee.setBranchBankName(request.getBranchBankName());
        vendorPayee.setCompanyLocationCity(request.getCompanyLocation().getCity());
        vendorPayee.setCompanyLocationProvince(request.getCompanyLocation().getProvince());
        vendorPayee.setBankLocationCity(request.getBankLocation().getCity());
        vendorPayee.setBankLocationProvince(request.getBankLocation().getProvince());
        vendorPayee.setValid(true);
        return vendorPayee;
    }
}
