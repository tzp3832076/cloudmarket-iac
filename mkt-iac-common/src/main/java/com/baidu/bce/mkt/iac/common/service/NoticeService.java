/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopMapper;
import com.baidu.bce.mkt.iac.common.model.VendorServiceInfoModel;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.m .
 */
@Slf4j
@Service
public class NoticeService {
    @Autowired
    private VendorInfoMapper vendorInfoMapper;
    @Autowired
    private VendorShopDraftMapper shopDraftMapper;
    @Autowired
    private VendorShopMapper vendorShopMapper;

    public void auditNoticeApplication(String status, VendorInfo vendorInfo) {
        if (InfoStatus.PASS.name().equals(status)) {
            VendorInfo temp = vendorInfo == null
                                      ? null : vendorInfoMapper.getVendorInfoByVendorId(vendorInfo.getVendorId());
            if (temp == null) {
                vendorInfoMapper.add(vendorInfo);
            } else {
                log.warn("auditNoticeApplication is exist. vendorId {}", vendorInfo.getVendorId());
            }
        }
    }

    @Transactional
    public void auditNoticeVendorShop(String status, String vendorId) {
        VendorShopDraft vendorShopDraft = shopDraftMapper.getShopDraftByVendorId(vendorId);
        if (InfoStatus.PASS.name().equals(status)) {
            VendorShopAuditContent.ShopDraft content = JsonUtils.fromJson(vendorShopDraft.getContent(),
                    VendorShopAuditContent.ShopDraft.class);
            VendorShop vendorShop = getVendorShopFromContent(content, vendorShopDraft);
            if (vendorShopMapper.getVendorShopByVendorId(vendorId) == null) {
                vendorShopMapper.add(vendorShop);
            } else {
                vendorShopMapper.updateVendorShop(vendorShop);
            }
            vendorInfoMapper.updateWalletId(vendorId, content.getBaiduWalletAccount());
        }
        shopDraftMapper.updateShopAuditIdAndStatus(vendorId, vendorShopDraft.getAuditId(),
                InfoStatus.valueOf(status));
    }

    private VendorShop getVendorShopFromContent(VendorShopAuditContent.ShopDraft shopDraft,
                                                VendorShopDraft vendorShopDraft) {
        VendorShop shop = new VendorShop();
        shop.setEmail(shopDraft.getServiceEmail());
        shop.setCellphone(shopDraft.getServicePhone());
        shop.setIntro(shopDraft.getCompanyDescription());
        shop.setName(shopDraft.getCompanyName());
        shop.setVendorId(vendorShopDraft.getVendorId());
        VendorServiceInfoModel serviceInfoModel = new VendorServiceInfoModel();
        serviceInfoModel.setServiceAvailTime(shopDraft.getServiceAvailTime());
        serviceInfoModel.setOnlineSupports(shopDraft.getBaiduQiaos());
        shop.setServiceInfo(JsonUtils.toJson(serviceInfoModel));
        return shop;
    }
}
