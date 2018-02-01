/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;

import lombok.Data;

/**
 * 服务商收款信息
 * Created by chenxiang05@baidu.com on 2018/2/1.
 */
@Data
public class VendorPayee {
    // 服务商id
    private String vendorId;

    // 服务商公司所在省份
    private String companyLocationProvince;

    // 服务商公司所在城市
    private String companyLocationCity;

    // 服务商开户行名称
    private String bankName;

    // 服务商开户行支行名称
    private String branchBankName;

    // 银行卡号
    private String bankCardNumber;

    // 开户行省份
    private String bankLocationProvince;

    // 开户行所在城市
    private String bankLocationCity;

    // 有效标记位
    private boolean valid;

    private Timestamp createTime;

    private Timestamp updateTime;
}
