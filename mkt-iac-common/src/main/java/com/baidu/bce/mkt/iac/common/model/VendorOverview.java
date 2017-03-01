/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model;

import java.util.List;

import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;

import lombok.Data;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Data
public class VendorOverview {
    private VendorInfo vendorInfo;
    private VendorShop vendorShop;
    private List<VendorContract> vendorContractList;
    private VendorDeposit vendorDeposit;
    private AuditStatus qualityStatus;
    private int productsOnSale;
    private int productsAuditing;
}
