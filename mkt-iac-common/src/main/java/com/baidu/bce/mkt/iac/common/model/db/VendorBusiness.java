/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.model.db;

import lombok.Getter;

/**
 * Created by wangbin33@baidu.com on 2018/4/15.
 */
public enum VendorBusiness {
    INFRA_CLOUD("基础云"),
    BIG_DATA("大数据"),
    IOT("物联网"),
    SAAS("SaaS服务"),
    OP("运维管理"),
    API("API"),
    AI("AI"),
    BLOCK_CHAIN("区块链"),
    OTHER("其它"),
    UNDEFINED("未定义");

    @Getter
    private String vendorBusinessDes;

    VendorBusiness(String vendorBusinessDes) {
        this.vendorBusinessDes = vendorBusinessDes;
    }
}
