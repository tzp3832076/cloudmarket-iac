/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.baidu.bce.internalsdk.mkt.iac.model.OnlineSupport;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.exception.MktIacExceptions;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorInfoContacts;
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
        content.setCellphone(request.getServicePhone());
        content.setEmail(request.getServiceEmail());
        content.setIntro(request.getCompanyDescription());
        List<VendorShopAuditContent.CustomerService> customerServices = new ArrayList<>();
        for (OnlineSupport onlineSupport : request.getBaiduQiaos()) {
            customerServices.add(new VendorShopAuditContent.CustomerService(
                    onlineSupport.getName(), onlineSupport.getLink()));
        }
        content.setCustomerServices(customerServices);
        content.setServiceTime(request.getServiceAvailTime());
        content.setWalletId(request.getBaiduWalletAccount());
        return content;
    }

    public ShopDraftDetailResponse toShopDraftDetailResponse(ShopDraftContentAndStatus
                                                                     contentAndStatus) {
        ShopDraftDetailResponse response = new ShopDraftDetailResponse();
        response.setStatus(contentAndStatus.getStatus().name());
        VendorShopAuditContent content = contentAndStatus.getContent();
        response.setServicePhone(content.getCellphone());
        response.setBaiduWalletAccount(content.getWalletId());
        response.setServiceEmail(content.getEmail());
        response.setCompanyDescription(content.getIntro());
        response.setCompanyName(content.getName());
        response.setServiceAvailTime(content.getServiceTime());
        List<OnlineSupport> onlineSupports = new ArrayList<>();
        for (VendorShopAuditContent.CustomerService customerService : content.getCustomerServices()) {
            onlineSupports.add(new OnlineSupport(customerService.getTitle(),
                                                        customerService.getUrl()));
        }
        response.setBaiduQiaos(onlineSupports);
        return response;
    }

    public VendorInfoDetailResponse toVendorInfoDetailResponse(VendorInfo vendorInfo) {
        VendorInfoDetailResponse response = new VendorInfoDetailResponse();
        if (vendorInfo == null) {
            return response;
        }
        response.setCompanyName(vendorInfo.getCompany());
        response.setCompanyCapital(vendorInfo.getCapital());
        response.setServiceHotline(vendorInfo.getHotline());
        response.setJoinedOtherMarkets(vendorInfo.getOtherMarket());
        response.setCompanyPhone(vendorInfo.getTelephone());
        response.setCompanySite(vendorInfo.getWebsite());
        response.setCompanyAddress(vendorInfo.getAddress());
        VendorInfoContacts contacts = JsonUtils.fromJson(vendorInfo.getContactInfo(),
                VendorInfoContacts.class);
        Map<VendorInfoContacts.ContactType, VendorInfoContacts.ContactWay> contactWayMap =
                getVendorContactMap(contacts.getContractList());
        response.setBizContact(contactWayMap.get(VendorInfoContacts.ContactType.Business).getName());
        response.setBizContactPhone(contactWayMap.get(VendorInfoContacts.ContactType.Business).getPhone());
        response.setEmerContact(contactWayMap.get(VendorInfoContacts.ContactType.Emergency).getName());
        response.setEmerContactPhone(contactWayMap.get(VendorInfoContacts.ContactType.Emergency).getPhone());
        response.setTechContact(contactWayMap.get(VendorInfoContacts.ContactType.Technical).getName());
        response.setTechContactPhone(contactWayMap.get(VendorInfoContacts.ContactType.Technical).getPhone());
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
        if (!CheckUtils.checkEmail(request.getServiceEmail())) {
            throw MktIacExceptions.emailNotValid();
        }
        if (!CheckUtils.checkMobileNumber(request.getServicePhone())) {
            throw MktIacExceptions.cellphoneNotValid();
        }
    }

    private Map<VendorInfoContacts.ContactType, VendorInfoContacts.ContactWay> getVendorContactMap(
            List<VendorInfoContacts.ContactWay> contactWayList) {
        Map<VendorInfoContacts.ContactType, VendorInfoContacts.ContactWay> contactWayMap = new HashMap<>();
        for (VendorInfoContacts.ContactWay contactWay : contactWayList) {
            contactWayMap.put(contactWay.getType(), contactWay);
        }
        return contactWayMap;
    }
}
