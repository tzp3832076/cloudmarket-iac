// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.aop;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baidu.bce.mkt.framework.iac.service.CheckAuthService;
import com.baidu.bce.mkt.framework.iac.service.RemoteCheckAuthService;

import lombok.extern.slf4j.Slf4j;

/**
 * authorization aop configuration
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Configuration
@Slf4j
public class AuthorizationAopConfiguration {
    public static final String BEAN_NAME_CHECK_AUTH_INTERCEPTOR = "checkAuthAopInterceptor";

    @Bean(name = CheckAuthService.BEAN_NAME)
    @ConditionalOnMissingBean(CheckAuthService.class)
    public CheckAuthService checkAuthService() {
        return new RemoteCheckAuthService();
    }

    @Bean(name = BEAN_NAME_CHECK_AUTH_INTERCEPTOR)
    public CheckAuthAopInterceptor checkAuthInterceptor(CheckAuthService checkAuthService) {
        log.info("current check auth service type is {}", checkAuthService.getClass().getName());
        return new CheckAuthAopInterceptor(checkAuthService);
    }

    @Bean
    public AuthorizationBeanProcessor authorizationBeanProcessor(CheckAuthAopInterceptor checkAuthAopInterceptor) {
        return new AuthorizationBeanProcessor(checkAuthAopInterceptor);
    }
}
