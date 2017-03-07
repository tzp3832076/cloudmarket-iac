/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import static com.baidu.bae.commons.lib.utils.ReflectionHelper.getFieldValue;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.baidu.bce.internalsdk.mkt.iac.model.ParamMapModel;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorBaseContactResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorInfoContacts;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.utils.CheckUtils;
import com.baidu.bce.plat.webframework.exception.BceValidationException;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@ControllerHelper
@Slf4j
public class VendorControllerHelper {
    @Autowired
    private ParamProperties paramProperties;

    public VendorShopAuditContent.ShopDraft getShopDraftContent(ShopDraftSaveRequest request) {
        VendorShopAuditContent.ShopDraft shopDraft = new VendorShopAuditContent.ShopDraft();
        shopDraft.setBaiduQiaos(request.getBaiduQiaos());
        shopDraft.setBaiduWalletAccount(request.getBaiduWalletAccount());
        shopDraft.setCompanyDescription(request.getCompanyDescription());
        shopDraft.setServiceAvailTime(request.getServiceAvailTime());
        shopDraft.setServiceEmail(request.getServiceEmail());
        shopDraft.setServicePhone(request.getServicePhone());
        return shopDraft;
    }

    public VendorShopAuditContent toShopAuditContent(ShopDraftSaveRequest request) {
        VendorShopAuditContent content = new VendorShopAuditContent();
        VendorShopAuditContent.ShopDraft shopDraft = getShopDraftContent(request);
        content.setData(shopDraft);
        content.setMap(toParamMapModel(paramProperties.getVendorShopMap()));
        return content;
    }

    public ShopDraftDetailResponse toShopDraftDetailResponse(ShopDraftContentAndStatus
                                                                     contentAndStatus,
                                                             Map<String, String> paramMap) {
        ShopDraftDetailResponse response = new ShopDraftDetailResponse();
        ParamMapModel paramMapModel = toParamMapModel(paramMap);
        response.setMap(paramMapModel);
        ShopDraftDetailResponse.ShopDraftDetail detail = new ShopDraftDetailResponse.ShopDraftDetail();
        detail.setStatus(contentAndStatus.getStatus().name());
        VendorShopAuditContent.ShopDraft content = contentAndStatus.getContent();
        detail.setServicePhone(content.getServicePhone());
        detail.setBaiduWalletAccount(content.getBaiduWalletAccount());
        detail.setServiceEmail(content.getServiceEmail());
        detail.setCompanyDescription(content.getCompanyDescription());
        detail.setCompanyName(content.getCompanyName());
        detail.setServiceAvailTime(content.getServiceAvailTime());
        detail.setBaiduQiaos(content.getBaiduQiaos());
        response.setData(detail);
        return response;
    }

    public VendorInfoDetailResponse toVendorInfoDetailResponse(VendorInfo vendorInfo,
                                                               Map<String, String> paramMap) {
        VendorInfoDetailResponse response = new VendorInfoDetailResponse();
        response.setMap(toParamMapModel(paramMap));
        VendorInfoDetailResponse.VendorInfoDetail detail =
                new VendorInfoDetailResponse.VendorInfoDetail();
        if (vendorInfo == null) {
            response.setData(detail);
            return response;
        }
        detail.setCompanyName(vendorInfo.getCompany());
        detail.setCompanyCapital(vendorInfo.getCapital());
        detail.setServiceHotline(vendorInfo.getHotline());
        detail.setJoinedOtherMarkets(vendorInfo.getOtherMarket());
        detail.setCompanyPhone(vendorInfo.getTelephone());
        detail.setCompanySite(vendorInfo.getWebsite());
        detail.setCompanyAddress(vendorInfo.getAddress());
        VendorInfoContacts contacts = JsonUtils.fromJson(vendorInfo.getContactInfo(),
                VendorInfoContacts.class);
        Map<VendorInfoContacts.ContactType, VendorInfoContacts.ContactWay> contactWayMap =
                getVendorContactMap(contacts.getContractList());
        detail.setBizContact(
                contactWayMap.get(VendorInfoContacts.ContactType.Business).getName());
        detail.setBizContactPhone(
                contactWayMap.get(VendorInfoContacts.ContactType.Business).getPhone());
        detail.setEmerContact(
                contactWayMap.get(VendorInfoContacts.ContactType.Emergency).getName());
        detail.setEmerContactPhone(
                contactWayMap.get(VendorInfoContacts.ContactType.Emergency).getPhone());
        detail.setTechContact(
                contactWayMap.get(VendorInfoContacts.ContactType.Technical).getName());
        detail.setTechContactPhone(
                contactWayMap.get(VendorInfoContacts.ContactType.Technical).getPhone());
        response.setData(detail);
        return response;
    }

    public VendorOverviewResponse toVendorOverviewResponse(VendorOverview vendorOverview) {
        VendorOverviewResponse response = new VendorOverviewResponse();
        VendorInfo vendorInfo = vendorOverview.getVendorInfo();
        response.setCompanyName(vendorInfo.getCompany());
        response.setVerifyStatus(vendorOverview.getQualityStatus().name());
        response.setUserId(vendorInfo.getBceUserId());
        response.setVendorStatus(vendorInfo.getStatus().name());
        response.setVendorAuditStatus(vendorOverview.getVendorAuditStatus().name());
        response.setVendorShopAuditStatus(vendorOverview.getVendorShopAuditStatus().name());
        response.setAgreementAuditStatus(vendorOverview.getAgreementAuditStatus().name());
        response.setDepositAuditStatus(vendorOverview.getDepositAuditStatus().name());
        response.setProductsOnSale(vendorOverview.getProductsOnSale());
        response.setProductsAuditing(vendorOverview.getProductsAuditing());
        return response;
    }

    public VendorBaseContactResponse toVendorBaseContact(VendorShop shop) {
        VendorBaseContactResponse response = new VendorBaseContactResponse();
        if (shop != null) {
            response.setEmail(shop.getEmail());
        }
        return response;
    }
    public void checkShopDraftSaveRequest(ShopDraftSaveRequest request, boolean canBeEmpty) {
        Map<String, String> fieldMap = canBeEmpty ? new HashMap<>() : getEmptyFieldMap(request);
        if (!(canBeEmpty && StringUtils.isEmpty(request.getServiceEmail()))) {
            if (!CheckUtils.checkEmail(request.getServiceEmail())) {
                fieldMap.put("serviceEmail", IacConstants.FORMAT_ERROR);
            }
        }
        if (!(canBeEmpty && StringUtils.isEmpty(request.getServicePhone()))) {
            if (!CheckUtils.checkMobileNumber(request.getServicePhone())) {
                fieldMap.put("servicePhone", IacConstants.FORMAT_ERROR);
            }
        }
        if (!CollectionUtils.isEmpty(fieldMap)) {
            log.debug(" fieldMap {}", fieldMap);
            throw new BceValidationException(fieldMap);
        }
    }

    private Map<String, String> getEmptyFieldMap(ShopDraftSaveRequest request) {
        Map<String, String> fieldMap = new HashMap<>();
        Field[] fields = request.getClass().getDeclaredFields(); // 获取属性名称数组
        for (int i = 0; i < fields.length; i++) {
            Object valueObj = getFieldValue(request, fields[i].getName()); // 获取属性值
            if (fields[i].getGenericType().equals(String.class)) {
                if (StringUtils.isEmpty((String) valueObj)) {
                    fieldMap.put(fields[i].getName(), IacConstants.INFO_EMPTY);
                }
            }
        }
        if (CollectionUtils.isEmpty(request.getBaiduQiaos())) {
            fieldMap.put("baiduQiaos", IacConstants.INFO_EMPTY);
        }
        return fieldMap;
    }

    private Map<VendorInfoContacts.ContactType, VendorInfoContacts.ContactWay> getVendorContactMap(
            List<VendorInfoContacts.ContactWay> contactWayList) {
        Map<VendorInfoContacts.ContactType, VendorInfoContacts.ContactWay> contactWayMap =
                new HashMap<>();
        for (VendorInfoContacts.ContactWay contactWay : contactWayList) {
            contactWayMap.put(contactWay.getType(), contactWay);
        }
        return contactWayMap;
    }

    private ParamMapModel toParamMapModel(Map<String, String> paramMap) {
        ParamMapModel response = new ParamMapModel();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            response.put(entry.getKey(), entry.getValue());
            log.debug("param response {} ", response.get(entry.getKey()));
        }
        return response;
    }
}
