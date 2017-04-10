/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model;

import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;

import lombok.Data;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Data
public class VendorOverview {
    private VendorInfo vendorInfo;
    private ProcessStatus vendorAuditStatus;
    private ProcessStatus vendorShopAuditStatus;
    private ProcessStatus agreementAuditStatus;
    private ProcessStatus depositAuditStatus;
    private AuditStatus qualityStatus;
    private String categoryInfo;
}
