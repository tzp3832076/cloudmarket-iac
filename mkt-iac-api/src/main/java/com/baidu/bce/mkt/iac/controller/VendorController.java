/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorBaseContactResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;
import com.baidu.bce.mkt.framework.exception.UnknownExceptionResponse;
import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.framework.iac.annotation.VendorId;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.service.VendorService;
import com.baidu.bce.mkt.iac.helper.ParamProperties;
import com.baidu.bce.mkt.iac.helper.VendorControllerHelper;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/27 by sunfangyuan@baidu.com .
 */
@RestController
@RequestMapping("/v1/vendor")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VendorController {
    private final VendorService vendorService;
    private final VendorControllerHelper helper;
    private final ParamProperties paramProperties;

    @ApiOperation(value = "商铺信息保存接口")
    @RequestMapping(method = RequestMethod.POST, value = "/shopDraft")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_SHOP, operation = "saveDraft")
    @UnknownExceptionResponse(message = "商铺信息保存失败")
    public void saveVendorShopDraft(@VendorId String vendorId,
                                    @RequestBody ShopDraftSaveRequest request) {
        helper.checkShopDraftSaveRequest(request, true);
        VendorShopAuditContent.ShopDraft content = helper.getShopDraftContent(request);
        vendorService.saveShopDraft(vendorId, content);
    }

    @ApiOperation(value = "商铺信息提交接口")
    @RequestMapping(method = RequestMethod.POST, value = "/shopDraft", params = "submit")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_SHOP, operation = "submitDraft")
    @UnknownExceptionResponse(message = "商铺信息提交失败")
    public void submitVendorShopDraft(@VendorId String vendorId,
                                    @RequestBody ShopDraftSaveRequest request) {
        helper.checkShopDraftSaveRequest(request, false);
        VendorShopAuditContent content = helper.toShopAuditContent(request);
        vendorService.submitShopDraft(vendorId, content);
    }

    @ApiOperation(value = "服务商-商铺草稿信息获取接口")
    @RequestMapping(method = RequestMethod.GET, value = "/shopDraft")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_SHOP, operation = "read")
    @UnknownExceptionResponse(message = "商铺草稿信息获取失败")
    public ShopDraftDetailResponse getVendorShopDraft(@VendorId String vendorId) {
        ShopDraftContentAndStatus shopDraftContent = vendorService.getShopDraftContentAndStatus(vendorId);
        return helper.toShopDraftDetailResponse(shopDraftContent, paramProperties.getVendorShopMap());
    }

    @ApiOperation(value = "服务商基本信息获取接口")
    @RequestMapping(method = RequestMethod.GET, value = "/vendorInfo")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read")
    @UnknownExceptionResponse(message = "服务商基本信息获取失败")
    public VendorInfoDetailResponse getVendorInfo(@VendorId String vendorId) {
        VendorInfo vendorInfo = vendorService.getVendorInfoByVendorId(vendorId);
        return helper.toVendorInfoDetailResponse(vendorInfo, paramProperties.getVendorInfoMap());
    }

    @ApiOperation(value = "服务商概览页面接口")
    @RequestMapping(method = RequestMethod.GET, value = "/overview")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_OVERVIEW, operation = "read")
    @UnknownExceptionResponse(message = "服务商概览页获取失败")
    public VendorOverviewResponse getVendorOverview(@VendorId String vendorId) {
        VendorOverview vendorOverview = vendorService.getVendorOverview(vendorId);
        return helper.toVendorOverviewResponse(vendorOverview);
    }

    @ApiOperation(value = "服务商总控状况的同步接口 --AUDIT系统 调用")
    @RequestMapping(method = RequestMethod.PUT, value = "/{vendorId}", params = "status")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read",
            instanceParameterName = "vendorId")
    @UnknownExceptionResponse(message = "服务商状态更新失败")
    public void updateVendorStatus(@PathVariable("vendorId") String vendorId,
                                   @RequestParam("status") String status) {
        vendorService.updateVendorStatus(vendorId, status);
    }

    @ApiOperation(value = "服务商店铺信息线上接口 -- 给审核系统用于获取Email 通过提交人用户ID")
    @RequestMapping(method = RequestMethod.GET, value = "/{bceUserId}/baseContact",
            params = "type=BCE_ID")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read",
            instanceParameterName = "bceUserId")
    @UnknownExceptionResponse(message = "服务商店铺联系方式获取失败")
    public VendorBaseContactResponse getVendorBaseContactByBceId(@PathVariable("bceUserId") String bceUserId) {
        VendorShop vendorShop = vendorService.getVendorShopByBceUserId(bceUserId);
        return helper.toVendorBaseContact(vendorShop);
    }
}


