/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@ControllerHelper
public class VendorControllerHelper {
    public ShopDraftDetailResponse toShopDraftDetailResponse(VendorShopDraft vendorShopDraft) {
        ShopDraftDetailResponse response = new ShopDraftDetailResponse();
        response.setContent(vendorShopDraft.getContent());
        response.setStatus(vendorShopDraft.getStatus().name());
        return response;
    }
}
