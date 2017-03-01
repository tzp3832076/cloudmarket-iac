// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.service;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baidu.bce.internalsdk.iam.model.Token;
import com.baidu.bce.internalsdk.mkt.iac.MktIacAuthorizationClient;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationResponse;
import com.baidu.bce.mkt.framework.iac.HeaderConstants;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.plat.webframework.iam.model.BceAuthContext;
import com.baidu.bce.plat.webframework.iam.model.BceRole;
import com.baidu.bce.plat.webframework.iam.service.UserService;

import lombok.extern.slf4j.Slf4j;

/**
 * authorization service
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class AuthorizationService {
    public AuthorizedToken checkAuth(String resource, String operation, List<String> instances) {
        Token token = UserService.getSubjectToken();
        MktIacAuthorizationClient authorizationClient = new MktIacAuthorizationClient();
        AuthorizationRequest request = new AuthorizationRequest();
        request.setBceToken(createBceToken(token));
        request.setHeaderUser(createHeaderUser());
        request.setResource(resource);
        request.setOperation(operation);
        request.setInstances(instances);
        AuthorizationResponse response = authorizationClient.checkAuth(request);
        return new AuthorizedToken(token, response.getMktToken());
    }

    private AuthorizationRequest.BceToken createBceToken(Token token) {
        if (token == null) {
            log.error("iam user token not found");
            throw new CheckAuthFailedException();
        }
        BceAuthContext bceAuthContext = new BceAuthContext(token);
        AuthorizationRequest.BceToken bceToken = new AuthorizationRequest.BceToken();
        bceToken.setUserId(bceAuthContext.getUserId());
        bceToken.setUsername(bceAuthContext.getUserName());
        bceToken.setMainUserId(bceAuthContext.getDomainId());
        bceToken.setServiceAccount(isServiceAccount(bceAuthContext));
        return bceToken;
    }

    private AuthorizationRequest.HeaderUser createHeaderUser() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null || !(requestAttributes instanceof ServletRequestAttributes)) {
            log.error("get ServletRequestAttributes failed");
            throw new CheckAuthFailedException();
        }
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
        String userId = httpServletRequest.getHeader(HeaderConstants.CURRENT_USER_ID);
        AuthorizationRequest.HeaderUser headerUser = new AuthorizationRequest.HeaderUser();
        headerUser.setUserId(userId);
        return headerUser;
    }

    private boolean isServiceAccount(BceAuthContext bceAuthContext) {
        Set<BceRole> roles = bceAuthContext.getBceRoles();
        return roles.contains(BceRole.SERVICE)
                || roles.contains(BceRole.PRIVILEGED_SERVICE)
                || roles.contains(BceRole.CLOUD_ADMIN);
    }
}
