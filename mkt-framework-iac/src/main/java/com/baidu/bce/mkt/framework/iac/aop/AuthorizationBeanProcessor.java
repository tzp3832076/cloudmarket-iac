// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.aop;

import java.lang.annotation.Annotation;

import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.autoproxy.AbstractBeanFactoryAwareAdvisingPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;

import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;

import lombok.RequiredArgsConstructor;

/**
 * bean processor to generate proxy
 * use spring aop
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@RequiredArgsConstructor
public class AuthorizationBeanProcessor extends AbstractBeanFactoryAwareAdvisingPostProcessor
        implements InitializingBean {
    private final CheckAuthAopInterceptor checkAuthAopInterceptor;

    private Class<? extends Annotation> checkAuthAnnotationType = CheckAuth.class;

    @Override
    public void afterPropertiesSet() throws Exception {
        Pointcut pointcut = AnnotationMatchingPointcut.forMethodAnnotation(checkAuthAnnotationType);
        this.advisor = new DefaultPointcutAdvisor(pointcut, checkAuthAopInterceptor);
    }
}
