// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.helper;

import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.MktToken;
import com.baidu.bce.mkt.framework.mvc.ControllerHelper;
import com.baidu.bce.mkt.iac.common.model.AuthorizeCommand;
import com.baidu.bce.mkt.iac.common.model.UserIdentity;

/**
 * authorization controller helper
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@ControllerHelper
public class AuthorizationControllerHelper {
    public AuthorizeCommand toAuthorizeCommand(AuthorizationRequest request) {
        String requestedUser = request.getHeaderUser() == null ? null : request.getHeaderUser().getUserId();
        return new AuthorizeCommand(request.getBceToken().getUsername(),
                request.getBceToken().getUserId(),
                request.getBceToken().getMainUserId(),
                request.getBceToken().isServiceAccount(),
                requestedUser,
                request.getResource(),
                request.getOperation(),
                request.getInstances());
    }

    public AuthorizationResponse toAuthorizationResponse(UserIdentity userIdentity) {
        AuthorizationResponse response = new AuthorizationResponse();
        MktToken mktToken = new MktToken();
        mktToken.setUserId(userIdentity.getUserId());
        mktToken.setType(userIdentity.getAccountType());
        mktToken.setMainUserId(userIdentity.getMainUserId());
        mktToken.setRole(userIdentity.getRole());
        mktToken.setVendorId(userIdentity.getVendorId());
        response.setMktToken(mktToken);
        return response;
    }
}
