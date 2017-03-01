/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.AuditNoticeRequest;
import com.baidu.bce.mkt.iac.common.model.db.VendorInfo;
import com.baidu.bce.mkt.iac.common.service.NoticeService;
import com.baidu.bce.mkt.iac.helper.NoticeHelper;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/23 by sunfangyuan@baidu.com .
 */
@RestController
@RequestMapping("/v1/notice")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeHelper helper;
    private static String TYPE_APPLICATION = "APPLICATION";
    private static String TYPE_VENDOR_SHOP = "VENDOR_SHOP";

    @ApiOperation(value = "入驻审核信息通过的通知接收")
    @RequestMapping(method = RequestMethod.POST, value = "/audit", params = "type=application")
    public void auditNotice(@RequestParam("id") String id,
                            @RequestParam("status") String status,
                            @RequestBody(required = false) AuditNoticeRequest request) {
        VendorInfo vendorInfo = helper.toVendorInfo(id, request.getContent());
        noticeService.auditNoticeApplication(status, vendorInfo);
    }

}
