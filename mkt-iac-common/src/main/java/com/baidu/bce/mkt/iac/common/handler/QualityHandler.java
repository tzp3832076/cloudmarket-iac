/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.common.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.bce.internalsdk.core.BceInternalResponseException;
import com.baidu.bce.internalsdk.qualify.model.EnterpriseInfoResponse;
import com.baidu.bce.internalsdk.qualify.model.finance.AuditStatus;
import com.baidu.bce.internalsdk.qualify.model.finance.RealNameTypeForFinance;
import com.baidu.bce.mkt.iac.common.client.IacClientFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/2/21 by sunfangyuan@baidu.com .
 */
@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class QualityHandler {
    private final IacClientFactory clientFactory;

    public AuditStatus getQualityStatus(String bceUserId) {
        try {
            EnterpriseInfoResponse enterpriseInfo =
                    clientFactory.createQualifyClient().getEnterpriseInfo(bceUserId, false);
            if (enterpriseInfo.getType().equals(RealNameTypeForFinance.ENTERPRISE)) {
                return enterpriseInfo.getStatus();
            }
        } catch (BceInternalResponseException e) {
            log.warn("getQualityStatus failed.", e);
        }

        return AuditStatus.NONE;
    }
}
