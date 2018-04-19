/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.model.db;

import lombok.Getter;

/**
 * Created by wangbin33@baidu.com on 2018/4/15.
 */
public enum VendorBusiness {
    InfraCloud("基础云"),
    BigData("大数据"),
    IoT("物联网"),
    SaaS("SaaS服务"),
    OP("运维管理"),
    API("API"),
    Other("其它"),
    EMPTY("未定义");

    @Getter
    private String vendorBusinessDes;

    VendorBusiness(String vendorBusinessDes) {
        this.vendorBusinessDes = vendorBusinessDes;
    }
}
