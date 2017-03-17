/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.mkt.audit.internal.sdk.model.request.SubmitAuditRequest;
import com.baidu.bce.mkt.audit.internal.sdk.model.response.SubmitAuditResponse;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.client.IacClientFactory;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
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
import com.baidu.bce.mkt.iac.common.model.VendorListModel;
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
    private final IacClientFactory iacClientFactory;

    @Transactional
    public void submitShopDraft(String vendorId, VendorShopAuditContent content) {
        saveShopDraft(vendorId, content.getData());
        SubmitAuditRequest request = new SubmitAuditRequest();
        request.setData(content.getData());
        request.setMap(content.getMap());
        request.setInfoType(IacConstants.AUDIT_VENDOR_SHOP);
        request.setBaseInfo("");
        request.setSearchItems(new ArrayList<>());
        request.setInfoId(vendorId);
        VendorInfo vendorInfo = getVendorInfoByVendorId(vendorId);
        SubmitAuditResponse response = iacClientFactory.createMktAuditClient(vendorInfo.getBceUserId())
                                               .auditSubmit(request);
        shopDraftMapper.updateShopAuditIdAndStatus(vendorId, response.getAuditId(), InfoStatus.AUDIT);
    }

    public void saveShopDraft(String vendorId, VendorShopAuditContent.ShopDraft content) {
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        content.setBceAccount(vendorInfo.getBceUserId());
        content.setCompanyName(vendorInfo.getCompany());
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

    @Transactional
    public void cancelAuditShopDraft(String vendorId) {
        VendorShopDraft shopDraft = getVendorShopDraft(vendorId);
        VendorInfo vendorInfo = getVendorInfoByVendorId(vendorId);
        if (shopDraft == null) {
            log.warn("cancelAuditShopDraft shop draft is null. vendorId {}", vendorId);
        } else {
            if (InfoStatus.AUDIT.equals(shopDraft.getStatus())) {
                iacClientFactory.createMktAuditClient(vendorInfo.getBceUserId())
                        .cancelAudit(shopDraft.getAuditId());
                shopDraftMapper.updateShopAuditIdAndStatus(vendorId, "", InfoStatus.EDIT);
            }
        }
    }

    public VendorShopDraft getVendorShopDraft(String vendorId) {
        return shopDraftMapper.getShopDraftByVendorId(vendorId);
    }

    public ShopDraftContentAndStatus getShopDraftContentAndStatus(String vendorId) {
        VendorShopDraft shopDraft = getVendorShopDraft(vendorId);
        if (shopDraft == null) {
            return null; // vendorInfo 为初始状态 无店铺信息
        }
        VendorShopAuditContent.ShopDraft content = JsonUtils.fromJson(shopDraft.getContent(),
                VendorShopAuditContent.ShopDraft.class);
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
        vendorOverview.setDepositAuditStatus(getVendorDepositStatus(vendorId));
        return vendorOverview;
    }

    public Map<VendorStatus, Integer> statisticsVendorAmount() {
        Map<VendorStatus, Integer> vendorCountMap = new HashMap<>();
        vendorCountMap.put(VendorStatus.HOSTED,
                vendorInfoMapper.getVendorCountByStatus(VendorStatus.HOSTED));
        vendorCountMap.put(VendorStatus.PROCESSING,
                vendorInfoMapper.getVendorCountByStatus(VendorStatus.PROCESSING));
        return vendorCountMap;
    }

    public void updateVendorStatus(String vendorId, String status) {
        VendorStatus vendorStatus = VendorStatus.valueOf(status);
        vendorInfoMapper.updateVendorStatus(vendorId, vendorStatus);
    }

    public VendorListModel getVendorList(String bceUserId, String companyName,
                                         int start, int limit) {
        bceUserId = StringUtils.isEmpty(bceUserId) ? null : bceUserId;
        companyName = StringUtils.isEmpty(companyName) ? null : companyName;
        List<VendorInfo> vendorInfos = vendorInfoMapper.getVendorList(VendorStatus.HOSTED,
                bceUserId, companyName, start, limit);
        int totalCount = vendorInfoMapper.getVendorCount(VendorStatus.HOSTED,
                bceUserId, companyName);
        VendorListModel vendorListModel = new VendorListModel();
        vendorListModel.setTotalCount(totalCount);
        vendorListModel.setVendorInfoList(vendorInfos);
        return vendorListModel;
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

    private ProcessStatus getVendorDepositStatus(String vendorId) {
        VendorDeposit deposit = depositMapper.getVendorDeposit(vendorId);
        if (deposit == null) {
            return ProcessStatus.TODO;
        } else {
            return deposit.isPayOff() ? ProcessStatus.DONE : ProcessStatus.TODO;
        }
    }

}
