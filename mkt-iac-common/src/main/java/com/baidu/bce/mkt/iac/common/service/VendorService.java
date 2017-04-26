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
import com.baidu.bce.mkt.iac.common.handler.QualityHandler;
import com.baidu.bce.mkt.iac.common.handler.SyncHandler;
import com.baidu.bce.mkt.iac.common.mapper.AccountMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorContractMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorDepositMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorInfoMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopDraftMapper;
import com.baidu.bce.mkt.iac.common.mapper.VendorShopMapper;
import com.baidu.bce.mkt.iac.common.model.ProcessStatus;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorListFilter;
import com.baidu.bce.mkt.iac.common.model.VendorListModel;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorServiceInfoModel;
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
    private final IacClientFactory iacClientFactory;
    private final SyncHandler syncHandler;
    private final AccountMapper accountMapper;

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

    public void editAuditShopDraft(String vendorId) {
        VendorShopDraft shopDraft = getVendorShopDraft(vendorId);
        if (shopDraft == null) {
            log.warn("cancelAuditShopDraft shop draft is null. vendorId {}", vendorId);
        } else {
            if (InfoStatus.AUDIT.equals(shopDraft.getStatus())) {
                throw MktIacExceptions.statusInAudit();
            }
            shopDraftMapper.updateShopAuditIdAndStatus(vendorId, "", InfoStatus.EDIT);
        }
    }

    public VendorShopDraft getVendorShopDraft(String vendorId) {
        return shopDraftMapper.getShopDraftByVendorId(vendorId);
    }

    public ShopDraftContentAndStatus getShopDraftContentAndStatus(String vendorId) {
        VendorShopDraft shopDraft = getVendorShopDraft(vendorId);
        VendorInfo vendorInfo = getVendorInfoByVendorId(vendorId);
        ShopDraftContentAndStatus contentAndStatus = new ShopDraftContentAndStatus();
        VendorShopAuditContent.ShopDraft content;
        if (shopDraft == null) {
            content = new VendorShopAuditContent.ShopDraft();
            content.setBaiduWalletAccount(vendorInfo.getWalletId());
            VendorShop vendorShop = vendorShopMapper.getVendorShopByVendorId(vendorId);
            if (vendorShop != null) {
                content.setServicePhone(vendorShop.getServiceInfo());
                content.setServiceEmail(vendorShop.getEmail());
                content.setCompanyDescription(vendorShop.getIntro());
                VendorServiceInfoModel serviceInfoModel = JsonUtils.fromJson(
                        vendorShop.getServiceInfo(), VendorServiceInfoModel.class);
                if (serviceInfoModel != null) {
                    content.setServiceAvailTime(serviceInfoModel.getServiceAvailTime());
                    content.setBaiduQiaos(serviceInfoModel.getOnlineSupports());
                }
            }
        } else {
            content = JsonUtils.fromJson(shopDraft.getContent(),
                    VendorShopAuditContent.ShopDraft.class);
        }
        content.setCompanyName(vendorInfo.getCompany());
        content.setBceAccount(vendorInfo.getBceUserId());
        contentAndStatus.setContent(content);
        contentAndStatus.setStatus(shopDraft == null ? InfoStatus.EDIT : shopDraft.getStatus());
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

    public VendorShop getVendorShopByVendorId(String vendorId) {
        VendorShop shop =  vendorShopMapper.getVendorShopByVendorId(vendorId);
        if (shop == null) {
            throw MktIacExceptions.noVendorShopInfo();
        }
        return shop;
    }

    /**
     * vendorInfo
     */
    public VendorInfo getVendorInfoByVendorId(String vendorId) {
        return vendorInfoMapper.getVendorInfoByVendorId(vendorId);
    }

    public VendorInfo getValidVendorInfo(String vendorId) {
        VendorInfo vendorInfo = getVendorInfoByVendorId(vendorId);
        if (vendorInfo == null) {
            throw MktIacExceptions.noVendorInfo();
        }
        return vendorInfo;
    }

    public VendorInfo getVendorInfoByUserId(String userId) {
        return vendorInfoMapper.getVendorInfoByBceUserId(userId);
    }

    public void signAgreement(String vendorId) {
        vendorInfoMapper.updateAgreementStatus(vendorId, ProcessStatus.DONE);
        VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
        if (VendorStatus.PROCESSING.equals(vendorInfo.getStatus())) {
            syncHandler.noticeAuditContractStatus(vendorId, true);
        }
    }

    public VendorOverview getVendorOverview(String vendorId) {
        VendorOverview vendorOverview = new VendorOverview();
        VendorInfo vendorInfo = getVendorInfoByVendorId(vendorId);
        VendorShop vendorShop = vendorShopMapper.getVendorShopByVendorId(vendorId);
        VendorShopDraft vendorShopDraft = shopDraftMapper.getShopDraftByVendorId(vendorId);
        List<VendorContract> contracts = contractMapper.getVendorContractList(vendorId);
        AuditStatus qualityStatus = qualityHandler.getQualityStatus(vendorInfo.getBceUserId());
        vendorOverview.setVendorInfo(vendorInfo);
        vendorOverview.setQualityStatus(qualityStatus);
        vendorOverview.setVendorAuditStatus(ProcessStatus.DONE); // 在console页面的一定是完成入驻
        vendorOverview.setVendorShopAuditStatus(getVendorShopAuditStatus(vendorShop, vendorShopDraft));
        vendorOverview.setAgreementAuditStatus(vendorInfo.getAgreementStatus());
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

    @Transactional
    public void updateVendorStatus(String vendorId, String status) {
        VendorStatus vendorStatus = VendorStatus.valueOf(status);
        vendorInfoMapper.updateVendorStatus(vendorId, vendorStatus);
        if (VendorStatus.HOSTED.equals(vendorStatus)) {
            VendorInfo vendorInfo = vendorInfoMapper.getVendorInfoByVendorId(vendorId);
            accountMapper.updateAccountRole(vendorInfo.getBceUserId(), IacConstants.ROLE_VENDOR);
        }
    }

    public VendorListModel getVendorList(String bceUserId, String companyName,
                                         int start, int limit, VendorStatus vendorStatus) {
        VendorListFilter filter = new VendorListFilter(bceUserId, companyName, vendorStatus);
        int totalCount = vendorInfoMapper.getVendorCount(filter);
        VendorListModel vendorListModel = new VendorListModel();
        if (totalCount > 0) {
            List<VendorInfo> vendorInfos = vendorInfoMapper.getVendorList(filter, start, limit);
            vendorListModel.setTotalCount(totalCount);
            vendorListModel.setVendorInfoList(vendorInfos);
        }
        return vendorListModel;
    }

    public VendorListModel getVendorListByIds(List<String> vendorIds) {
        if (CollectionUtils.isEmpty(vendorIds)) {
            return new VendorListModel(new ArrayList<>(), 0);
        }
        List<VendorInfo> vendorInfos = vendorInfoMapper.getVendorListByIds(vendorIds);
        return new VendorListModel(vendorInfos, vendorInfos.size());
    }

    private ProcessStatus getVendorShopAuditStatus(VendorShop vendorShop,
                                                   VendorShopDraft shopDraft) {
        if (vendorShop != null) {
            return ProcessStatus.DONE;
        } else {
            if (shopDraft == null) {
                return ProcessStatus.TODO;
            }
            return InfoStatus.AUDIT.equals(shopDraft.getStatus()) ? ProcessStatus.AUDITING :
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
