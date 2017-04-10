// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.client;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.bce.internalsdk.qualify.QualifyClient;
import com.baidu.bce.mkt.audit.internal.sdk.model.MktAuditClient;
import com.baidu.bce.mkt.iac.common.config.ConfigProperties;
import com.baidu.bce.mkt.internalsdk.model.MktInternalClient;
import com.baidu.bce.plat.webframework.endpoint.SDKEndpointConfiguration;
import com.baidu.bce.plat.webframework.iam.service.IAMService;

import endpoint.EndpointManager;

/**
 * client factory
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Component
public class IacClientFactory implements InitializingBean {
    @Autowired
    private SDKEndpointConfiguration sdkEndpointConfiguration;
    @Autowired
    private ConfigProperties configProperties;
    private MktAuditClient mktAuditClient;
    private QualifyClient qualifyClient;
    private MktInternalClient mktInternalClient;

    @Autowired
    private IAMService iamService;

    public AuthClient createAuthClient(String system) {
        return new AuthClient(EndpointManager.getEndpoint(system),
                configProperties.getMktServiceAk(), configProperties.getMktServiceSk());
    }

    public MktAuditClient createAuditClient() {
        return mktAuditClient;
    }

    public MktAuditClient createMktAuditClient(String userId) {
        MktAuditClient mktAuditClient = new MktAuditClient(EndpointManager.getEndpoint("MKT_AUDIT"),
                                                                  configProperties.getMktServiceAk(),
                                                                  configProperties.getMktServiceSk());
        mktAuditClient.setHeaderUserId(userId);
        return mktAuditClient;
    }

    public QualifyClient createQualifyClient() {
        return qualifyClient;
    }

    public MktInternalClient createMktServiceInternalClient() {
        return mktInternalClient;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        qualifyClient = new QualifyClient(configProperties.getMktServiceAk(),
                                                 configProperties.getMktServiceSk());
        mktAuditClient = new MktAuditClient(EndpointManager.getEndpoint("MKT_AUDIT"),
                                                   configProperties.getMktServiceAk(),
                                                   configProperties.getMktServiceSk());
        mktInternalClient = new MktInternalClient(EndpointManager.getEndpoint("MKT"),
                configProperties.getMktServiceAk(), configProperties.getMktServiceSk());
    }
}
