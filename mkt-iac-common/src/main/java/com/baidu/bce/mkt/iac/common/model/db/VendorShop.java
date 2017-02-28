/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;

import lombok.Data;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Data
public class VendorShop {
    private String vendorId;
    private String name;
    private String intro;
    private String email;
    private String cellphone;
    private String serviceInfo;
    private Timestamp createTime;
    private Timestamp updateTime;

    public VendorShop(String vendorId, String name, String intro, String email, String cellphone,
                      String serviceInfo) {
        this.vendorId = vendorId;
        this.name = name;
        this.intro = intro;
        this.email = email;
        this.cellphone = cellphone;
        this.serviceInfo = serviceInfo;
    }
}
