// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.bce.mkt.audit.internal.sdk.model.MktAuditClient;
import com.baidu.bce.mkt.iac.common.config.ConfigProperties;

import endpoint.EndpointManager;

/**
 * client factory
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Component
public class IacClientFactory {
    @Autowired
    private ConfigProperties configProperties;
    private MktAuditClient mktAuditClient;

    public AuthClient createAuthClient(String system) {
        return new AuthClient(EndpointManager.getEndpoint(system),
                configProperties.getMktServiceAk(), configProperties.getMktServiceSk());
    }

    public MktAuditClient createAuditClient() {
        if (mktAuditClient == null) {
            mktAuditClient = new MktAuditClient(EndpointManager.getEndpoint("MKT_AUDIT"),
                                                       configProperties.getMktServiceAk(),
                                                       configProperties.getMktServiceSk());
        }
        return mktAuditClient;
    }
}
