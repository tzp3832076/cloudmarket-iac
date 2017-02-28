/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Data
@NoArgsConstructor
public class VendorShopDraft {
    private String vendorId;
    private String content;
    private InfoStatus status;
    private String auditId;
    private Timestamp createTime;
    private Timestamp updateTime;

    public VendorShopDraft(String vendorId, String content) {
        this.vendorId = vendorId;
        this.content = content;
        this.status = InfoStatus.EDIT;
        this.auditId = "";
    }
}
