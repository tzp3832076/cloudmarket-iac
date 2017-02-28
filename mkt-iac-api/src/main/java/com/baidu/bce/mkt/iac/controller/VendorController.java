/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftDetailResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.ShopDraftSaveRequest;
import com.baidu.bce.mkt.iac.common.model.db.VendorShopDraft;
import com.baidu.bce.mkt.iac.common.service.VendorService;
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

    @ApiOperation(value = "商铺信息保存接口")
    @RequestMapping(method = RequestMethod.POST, value = "shopDraft/{vendorId}")
    public void saveVendorShopDraft(@PathVariable("vendorId") String vendorId,
                                    @RequestBody ShopDraftSaveRequest request) {
        vendorService.saveShopDraft(vendorId, request.getContent());
    }

    @ApiOperation(value = "服务商-商铺信息获取接口")
    @RequestMapping(method = RequestMethod.GET, value = "shopDraft/{vendorId}")
    public ShopDraftDetailResponse getVendorShopDraft(@PathVariable("vendorId") String vendorId) {
        VendorShopDraft vendorShopDraft = vendorService.getVendorShopDraft(vendorId);
        return helper.toShopDraftDetailResponse(vendorShopDraft);
    }

}


