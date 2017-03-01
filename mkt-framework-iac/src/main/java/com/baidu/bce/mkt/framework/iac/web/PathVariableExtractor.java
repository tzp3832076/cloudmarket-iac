// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

/**
 * extract by @PathVariable
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class PathVariableExtractor extends WebInstanceExtractor {
    public PathVariableExtractor(String name) {
        super(name);
    }

    @Override
    public List<String> doExtract(HttpServletRequest request) {
        // code from spring PathVariableMethodArgumentResolver
        Map<String, String> uriTemplateVars =
                (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String value = (uriTemplateVars != null ? uriTemplateVars.get(name) : null);
        if (value != null) {
            return Arrays.asList(value);
        } else {
            return null;
        }
    }
}
