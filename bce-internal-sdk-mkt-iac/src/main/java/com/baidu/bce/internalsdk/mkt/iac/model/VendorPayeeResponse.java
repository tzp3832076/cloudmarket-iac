/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.internalsdk.mkt.iac.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Created by chenxiang05@baidu.com on 2018/3/6.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VendorPayeeResponse {
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
}
