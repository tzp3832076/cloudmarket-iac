/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.internalsdk.mkt.iac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class VendorOverviewResponse {
    private String companyName;
    // 'NONE|AUDIT|PASS|RETURN 未认证、认证中、认证成功、认证失败'
    private String verifyStatus;
    private String userId;

    // 'PROCESSING|HOSTED|FROZEN' 流程中、已入驻、已冻结
    private String vendorStatus;

    // 'VENDOR_DONE|VENDOR_SHOP_DONE|AGREEMENT_DONE|DEPOSIT_DONE'
    private String vendorAuditStatus;
    private String vendorShopAuditStatus;
    private String agreementAuditStatus;
    private String depositAuditStatus;

    public VendorOverviewResponse (VendorOverviewResponse response) {
        this.companyName = response.getCompanyName();
        this.vendorStatus = response.getVendorStatus();
        this.userId = response.getUserId();
        this.vendorStatus = response.getVendorStatus();
        this.vendorAuditStatus = response.getVendorAuditStatus();
        this.vendorShopAuditStatus = response.getVendorShopAuditStatus();
        this.agreementAuditStatus = response.getAgreementAuditStatus();
        this.depositAuditStatus = response.getDepositAuditStatus();
    }
}
