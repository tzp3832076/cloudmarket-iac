// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.service;

import java.util.List;

import com.baidu.bce.internalsdk.core.BceInternalResponseException;
import com.baidu.bce.internalsdk.mkt.iac.MktIacAuthorizationClient;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationResponse;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.BceAuthContextWrapper;
import com.baidu.bce.mkt.framework.iac.model.HeadUser;
import com.baidu.bce.mkt.framework.iac.model.ReceivedAuthorizedToken;
import com.baidu.bce.plat.webframework.exception.BceException;

import lombok.extern.slf4j.Slf4j;

/**
 * authorization service
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class RemoteCheckAuthService implements CheckAuthService {
    @Override
    public AuthorizedToken checkAuth(BceAuthContextWrapper bceAuthContextWrapper, HeadUser headUser, String resource,
                                     String operation, List<String> instances) {
        MktIacAuthorizationClient authorizationClient = new MktIacAuthorizationClient();
        AuthorizationRequest request = new AuthorizationRequest();
        request.setBceToken(createBceToken(bceAuthContextWrapper));
        request.setHeaderUser(createHeaderUser(headUser));
        request.setResource(resource);
        request.setOperation(operation);
        request.setInstances(instances);
        try {
            AuthorizationResponse response = authorizationClient.checkAuth(request);
            return new ReceivedAuthorizedToken(bceAuthContextWrapper.getToken(), response.getMktToken());
        } catch (BceInternalResponseException e) {
            throw new BceException(e.getMessage(), e.getHttpStatus(), e.getCode());
        }
    }

    private AuthorizationRequest.BceToken createBceToken(BceAuthContextWrapper bceAuthContextWrapper) {
        if (bceAuthContextWrapper.getToken() == null) {
            log.error("iam user token not found");
            throw new CheckAuthFailedException();
        }
        AuthorizationRequest.BceToken bceToken = new AuthorizationRequest.BceToken();
        bceToken.setUserId(bceAuthContextWrapper.getUserId());
        bceToken.setUsername(bceAuthContextWrapper.getUserName());
        bceToken.setMainUserId(bceAuthContextWrapper.getDomainId());
        bceToken.setServiceAccount(bceAuthContextWrapper.isServiceAccount());
        return bceToken;
    }

    private AuthorizationRequest.HeaderUser createHeaderUser(HeadUser headUser) {
        AuthorizationRequest.HeaderUser headerUser = new AuthorizationRequest.HeaderUser();
        headerUser.setUserId(headUser.getUserId());
        headerUser.setTargetVendorList(headUser.getTargetVendorList());
        return headerUser;
    }


}
