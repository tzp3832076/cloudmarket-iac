// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.bce.mkt.iac.common.cache.ResourceSystemCache;
import com.baidu.bce.mkt.iac.common.client.AuthClient;
import com.baidu.bce.mkt.iac.common.client.IacClientFactory;
import com.baidu.bce.mkt.iac.common.client.model.CheckInstanceOwnerRequest;

/**
 * send request to business module to check instance auth
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Component
public class RemoteInstanceCheckHandler {
    @Autowired
    private ResourceSystemCache resourceSystemCache;
    @Autowired
    private IacClientFactory clientFactory;

    public boolean check(String userId, String vendorId, String resource, List<String> instances) {
        CheckInstanceOwnerRequest request = new CheckInstanceOwnerRequest();
        CheckInstanceOwnerRequest.UserInfo userInfo = new CheckInstanceOwnerRequest.UserInfo();
        userInfo.setBceUserId(userId);
        userInfo.setVendorId(vendorId);
        request.setUserInfo(userInfo);
        request.setResource(resource);
        request.setInstances(instances);
        String system = resourceSystemCache.getSystem(resource);
        AuthClient authClient = clientFactory.createAuthClient(system);
        return authClient.checkInstanceOwner(request).isOwner();
    }
}
