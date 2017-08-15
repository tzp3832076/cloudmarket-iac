// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * configuration for @Subject
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Configuration
@Slf4j
public class SubjectResolverConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        SubjectMethodArgumentResolver subjectMethodArgumentResolver = new SubjectMethodArgumentResolver();
        argumentResolvers.add(subjectMethodArgumentResolver);
    }
}
