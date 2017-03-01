// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.client;

import com.baidu.bce.internalsdk.core.BceClient;
import com.baidu.bce.internalsdk.core.Entity;
import com.baidu.bce.mkt.framework.iac.client.model.CheckInstanceOwnerRequest;
import com.baidu.bce.mkt.framework.iac.client.model.CheckInstanceOwnerResponse;

/**
 * auth client
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class AuthClient extends BceClient {
    public AuthClient(String endpoint, String accessKey, String secretKey) {
        super(endpoint, accessKey, secretKey);
    }

    public CheckInstanceOwnerResponse checkInstanceOwner(CheckInstanceOwnerRequest request) {
        return createAuthorizedRequest()
                .path("/mkt/auth")
                .keyOnlyQueryParam("checkOwner")
                .post(Entity.json(request), CheckInstanceOwnerResponse.class);
    }
}
