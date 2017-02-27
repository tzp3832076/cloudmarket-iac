// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationResponse;
import com.baidu.bce.mkt.iac.common.model.AuthorizeCommand;
import com.baidu.bce.mkt.iac.common.model.db.UserIdentity;
import com.baidu.bce.mkt.iac.common.service.AuthorizationService;
import com.baidu.bce.mkt.iac.helper.AuthorizationControllerHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * authentication controller
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@RestController
@RequestMapping("/v1/authorization")
@Slf4j
public class AuthorizationController {
    @Autowired
    private AuthorizationControllerHelper helper;
    @Autowired
    private AuthorizationService authorizationService;

    @RequestMapping(method = RequestMethod.POST)
    public AuthorizationResponse checkAuth(@RequestBody AuthorizationRequest request) {
        AuthorizeCommand command = helper.toAuthorizeCommand(request);
        UserIdentity userIdentity = authorizationService.authorize(command);
        return helper.toAuthorizationResponse(userIdentity);
    }
}
