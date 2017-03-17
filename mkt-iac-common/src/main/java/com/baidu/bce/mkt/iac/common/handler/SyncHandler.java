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
public class SyncHandler {
    private static String PROCESS_DONE = "DONE";
    private static String PROCESS_TODO = "TODO";

    @Autowired
    private IacClientFactory clientFactory;

    public void noticeAuditDepositPayOff(String vendorId, boolean isPayOff) {
        String status = isPayOff ? PROCESS_DONE : PROCESS_TODO;
        clientFactory.createAuditClient().syncVendorDeposit(vendorId, status);
    }

    public void noticeAuditContractStatus(String vendorId, boolean isSign) {
        String status = isSign ? PROCESS_DONE : PROCESS_TODO;
        clientFactory.createAuditClient().syncVendorContract(vendorId, status);
    }
}
