// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.internalsdk.mkt.iac;

import com.baidu.bce.internalsdk.core.BceClient;
import com.baidu.bce.internalsdk.core.BceInternalRequest;
import com.baidu.bce.internalsdk.core.Entity;
import com.baidu.bce.internalsdk.mkt.iac.model.AuditNoticeRequest;

import endpoint.EndpointManager;

/**
 * mkt iac client
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class MktIacClient extends BceClient {
    public MktIacClient(String accessKey, String secretKey) {
        super(EndpointManager.getEndpoint("MKT-IAC"), accessKey, secretKey);
    }

    public MktIacClient(String endpoint, String accessKey, String secretKey) {
        super(endpoint, accessKey, secretKey);
    }

    private BceInternalRequest createMktRequest() {
        return createAuthorizedRequest();
    }

    public void noticeAudit(String type, String id, String status, AuditNoticeRequest request) {
        createMktRequest()
                .path("/v1/notice/audit")
                .queryParam("type", type)
                .queryParam("id", id)
                .queryParam("status", status)
                .post(Entity.json(request));
    }
}
