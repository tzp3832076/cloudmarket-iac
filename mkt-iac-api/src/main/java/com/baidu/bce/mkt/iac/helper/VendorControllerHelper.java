/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.helper;

import static com.baidu.bae.commons.lib.utils.ReflectionHelper.getFieldValue;
import static com.baidu.bae.commons.lib.utils.ReflectionHelper.setFieldValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.baidu.bce.internalsdk.mkt.iac.model.OnlineSupport;
import com.baidu.bce.internalsdk.mkt.iac.model.ParamMapModel;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorAmountResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorBaseContactResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorListResponseV2;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorSearchMapResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorShopInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorShopResponse;
import com.baidu.bce.mkt.audit.internal.sdk.model.MktAuditSdkConstant;
import com.baidu.bce.mkt.audit.internal.sdk.model.common.VendorInfoContacts;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.framework.utils.JsonUtils;
import com.baidu.bce.mkt.framework.utils.SecurityUtils;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.handler.CategoryHandler;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorListModel;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorServiceInfoModel;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;
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
    @Autowired
    private CategoryHandler categoryHandler;

    public VendorShopAuditContent.ShopDraft getShopDraftContent(ShopDraftSaveRequest request) {
        VendorShopAuditContent.ShopDraft shopDraft = new VendorShopAuditContent.ShopDraft();
        shopDraft.setBaiduQiaos(getBaiduQiaos(request));
        shopDraft.setCompanyDescription(request.getCompanyDescription());
        shopDraft.setServiceAvailTime(request.getServiceAvailTime());
        shopDraft.setServiceEmail(request.getServiceEmail());
        shopDraft.setServicePhone(request.getServicePhone());
        // 取消百度钱包后兼容旧代码
        if(StringUtils.isBlank(request.getBaiduWalletAccount())) {
            shopDraft.setBaiduWalletAccount("EMPTY");
        } else {
            shopDraft.setBaiduWalletAccount(request.getBaiduWalletAccount());
        }
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
        if (contentAndStatus == null) {
            return response;
        }
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
        if(!vendorInfo.getBusiness().equals("UNDEFINED")) {
            detail.setBusiness(vendorInfo.getBusiness());
        } else {
            detail.setBusiness("");
        }
        detail.setCompanyName(vendorInfo.getCompany());
        detail.setCompanyCapital(vendorInfo.getCapital());
        detail.setServiceHotline(vendorInfo.getHotline());
        detail.setJoinedOtherMarkets(vendorInfo.getOtherMarket());
        detail.setCompanySite(vendorInfo.getWebsite());
        detail.setCompanyAddress(vendorInfo.getAddress());
        detail.setCompanyHeadcount(vendorInfo.getHeadcount());
        detail.setCompanyEmail(vendorInfo.getEmail());
        detail.setServiceIllustration(vendorInfo.getServiceIllustration());
        detail.setServiceCategory(getCategoryName(vendorInfo.getServiceCategory()));
        detail.setIsServiceHotlineSupported(StringUtils.isNotEmpty(vendorInfo.getHotline()));
        detail.setIsJonedOtherMarkets(StringUtils.isNotEmpty(vendorInfo.getOtherMarket()));
        log.debug("vendorId:{},serviceCategory:{}", vendorInfo.getVendorId(), detail.getServiceCategory());

        VendorInfoContacts contacts = StringUtils.isEmpty(vendorInfo.getContactInfo()) ? null :
                                              JsonUtils.fromJson(vendorInfo.getContactInfo(), VendorInfoContacts.class);
        if (contacts != null) {
            Map<String, VendorInfoContacts.ContactWay> contactWayMap = contacts
                                                                               .getVendorContactMap();
            if (contactWayMap.get(MktAuditSdkConstant.BUSINESS_CONTACT) != null) {
                detail.setBizContact(
                        contactWayMap.get(MktAuditSdkConstant.BUSINESS_CONTACT).getName());
                detail.setBizContactPhone(
                        contactWayMap.get(MktAuditSdkConstant.BUSINESS_CONTACT).getPhone());
                String email = contactWayMap.get(MktAuditSdkConstant.BUSINESS_CONTACT).getEmail();
                if (StringUtils.isNotBlank(email)) {
                    detail.setBizContactEmail(email);
                }
            }
            if (contactWayMap.get(MktAuditSdkConstant.EMERGENCY_CONTACT) != null) {
                detail.setEmerContact(
                        contactWayMap.get(MktAuditSdkConstant.EMERGENCY_CONTACT).getName());
                detail.setEmerContactPhone(
                        contactWayMap.get(MktAuditSdkConstant.EMERGENCY_CONTACT).getPhone());
            }
        }
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
        return response;
    }

    public VendorAmountResponse toVendorAmountResponse(Map<VendorStatus, Integer> countMap) {
        VendorAmountResponse response = new VendorAmountResponse();
        response.setProcessingNum(countMap.get(VendorStatus.PROCESSING));
        response.setHostedNum(countMap.get(VendorStatus.HOSTED));
        return response;
    }

    public VendorBaseContactResponse toVendorBaseContact(VendorShop shop) {
        VendorBaseContactResponse response = new VendorBaseContactResponse();
        if (shop != null) {
            response.setEmail(shop.getEmail());
        }
        return response;
    }

    public VendorShopResponse toVendorShopResponse(VendorShop shop) {
        VendorShopResponse response = new VendorShopResponse();
        if (shop == null) {
            return response;
        }
        response.setEmail(shop.getEmail());
        response.setHotline(shop.getCellphone());
        response.setIntro(shop.getIntro());
        response.setVendorName(shop.getName());
        VendorServiceInfoModel vendorServiceInfoModel = JsonUtils.fromJson(shop.getServiceInfo(),
                VendorServiceInfoModel.class);
        if (vendorServiceInfoModel != null) {
            response.setServiceTime(vendorServiceInfoModel.getServiceAvailTime());
            List<VendorShopResponse.OnlineSupport> onlineSupports = new ArrayList<>();
            for (OnlineSupport onlineSupport : vendorServiceInfoModel.getOnlineSupports()) {
                onlineSupports.add(new VendorShopResponse.OnlineSupport(
                                                                               onlineSupport.getName(), onlineSupport.getLink()));
            }
            response.setOnlineSupports(onlineSupports);
        }
        return response;
    }

    public VendorShopInfoDetailResponse toShopInfoResponse(VendorShop shop, VendorInfo
                                                                                    vendorInfo) {
        VendorShopInfoDetailResponse response = new VendorShopInfoDetailResponse();
        if (shop == null || vendorInfo == null) {
            return response;
        }
        response.setBceAccount(vendorInfo.getBceUserId());
        response.setBaiduWalletAccount(vendorInfo.getWalletId());
        response.setCompanyDescription(shop.getIntro());
        response.setCompanyName(vendorInfo.getCompany());
        response.setServiceEmail(shop.getEmail());
        response.setServicePhone(shop.getCellphone());
        response.setVendorId(vendorInfo.getVendorId());
        VendorServiceInfoModel vendorServiceInfoModel = JsonUtils.fromJson(shop.getServiceInfo(),
                VendorServiceInfoModel.class);
        if (vendorServiceInfoModel != null) {
            response.setServiceAvailTime(vendorServiceInfoModel.getServiceAvailTime());
            List<OnlineSupport> onlineSupports = new ArrayList<>();
            for (OnlineSupport onlineSupport : vendorServiceInfoModel.getOnlineSupports()) {
                onlineSupports.add(new OnlineSupport(onlineSupport.getName(), onlineSupport.getLink()));
            }
            response.setBaiduQiaos(onlineSupports);
        }
        return response;
    }

    public VendorListResponse toVendorListResponse(VendorListModel listModel) {
        VendorListResponse vendorListResponse = new VendorListResponse();
        if (listModel == null || CollectionUtils.isEmpty(listModel.getVendorInfoList())) {
            vendorListResponse.setTotalCount(0);
            vendorListResponse.setVendorBaseInfoList(new ArrayList<>());
            return vendorListResponse;
        }
        vendorListResponse.setTotalCount(listModel.getTotalCount());
        List<VendorListResponse.VendorBaseInfo> vendorBaseInfoList = new ArrayList<>();
        for (VendorInfo vendorInfo : listModel.getVendorInfoList()) {
            vendorBaseInfoList.add(new VendorListResponse.VendorBaseInfo(
                                                                                vendorInfo.getCompany(), vendorInfo.getBceUserId(), vendorInfo.getVendorId(),
                                                                                vendorInfo.getCreateTime()));
        }
        vendorListResponse.setVendorBaseInfoList(vendorBaseInfoList);
        return vendorListResponse;
    }

    public VendorListResponseV2 toVendorListResponseV2(VendorListModel listModel) {
        VendorListResponseV2 vendorListResponseV2 = new VendorListResponseV2();

        if (listModel == null || CollectionUtils.isEmpty(listModel.getVendorInfoList())) {
            vendorListResponseV2.setTotalCount(0);
            vendorListResponseV2.setVendorBaseInfoList(new ArrayList<>());
            return vendorListResponseV2;
        }

        vendorListResponseV2.setTotalCount(listModel.getTotalCount());
        List<VendorListResponseV2.VendorBaseInfo> vendorBaseInfoList = new ArrayList<>();
        for (VendorInfo vendorInfo : listModel.getVendorInfoList()) {
            vendorBaseInfoList.add(new VendorListResponseV2.VendorBaseInfo(
                                                                                vendorInfo.getCompany(), vendorInfo.getBceUserId(),
                                                                                vendorInfo.getBusiness(), vendorInfo.getVendorId(),
                                                                                vendorInfo.getCreateTime()));
        }
        vendorListResponseV2.setVendorBaseInfoList(vendorBaseInfoList);
        return vendorListResponseV2;
    }

    public VendorSearchMapResponse toVendorSearchMapResponse(List<VendorInfo> vendorInfos) {
        VendorSearchMapResponse.VendorMap vendorMap = new VendorSearchMapResponse.VendorMap();
        for (VendorInfo vendorInfo : vendorInfos) {
            vendorMap.put(vendorInfo.getVendorId(), new VendorSearchMapResponse.VendorSearchModel(
                                                                                                         vendorInfo.getVendorId(), vendorInfo.getCompany()));
        }
        return new VendorSearchMapResponse(vendorMap);
    }

    public void checkShopDraftSaveRequest(ShopDraftSaveRequest request, boolean canBeEmpty) {
        Map<String, String> fieldMap = canBeEmpty ? new HashMap<>() : getEmptyFieldMap(request);
        if (!(canBeEmpty && StringUtils.isEmpty(request.getServiceEmail()))) {
            if (!CheckUtils.checkEmail(request.getServiceEmail())) {
                fieldMap.put("serviceEmail", IacConstants.FORMAT_ERROR);
            }
        }
        checkString("servicePhone", request.getServicePhone(), 5, 18, fieldMap, canBeEmpty, true);
        if (!CollectionUtils.isEmpty(fieldMap)) {
            log.debug(" fieldMap {}", fieldMap);
            throw new BceValidationException(fieldMap);
        }
    }

    private Map<String, String> getEmptyFieldMap(ShopDraftSaveRequest request) {
        // 取消百度钱包后兼容旧代码
        if (StringUtils.isBlank(request.getBaiduWalletAccount())) {
            request.setBaiduWalletAccount("EMPTY");
        }
        Map<String, String> fieldMap = new HashMap<>();
        Field[] fields = request.getClass().getDeclaredFields(); // 获取属性名称数组
        for (int i = 0; i < fields.length; i++) {
            Object valueObj = getFieldValue(request, fields[i].getName()); // 获取属性值
            if (fields[i].getGenericType().equals(String.class)) {
                String str = SecurityUtils.stripSqlAndXss((String) valueObj);
                if (StringUtils.isEmpty(str)) {
                    fieldMap.put(fields[i].getName(), IacConstants.INFO_EMPTY);
                } else {
                    setFieldValue(request, fields[i].getName(), str);
                }
            }
        }
        if (CollectionUtils.isEmpty(request.getBaiduQiaos())) {
            fieldMap.put("baiduQiaos", IacConstants.INFO_EMPTY);
        } else {
            for (OnlineSupport support : request.getBaiduQiaos()) {
                support.setLink(SecurityUtils.stripSqlAndXss(support.getLink()));
                support.setName(SecurityUtils.stripSqlAndXss(support.getName()));
                if (StringUtils.isEmpty(support.getLink())
                            || StringUtils.isEmpty(support.getName())) {
                    fieldMap.put("baiduQiaos", IacConstants.INFO_EMPTY);
                }
                if (!support.getLink().startsWith(IacConstants.SUPPORT_URL_PRE)) {
                    fieldMap.put("baiduQiaos", "客服信息需要为商桥平台：" + IacConstants.SUPPORT_URL_PRE);
                }
            }
        }
        return fieldMap;
    }

    private ParamMapModel toParamMapModel(Map<String, String> paramMap) {
        ParamMapModel response = new ParamMapModel();
        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            response.put(entry.getKey(), entry.getValue());
            log.debug("param response {} ", response.get(entry.getKey()));
        }
        return response;
    }

    private void checkString(String paramName, String paramValue, int minLength, int maxLength,
                             Map<String, String> validationMap, boolean isDraft, boolean required) {
        if (StringUtils.isEmpty(paramValue)) {
            if (isDraft || !required) {
                return;
            } else {
                validationMap.put(paramName, "不能为空");
                return;
            }
        }
        int length = paramValue.length();
        if (length < minLength || length > maxLength) {
            validationMap.put(paramName, "长度必须在" + minLength + "到" + maxLength + "之间");
            return;
        }
    }

    private List<String> getCategoryName(String serviceCategory) {
        Map<String, String> nameMap = categoryHandler.getCategoryNameMap(serviceCategory);
        return nameMap.entrySet().stream().map(x -> x.getKey() + ":" + x.getValue()).collect(Collectors.toList());
    }

    private List<OnlineSupport> getBaiduQiaos(ShopDraftSaveRequest request) {
        List<OnlineSupport> res = new ArrayList<>();
        if (!CollectionUtils.isEmpty(request.getBaiduQiaos())) {
            for (OnlineSupport onlineSupport : request.getBaiduQiaos()) {
                String name = onlineSupport.getName();
                String link = onlineSupport.getLink().replaceAll("&amp;", "&");
                OnlineSupport os = new OnlineSupport(name, link);
                res.add(os);
            }
        }
        return res;
    }
}
