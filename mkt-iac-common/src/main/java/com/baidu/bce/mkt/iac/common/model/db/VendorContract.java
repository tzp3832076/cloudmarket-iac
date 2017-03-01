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
    private String contractDigest;
    private Timestamp createTime;
    private Timestamp updateTime;
    private boolean isDelete;

    public VendorContract(String vendorId, String contractNum, String contractDigest) {
        this(vendorId, contractNum, contractDigest, false);
    }

    public VendorContract(String vendorId, String contractNum, String contractDigest,
                          boolean delete) {
        this.vendorId = vendorId;
        this.contractNum = contractNum;
        this.contractDigest = contractDigest;
        this.isDelete = delete;
    }
}
