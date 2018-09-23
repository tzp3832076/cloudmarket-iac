/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.common.client;

import java.util.List;

import com.baidu.bce.internalsdk.core.BceClient;
import com.baidu.bce.internalsdk.core.BceInternalRequest;
import com.baidu.bce.internalsdk.core.Entity;
import com.baidu.bce.mkt.framework.iac.client.model.request.AccountIdsRequest;
import com.baidu.bce.mkt.framework.iac.client.model.response.SensitiveListResponse;

import endpoint.EndpointManager;

/**
 * Created by wangbin33@baidu.com on 2018/9/17.
 */
public class CrmSensitiveClient extends BceClient {

    public static final String SERVICE_NAME = "CRM";

    public CrmSensitiveClient(String accessKey, String secretKey) {
        super(EndpointManager.getEndpoint("CRM"), accessKey, secretKey);
    }

    public CrmSensitiveClient(String endpoint, String accessKey, String secretKey) {
        super(endpoint, accessKey, secretKey);
    }

    public BceInternalRequest createCrmRequest() {
        return super.createAuthorizedRequest();
    }

    public SensitiveListResponse listSensitiveByAccountIds(List<String> accountIds) {
        AccountIdsRequest accountIdsRequest = new AccountIdsRequest();
        accountIdsRequest.setAccountIds(accountIds);
        return (SensitiveListResponse) this.createCrmRequest().path("/customer/").path("list_sensitive_by_account_ids")
                                               .post(Entity.json(accountIdsRequest), SensitiveListResponse.class);
    }
}
