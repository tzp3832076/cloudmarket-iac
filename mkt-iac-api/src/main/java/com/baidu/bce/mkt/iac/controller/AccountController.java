/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.GetAccountRoleResponse;
import com.baidu.bce.mkt.framework.exception.UnknownExceptionResponse;
import com.baidu.bce.mkt.iac.common.service.AccountService;
import com.baidu.bce.plat.webframework.iam.config.access.annotation.BceAuth;
import com.baidu.bce.plat.webframework.iam.model.BceRole;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/24 by sunfangyuan@baidu.com .
 */
@RestController
@RequestMapping("/v1/account")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {
    private final AccountService accountService;

    @ApiOperation(value = "获取用户角色的接口")
    @RequestMapping(method = RequestMethod.GET, value = "/role/{bceUserId}")
    @BceAuth(role = {BceRole.SERVICE})
    @UnknownExceptionResponse(message = "用户角色获取失败")
    public GetAccountRoleResponse getAccountRole(@PathVariable String bceUserId) {
        return new GetAccountRoleResponse(accountService.getAccountRole(bceUserId));
    }
}
