/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.bce.mkt.iac.common.client.IacClientFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/1 by sunfangyuan@baidu.com .
 */
@Slf4j
@Component
public class NoticeSenderHandler {
    @Autowired
    private IacClientFactory clientFactory;

    public void noticeAuditMarginPayOff(String vendorId, boolean isPayOff) {
        // todo 修改审核模块的接口 可以状态TODO
        clientFactory.createAuditClient().confirmVendorMargin(vendorId);
    }

    public void noticeAuditContractStatus(String vendorId, boolean isEmpty) {
        // todo 修改审核模块的接口 可以进行协议的状态取消
        clientFactory.createAuditClient().confirmVendorContract(vendorId);
    }
}
