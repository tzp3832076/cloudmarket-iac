/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@Data
@NoArgsConstructor
public class VendorContract {
    private int id;
    private String vendorId;
    private String contractNum;
    private String customerNum;
    private String contractDigest;
    private Timestamp beginTime;
    private Timestamp endTime;
    private Timestamp createTime;
    private Timestamp updateTime;
    private boolean isDelete;

    public VendorContract(String vendorId, String contractNum, String customerNum, String contractDigest,
                          Timestamp beginTime, Timestamp endTime) {
        this(vendorId, contractNum, customerNum, contractDigest, beginTime, endTime, false);
    }

    public VendorContract(String vendorId, String contractNum, String customerNum, String contractDigest,
                          Timestamp beginTime, Timestamp endTime, boolean delete) {
        this.vendorId = vendorId;
        this.contractNum = contractNum;
        this.customerNum = customerNum;
        this.contractDigest = contractDigest;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.isDelete = delete;
    }
}
