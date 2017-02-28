/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Slf4j
@Service
public class VendorService {
    @Autowired
    private VendorShopDraftMapper shopDraftMapper;

    public void saveShopDraft(String vendorId, String content) {
        VendorShopDraft shopDraft = getVendorShopDraft(vendorId);
        if (shopDraft == null) {
            shopDraftMapper.add(new VendorShopDraft(vendorId, content));
        } else {
            shopDraftMapper.updateShopDraftContentAndStatus(vendorId, content, InfoStatus.EDIT);
        }
    }

    public VendorShopDraft getVendorShopDraft(String vendorId) {
        return shopDraftMapper.getShopDraftByVendorId(vendorId);
    }
}
