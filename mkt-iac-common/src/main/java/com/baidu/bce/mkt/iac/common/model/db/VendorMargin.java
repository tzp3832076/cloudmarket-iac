/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model.db;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@Data
@NoArgsConstructor
public class VendorMargin {
    private String vendorId;
    private BigDecimal targetValue;
    private BigDecimal payValue;
    private Timestamp createTime;
    private Timestamp updateTime;

    public VendorMargin(String vendorId, BigDecimal targetValue, BigDecimal payValue) {
        this.vendorId = vendorId;
        this.targetValue = targetValue;
        this.payValue = payValue;
    }

    public boolean isPayOff() {
        return payValue.compareTo(targetValue) >= 0;
    }
}
