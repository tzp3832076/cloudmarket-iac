// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.BceAuthContextWrapper;
import com.baidu.bce.mkt.framework.iac.model.HeadUser;
import com.baidu.bce.mkt.framework.iac.service.CheckAuthService;
import com.baidu.bce.mkt.iac.common.model.AuthorizeCommand;
import com.baidu.bce.mkt.iac.common.model.LocalAuthorizedToken;
import com.baidu.bce.mkt.iac.common.model.UserIdentity;

/**
 * check auth service for iac
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Service(CheckAuthService.BEAN_NAME)
public class LocalCheckAuthService implements CheckAuthService {
    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public AuthorizedToken checkAuth(BceAuthContextWrapper bceAuthContextWrapper, HeadUser headUser, String resource,
                                     String operation, List<String> instances) {
        AuthorizeCommand authorizeCommand = new AuthorizeCommand(bceAuthContextWrapper.getUserName(),
                bceAuthContextWrapper.getUserId(), bceAuthContextWrapper.getDomainId(),
                bceAuthContextWrapper.isServiceAccount(), headUser.getUserId(), headUser.getTargetVendorList(),
                resource, operation, instances);
        UserIdentity userIdentity = authorizationService.authorize(authorizeCommand);
        return new LocalAuthorizedToken(userIdentity, headUser.getTargetVendorList());
    }
}
