// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * extract by @RequestParam
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class RequestParamExtractor extends WebInstanceExtractor {
    public RequestParamExtractor(String name) {
        super(name);
    }

    @Override
    public List<String> doExtract(HttpServletRequest request) {
        String[] value = request.getParameterValues(name);
        return value == null ? null : Arrays.asList(value);
    }
}
