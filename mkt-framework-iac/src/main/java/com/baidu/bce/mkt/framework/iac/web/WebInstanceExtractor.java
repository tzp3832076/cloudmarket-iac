// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.CollectionUtils;

import lombok.AllArgsConstructor;

/**
 * extract instance from request
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@AllArgsConstructor
public abstract class WebInstanceExtractor {
    protected String name;

    public List<String> extract(HttpServletRequest request) {
        List<String> extracted = doExtract(request);
        if (CollectionUtils.isEmpty(extracted)) {
            throw new IllegalArgumentException("no instance found by name = " + name);
        }
        return extracted;
    }

    protected abstract List<String> doExtract(HttpServletRequest request);
}
