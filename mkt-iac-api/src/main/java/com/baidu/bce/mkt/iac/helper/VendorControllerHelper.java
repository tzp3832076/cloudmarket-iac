/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.baidu.bce.internalsdk.mkt.iac.model.OnlineSupport;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.utils.CheckUtils;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@ControllerHelper
public class VendorControllerHelper {

    public VendorShopAuditContent  toShopAuditContent(ShopDraftSaveRequest request) {
        checkShopDraftSaveRequest(request);
        VendorShopAuditContent content = new VendorShopAuditContent();
        content.setCellphone(request.getCellphone());
        content.setEmail(request.getEmail());
        content.setIntro(request.getIntro());
        List<VendorShopAuditContent.CustomerService> customerServices = new ArrayList<>();
        for (OnlineSupport onlineSupport : request.getOnlineSupports()) {
            customerServices.add(new VendorShopAuditContent.CustomerService(
                    onlineSupport.getTitle(), onlineSupport.getUrl()));
        }
        content.setCustomerServices(customerServices);
        content.setServiceTime(request.getServiceTime());
        content.setWalletId(request.getWalletId());
        return content;
    }

    public ShopDraftDetailResponse toShopDraftDetailResponse(ShopDraftContentAndStatus
                                                                     contentAndStatus) {
        ShopDraftDetailResponse response = new ShopDraftDetailResponse();
        response.setStatus(contentAndStatus.getStatus().name());
        VendorShopAuditContent content = contentAndStatus.getContent();
        response.setCellphone(content.getCellphone());
        response.setWalletId(content.getWalletId());
        response.setEmail(content.getEmail());
        response.setIntro(content.getIntro());
        response.setName(content.getName());
        response.setServiceTime(content.getServiceTime());
        List<OnlineSupport> onlineSupports = new ArrayList<>();
        for (VendorShopAuditContent.CustomerService customerService : content.getCustomerServices()) {
            onlineSupports.add(new OnlineSupport(customerService.getTitle(),
                                                        customerService.getUrl()));
        }
        response.setOnlineSupports(onlineSupports);
        return response;
    }

    public VendorInfoDetailResponse toVendorInfoDetailResponse(VendorInfo vendorInfo) {
        VendorInfoDetailResponse response = new VendorInfoDetailResponse();
        if (vendorInfo == null) {
            return response;
        }
        response.setCompany(vendorInfo.getCompany());
        response.setAddress(vendorInfo.getAddress());
        response.setCapital(vendorInfo.getCapital());
        response.setContactInfo(vendorInfo.getContactInfo());
        response.setHotline(vendorInfo.getHotline());
        response.setOtherMarket(vendorInfo.getOtherMarket());
        response.setTelephone(vendorInfo.getTelephone());
        response.setWebsite(vendorInfo.getWebsite());
        return response;
    }

    public VendorOverviewResponse toVendorOverviewResponse(VendorOverview vendorOverview) {
        VendorOverviewResponse response = new VendorOverviewResponse();
        VendorInfo vendorInfo = vendorOverview.getVendorInfo();
        response.setVendorDone(true);
        response.setCompanyName(vendorInfo.getCompany());
        response.setVerifyStatus(vendorOverview.getQualityStatus().name());
        response.setUserId(vendorInfo.getBceUserId());
        response.setVendorStatus(vendorInfo.getStatus().name());
        response.setVendorShopDone(vendorOverview.getVendorShop() != null);
        response.setAgreementDone(!CollectionUtils.isEmpty(vendorOverview.getVendorContractList()));
        response.setDepositDone(vendorOverview.getVendorDeposit() != null);
        response.setProductsOnSale(vendorOverview.getProductsOnSale());
        response.setProductsAuditing(vendorOverview.getProductsAuditing());
        return response;
    }

    private void checkShopDraftSaveRequest(ShopDraftSaveRequest request) {
        if (!CheckUtils.checkEmail(request.getEmail())) {
            throw MktIacExceptions.emailNotValid();
        }
        if (!CheckUtils.checkMobileNumber(request.getCellphone())) {
            throw MktIacExceptions.cellphoneNotValid();
        }
    }
}
