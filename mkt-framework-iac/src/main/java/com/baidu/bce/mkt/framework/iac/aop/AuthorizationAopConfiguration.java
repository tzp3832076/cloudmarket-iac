// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.aop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baidu.bce.mkt.framework.iac.aop.AuthorizationBeanProcessor;
import com.baidu.bce.mkt.framework.iac.aop.CheckAuthAopInterceptor;
import com.baidu.bce.mkt.framework.iac.service.AuthorizationService;

/**
 * authorization aop configuration
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Configuration
public class AuthorizationAopConfiguration {
    public static final String BEAN_NAME_AUTHORIZATION_SERVICE = "authorizationService";
    public static final String BEAN_NAME_CHECK_AUTH_INTERCEPTOR = "checkAuthAopInterceptor";

    @Bean(name = BEAN_NAME_AUTHORIZATION_SERVICE)
    public AuthorizationService authorizationService() {
        return new AuthorizationService();
    }

    @Bean(name = BEAN_NAME_CHECK_AUTH_INTERCEPTOR)
    public CheckAuthAopInterceptor checkAuthInterceptor(AuthorizationService authorizationService) {
        return new CheckAuthAopInterceptor(authorizationService);
    }

    @Bean
    public AuthorizationBeanProcessor authorizationBeanProcessor(CheckAuthAopInterceptor checkAuthAopInterceptor) {
        return new AuthorizationBeanProcessor(checkAuthAopInterceptor);
    }
}
