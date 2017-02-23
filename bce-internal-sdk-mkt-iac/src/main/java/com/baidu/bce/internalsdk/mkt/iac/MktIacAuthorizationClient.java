// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.internalsdk.mkt.iac;

import com.baidu.bce.internalsdk.core.BceClient;
import com.baidu.bce.internalsdk.core.Entity;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationResponse;

import endpoint.EndpointManager;

/**
 * authorization client
 * without sign
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class MktIacAuthorizationClient extends BceClient {
    public MktIacAuthorizationClient() {
        super(EndpointManager.getEndpoint("MKT-IAC"));
    }

    public MktIacAuthorizationClient(String endpoint) {
        super(endpoint);
    }

    public AuthorizationResponse checkAuth(AuthorizationRequest request) {
        return createRequest()
                .path("/v1/authorization")
                .post(Entity.json(request), AuthorizationResponse.class);
    }
}
