/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.handler.SyncHandler;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopMapper;
import com.baidu.bce.mkt.iac.common.model.VendorServiceInfoModel;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.Account;
import com.baidu.bce.mkt.iac.common.model.db.AccountType;
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
    private AccountMapper accountMapper;
    @Autowired
    private VendorShopDraftMapper shopDraftMapper;
    @Autowired
    private VendorShopMapper vendorShopMapper;
    @Autowired
    private SyncHandler syncHandler;

    @Transactional
    public void auditNoticeApplication(String status, VendorInfo vendorInfo) {
        if (InfoStatus.PASS.name().equals(status) && vendorInfo != null) {
            checkCompanyRepeat(vendorInfo);
            VendorInfo temp = vendorInfoMapper.getVendorInfoByVendorId(vendorInfo.getVendorId());
            if (temp == null) {
                vendorInfoMapper.add(vendorInfo);
            } else {
                log.warn("auditNoticeApplication is exist. vendorId {}", vendorInfo.getVendorId());
            }
            if (accountMapper.getByAccountId(vendorInfo.getBceUserId()) == null) {
                accountMapper.save(new Account(vendorInfo.getBceUserId(), AccountType.BCE,
                                                      IacConstants.ROLE_INIT_VENDOR, vendorInfo
                                                                                             .getVendorId()));

            }
        }
    }

    @Transactional
    public void auditNoticeVendorShop(String status, String vendorId) {
        VendorShopDraft vendorShopDraft = shopDraftMapper.getShopDraftByVendorId(vendorId);
        if (InfoStatus.PASS.name().equals(status) && vendorShopDraft != null) {
            VendorShopAuditContent.ShopDraft content = JsonUtils.fromJson(vendorShopDraft.getContent(),
                    VendorShopAuditContent.ShopDraft.class);
            if (content == null) {
                throw MktIacExceptions.notJsonFormat();
            }
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
        if (InfoStatus.PASS.equals(InfoStatus.valueOf(status))) {
            syncHandler.noticeProductToSyncVendor(vendorId);
        }
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

    private void checkCompanyRepeat(VendorInfo vendorInfo) {
        List<VendorInfo> applicantList = vendorInfoMapper.getVendorInfoByCompanyName(vendorInfo
                                                                                          .getCompany());
        if (CollectionUtils.isEmpty(applicantList)) {
            return;
        }
        if (applicantList.size() == 1) {
            if (applicantList.get(0).getBceUserId().equals(vendorInfo.getBceUserId())) {
                return;
            }
        }
        throw MktIacExceptions.companyNameRepeat();
    }
}
