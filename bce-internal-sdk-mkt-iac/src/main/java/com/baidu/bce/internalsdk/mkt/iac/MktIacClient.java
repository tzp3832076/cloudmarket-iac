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
    private static String TYPE_APPLICATION = "APPLICATION";
    private static String TYPE_VENDOR_SHOP = "VENDOR_SHOP";

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

        if (TYPE_APPLICATION.equals(type)) {
            createMktRequest()
                    .path("/v1/notice/audit")
                    .queryParam("type", "application")
                    .queryParam("id", id)
                    .queryParam("status", status)
                    .post(Entity.json(request));
        }
        if (TYPE_VENDOR_SHOP.equals(type)) {
            createMktRequest()
                    .path("/v1/notice/audit")
                    .queryParam("type", "vendorShop")
                    .queryParam("id", id)
                    .queryParam("status", status)
                    .put();
        }
    }
}
