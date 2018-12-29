/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Created on 2018/12/26 by v_zhouhuikun@baidu.com .
 */
@Data
@NoArgsConstructor
public class AiVendorContract {
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

    public AiVendorContract(String vendorId, String contractNum, String customerNum, String contractDigest,
                            Timestamp beginTime, Timestamp endTime) {
        this(vendorId, contractNum, customerNum, contractDigest, beginTime, endTime, false);
    }

    public AiVendorContract(String vendorId, String contractNum, String customerNum, String contractDigest,
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
