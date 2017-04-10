// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.helper;

import java.util.List;

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
        AuthorizationRequest.HeaderUser headerUser = request.getHeaderUser();
        String requestedUser = headerUser == null ? null : headerUser.getUserId();
        List<String> targetVendorList = headerUser == null ? null : headerUser.getTargetVendorList();
        return new AuthorizeCommand(request.getBceToken().getUsername(),
                request.getBceToken().getUserId(),
                request.getBceToken().getMainUserId(),
                request.getBceToken().isServiceAccount(),
                requestedUser,
                targetVendorList,
                request.getResource(),
                request.getOperation(),
                request.getInstances());
    }

    public AuthorizationResponse toAuthorizationResponse(UserIdentity userIdentity, List<String> targetVendorList) {
        AuthorizationResponse response = new AuthorizationResponse();
        MktToken mktToken = new MktToken();
        mktToken.setUserId(userIdentity.getUserId());
        mktToken.setType(userIdentity.getAccountType());
        mktToken.setMainUserId(userIdentity.getMainUserId());
        mktToken.setRole(userIdentity.getRole());
        mktToken.setVendorId(userIdentity.getVendorId());
        mktToken.setTargetVendorList(targetVendorList);
        response.setMktToken(mktToken);
        return response;
    }
}
