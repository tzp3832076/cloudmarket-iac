/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.controller;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorAmountResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorBaseContactResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorListResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorOverviewResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorShopInfoDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.VendorShopResponse;
import com.baidu.bce.mkt.framework.exception.UnknownExceptionResponse;
import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.framework.iac.annotation.VendorId;
import com.baidu.bce.mkt.iac.common.constant.IacConstants;
import com.baidu.bce.mkt.iac.common.model.ShopDraftContentAndStatus;
import com.baidu.bce.mkt.iac.common.model.VendorListModel;
import com.baidu.bce.mkt.iac.common.model.VendorOverview;
import com.baidu.bce.mkt.iac.common.model.VendorShopAuditContent;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.model.db.VendorShop;
import com.baidu.bce.mkt.iac.common.model.db.VendorStatus;
import com.baidu.bce.mkt.iac.common.service.VendorService;
import com.baidu.bce.mkt.iac.helper.ParamProperties;
import com.baidu.bce.mkt.iac.helper.VendorControllerHelper;
import com.baidu.bce.plat.webframework.iam.config.access.annotation.BceAuth;
import com.baidu.bce.plat.webframework.iam.model.BceRole;
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

    @ApiOperation(value = "商铺信息撤销审核")
    @RequestMapping(method = RequestMethod.PUT, value = "/shopDraft", params = "cancelAudit")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_SHOP, operation = "submitDraft")
    @UnknownExceptionResponse(message = "商铺撤销审核失败")
    public void cancelAuditShopDraft(@VendorId String vendorId) {
        vendorService.cancelAuditShopDraft(vendorId);
    }

    @ApiOperation(value = "商铺信息编辑更新状态")
    @RequestMapping(method = RequestMethod.PUT, value = "/shopDraft", params = "edit")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_SHOP, operation = "edit")
    @UnknownExceptionResponse(message = "商铺信息编辑更新状态")
    public void editShopDraft(@VendorId String vendorId) {
        vendorService.editAuditShopDraft(vendorId);
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
//    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read",
//            instanceParameterName = "vendorId")
    @BceAuth(role = {BceRole.SERVICE})
    @UnknownExceptionResponse(message = "服务商状态更新失败")
    public void updateVendorStatus(@PathVariable("vendorId") String vendorId,
                                   @RequestParam("status") String status) {
        vendorService.updateVendorStatus(vendorId, status);
    }

    @ApiOperation(value = "服务商店铺信息线上接口 -- 给审核系统用于获取Email 通过提交人用户ID")
    @RequestMapping(method = RequestMethod.GET, value = "/{bceUserId}/baseContact",
            params = "type=BCE_ID")
//    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read",
//            instanceParameterName = "bceUserId")
    @BceAuth(role = {BceRole.SERVICE})
    @UnknownExceptionResponse(message = "服务商店铺联系方式获取失败")
    public VendorBaseContactResponse getVendorBaseContactByBceId(@PathVariable("bceUserId") String bceUserId) {
        VendorShop vendorShop = vendorService.getVendorShopByBceUserId(bceUserId);
        return helper.toVendorBaseContact(vendorShop);
    }

    @ApiOperation(value = "待入驻及已入驻服务商统计接口")
    @RequestMapping(method = RequestMethod.GET, value = "/amountStatistics")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read")
    @UnknownExceptionResponse(message = "待入驻及已入驻服务商统计失败")
    public VendorAmountResponse getVendorAmount() {
        Map<VendorStatus, Integer> countMap = vendorService.statisticsVendorAmount();
        return helper.toVendorAmountResponse(countMap);
    }

    @ApiOperation(value = "通过服务商ID列表获取服务信息接口")
    @RequestMapping(method = RequestMethod.GET, value = "/listByIds")
    @UnknownExceptionResponse(message = "服务商信息获取失败")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "readListByIds")
    public VendorListResponse getVendorListByIds(@VendorId List<String> vendorIds) {
        VendorListModel vendorListModel = vendorService.getVendorListByIds(vendorIds);
        return helper.toVendorListResponse(vendorListModel);
    }

    @ApiOperation(value = "osp上服务商list页面中服务商信息获取接口")
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_INFO, operation = "read")
    @UnknownExceptionResponse(message = "服务商list页面获取失败")
    public VendorListResponse getVendorList( @RequestParam(value = "companyName", required = false)
                                                         String companyName,
                                             @RequestParam(value = "bceUserId", required = false)
                                                     String bceUserId,
                                             @Min(1) @RequestParam int pageNo,
                                             @Min(1) @RequestParam int pageSize) {
        int start = (pageNo - 1) * pageSize;
        int limit = pageSize;
        VendorListModel vendorListModel = vendorService.getVendorList(bceUserId, companyName,
                start, limit, VendorStatus.HOSTED);
        return helper.toVendorListResponse(vendorListModel);
    }

    @ApiOperation(value = "oss上获取服务商信息获取接口")
    @RequestMapping(method = RequestMethod.GET, value = "/searchList")
    @UnknownExceptionResponse(message = "服务商信息获取失败")
    @BceAuth(role = {BceRole.SERVICE}) // todo @BceAuth的兼容 可以直接用上面的接口
    public VendorListResponse getVendorOssSearch(@RequestParam(value = "companyName") String
                                                            companyName) {
        VendorListModel vendorListModel = vendorService.getVendorList(null, companyName, 0, 0, null);
        return helper.toVendorListResponse(vendorListModel);
    }

    @ApiOperation(value = "服务商是否已入驻(兼容一期的服务商)-接口判断")
    @RequestMapping(method = RequestMethod.GET, value = "/vendorInfo", params = "bceUserId")
    @UnknownExceptionResponse(message = "服务商基本信息获取失败")
    @BceAuth(role = {BceRole.SERVICE})
    public VendorInfoDetailResponse getVendorInfoByUserId(@RequestParam(value = "bceUserId")
                                                                  String bceUserId) {
        VendorInfo vendorInfo = vendorService.getVendorInfoByUserId(bceUserId);
        return helper.toVendorInfoDetailResponse(vendorInfo, paramProperties.getVendorInfoMap());
    }

    @ApiOperation(value = "服务商确认签署电子协议")
    @RequestMapping(method = RequestMethod.PUT, value = "/agreement")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_OVERVIEW, operation = "read")
    @UnknownExceptionResponse(message = "服务商确认签署电子协议状态同步失败")
    public void signAgreement(@VendorId String vendorId) {
        vendorService.signAgreement(vendorId);
    }

    @ApiOperation(value = "服务商店铺信息线上接口")
    @RequestMapping(method = RequestMethod.GET, value = "/{vendorId}/shopInfo")
    @BceAuth(role = {BceRole.SERVICE}) // todo 和下面的接口同步
    @UnknownExceptionResponse(message = "服务商线上店铺信息获取")
    public VendorShopResponse getVendorShopInfo(@PathVariable("vendorId") String vendorId) {
        VendorShop vendorShop = vendorService.getVendorShopByVendorId(vendorId);
        return helper.toVendorShopResponse(vendorShop);
    }

    @ApiOperation(value = "服务商店铺信息线上接口")
    @RequestMapping(method = RequestMethod.GET, value = "/shopInfo")
    @CheckAuth(resource = IacConstants.RESOURCE_VENDOR_SHOP, operation = "read")
    @UnknownExceptionResponse(message = "服务商线上店铺信息获取")
    public VendorShopInfoDetailResponse getOnlineVendorShopInfo(@VendorId String vendorId) {
        VendorShop vendorShop = vendorService.getVendorShopByVendorId(vendorId);
        VendorInfo vendorInfo = vendorService.getVendorInfoByVendorId(vendorId);
        return helper.toShopInfoResponse(vendorShop, vendorInfo);
    }

}


