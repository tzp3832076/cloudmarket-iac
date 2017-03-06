/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.handler.ProductHandler;
import com.baidu.bce.mkt.iac.common.handler.QualityHandler;
import com.baidu.bce.mkt.iac.common.mapper.VendorContractMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorDepositMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopMapper;
import com.baidu.bce.mkt.iac.common.model.ProcessStatus;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.InfoStatus;
import com.baidu.bce.mkt.iac.common.model.db.VendorContract;
import com.baidu.bce.mkt.iac.common.model.db.VendorDeposit;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendorService {
    private final VendorShopDraftMapper shopDraftMapper;
    private final VendorInfoMapper vendorInfoMapper;
    private final VendorShopMapper vendorShopMapper;
    private final VendorContractMapper contractMapper;
    private final VendorDepositMapper depositMapper;
    private final QualityHandler qualityHandler;
    private final ProductHandler productHandler;

    @Transactional
    public void submitShopDraft(String vendorId, VendorShopAuditContent content) {
        saveShopDraft(vendorId, content);
        // todo submit audit
    }

    public void saveShopDraft(String vendorId, VendorShopAuditContent content) {
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        content.setVendorId(vendorId);
        content.setName(vendorInfo.getCompany());
        VendorShopDraft shopDraft = getVendorShopDraft(vendorId);
        if (shopDraft == null) {
            shopDraftMapper.add(new VendorShopDraft(vendorId, JsonUtils.toJson(content)));
        } else {
            if (InfoStatus.AUDIT.equals(shopDraft.getStatus())) {
                throw MktIacExceptions.statusInAudit();
            }
            shopDraftMapper.updateShopDraftContentAndStatus(vendorId, JsonUtils.toJson(content),
                    InfoStatus.EDIT);
        }
    }

    public VendorShopDraft getVendorShopDraft(String vendorId) {
        return shopDraftMapper.getShopDraftByVendorId(vendorId);
    }

    public ShopDraftContentAndStatus getShopDraftContentAndStatus(String vendorId) {
        VendorShopDraft shopDraft = getVendorShopDraft(vendorId);
        VendorShopAuditContent content = JsonUtils.fromJson(shopDraft.getContent(),
                VendorShopAuditContent.class);
        ShopDraftContentAndStatus contentAndStatus = new ShopDraftContentAndStatus();
        contentAndStatus.setContent(content);
        contentAndStatus.setStatus(shopDraft.getStatus());
        return contentAndStatus;
    }

    /**
     * vendorShop Online
     */
    public VendorShop getVendorShopByBceUserId(String bceUserId) {
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByBceUserId(bceUserId);
        if (vendorInfo == null) {
            throw MktIacExceptions.noVendorInfo();
        }
        return vendorShopMapper.getVendorShopByVendorId(vendorInfo.getVendorId());
    }

    /**
     * vendorInfo
     */
    public VendorInfo getVendorInfoByVendorId(String vendorId) {
        return vendorInfoMapper.getVendorInfoByVendorId(vendorId);
    }

    public VendorOverview getVendorOverview(String vendorId) {
        VendorOverview vendorOverview = new VendorOverview();
        VendorInfo vendorInfo = getVendorInfoByVendorId(vendorId);
        VendorShop vendorShop = vendorShopMapper.getVendorShopByVendorId(vendorId);
        VendorShopDraft vendorShopDraft = shopDraftMapper.getShopDraftByVendorId(vendorId);
        List<VendorContract> contracts = contractMapper.getVendorContractList(vendorId);
        VendorDeposit deposit = depositMapper.getVendorDeposit(vendorId);
        AuditStatus qualityStatus = qualityHandler.getQualityStatus(vendorInfo.getBceUserId());
        int productsOnSaleCount = productHandler.getProductsOnSaleCount(vendorId);
        int productsAuditingCount = productHandler.getProductsAuditingCount(vendorId);
        vendorOverview.setVendorInfo(vendorInfo);
        vendorOverview.setProductsAuditing(productsAuditingCount);
        vendorOverview.setProductsOnSale(productsOnSaleCount);
        vendorOverview.setQualityStatus(qualityStatus);
        vendorOverview.setVendorAuditStatus(ProcessStatus.DONE); // 在console页面的一定是完成入驻
        vendorOverview.setVendorShopAuditStatus(getVendorShopAuditStatus(vendorShop, vendorShopDraft));
        vendorOverview.setAgreementAuditStatus(CollectionUtils.isEmpty(contracts)
                                                       ? ProcessStatus.TODO : ProcessStatus.DONE);
        vendorOverview.setDepositAuditStatus(
                deposit.isPayOff() ? ProcessStatus.DONE : ProcessStatus.TODO);
        return vendorOverview;
    }

    public void updateVendorStatus(String vendorId, String status) {
        VendorStatus vendorStatus = VendorStatus.valueOf(status);
        vendorInfoMapper.updateVendorStatus(vendorId, vendorStatus);
    }

    private ProcessStatus getVendorShopAuditStatus(VendorShop vendorShop,
                                                   VendorShopDraft shopDraft) {
        if (vendorShop != null) {
            return ProcessStatus.DONE;
        } else {
            return InfoStatus.AUDIT.equals(shopDraft.getStatus()) ? ProcessStatus.AUDITINIG :
                                                                                                    ProcessStatus.TODO;
        }
    }

}
