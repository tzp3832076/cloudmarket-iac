/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.model;

import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;

import lombok.Data;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
@Data
public class ShopDraftContentAndStatus {
    private InfoStatus status;
    private VendorShopAuditContent.ShopDraft content;
}
