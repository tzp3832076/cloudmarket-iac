// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationRequest;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizationResponse;
import com.baidu.bce.internalsdk.mkt.iac.model.AuthorizedToken;

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
    @RequestMapping(method = RequestMethod.POST)
    public AuthorizationResponse checkAuth(@RequestBody AuthorizationRequest request) {
        AuthorizationResponse response = new AuthorizationResponse();
        AuthorizedToken token = new AuthorizedToken();
        response.setToken(token);
        return response;
    }
}
