/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.test.controller;

import org.junit.Test;

import com.baidu.bce.internalsdk.mkt.iac.model.ParamMapResponse;
import com.baidu.bce.mkt.iac.test.ApiMockMvcTest;

import lombok.extern.slf4j.Slf4j;

/**
 * Created on 2017/3/3 by sunfangyuan@baidu.com .
 */
@Slf4j
public class ParamsControllerTest extends ApiMockMvcTest {

    @Test
    public void getParams() {
        ParamMapResponse response = mktIacClient.getParamsMap();
        log.info("getParams {}", response);
    }

}