/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */

package com.baidu.bce.mkt.iac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.bce.internalsdk.mkt.iac.model.ParamMapResponse;
import com.baidu.bce.mkt.iac.helper.ParamProperties;
import com.baidu.bce.mkt.iac.helper.ParamsHelper;
import com.wordnik.swagger.annotations.ApiOperation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/3 by sunfangyuan@baidu.com .
 */
@RestController
@RequestMapping("/v1/auditparams")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ParamsController {
    private final ParamProperties paramProperties;
    private final ParamsHelper paramsHelper;

    @ApiOperation(value = "获取params映射关系")
    @RequestMapping(method = RequestMethod.GET)
    public ParamMapResponse getParamTitlesMap() {
        log.debug("paramProperties {}", paramProperties.getMap());
        return paramsHelper.toParamMapResponse(paramProperties.getMap());
    }
}
