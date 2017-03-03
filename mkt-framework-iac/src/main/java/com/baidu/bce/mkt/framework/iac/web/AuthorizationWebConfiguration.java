// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.framework.Advised;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.framework.iac.instance.MethodParser;
import com.baidu.bce.mkt.framework.iac.service.CheckAuthService;
import com.baidu.bce.mkt.framework.iac.service.RemoteCheckAuthService;
import com.baidu.bce.plat.webframework.iam.config.access.service.BceSignatureValidateConfiguration;

import lombok.extern.slf4j.Slf4j;

/**
 * authorization web configuration
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Configuration
@AutoConfigureAfter(BceSignatureValidateConfiguration.class)
@Slf4j
public class AuthorizationWebConfiguration extends WebMvcConfigurerAdapter {
    @Value("${mkt.iac.check.auth.exclude.paths:${iam.signature.exclude.paths:}}")
    private String excludePaths;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private CheckAuthService checkAuthService;

    @Bean(name = CheckAuthService.BEAN_NAME)
    @ConditionalOnMissingBean(CheckAuthService.class)
    public CheckAuthService authorizationService() {
        return new RemoteCheckAuthService();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(Controller.class);
        if (!CollectionUtils.isEmpty(beanMap)) {
            log.info("current check auth service type is {}", checkAuthService.getClass().getName());
            Map<Method, Context> methodCache = parseToMethodCache(beanMap);
            registry.addInterceptor(new CheckAuthWebInterceptor(methodCache, checkAuthService))
                    .addPathPatterns("/**")
                    .excludePathPatterns(excludePaths.split(";"));
            log.info("check auth web interceptor enabled");
        } else {
            log.info("check auth web interceptor not enable");
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        SubjectMethodArgumentResolver subjectMethodArgumentResolver = new SubjectMethodArgumentResolver();
        VendorIdMethodArgumentResolver vendorIdMethodArgumentResolver = new VendorIdMethodArgumentResolver();
        argumentResolvers.add(subjectMethodArgumentResolver);
        argumentResolvers.add(vendorIdMethodArgumentResolver);
    }

    private Map<Method, Context> parseToMethodCache(Map<String, Object> beanMap) {
        WebMethodParser webMethodParser = new WebMethodParser();
        Map<Method, Context> methodCache = new HashMap<>();
        for (Object object : beanMap.values()) {
            Class clazz = object.getClass();
            if (object instanceof Advised) {
                Advised advised = (Advised) object;
                clazz = advised.getTargetClass();
            }
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                Context context = parseMethod(method, webMethodParser);
                if (context != null) {
                    methodCache.put(method, context);
                }
            }
        }
        return methodCache;
    }

    private Context parseMethod(Method method, WebMethodParser webMethodParser) {
        CheckAuth checkAuth = method.getAnnotation(CheckAuth.class);
        if (checkAuth == null) {
            return null;
        }
        Context context = new Context();
        context.setResource(checkAuth.resource());
        context.setOperation(checkAuth.operation());

        String instanceParameterName = checkAuth.instanceParameterName();
        WebInstanceExtractor webInstanceExtractor = StringUtils.isNotEmpty(instanceParameterName)
                ? webMethodParser.parseByInstanceParameterName(method, instanceParameterName) :
                webMethodParser.parseInstanceForCheck(method);
        context.setInstanceExtractor(webInstanceExtractor);
        return context;
    }

    public static class WebMethodParser extends MethodParser<WebInstanceExtractor> {

        @Override
        protected WebInstanceExtractor parseToInstanceExtractor(Method method, Parameter parameter, int index,
                                                                String propertyName) {
            DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();
            String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(method);
            return createWebInstanceExtractorByParameter(parameter, parameterNames[index]);
        }

        private WebInstanceExtractor createWebInstanceExtractorByParameter(Parameter parameter, String parameterName) {
            PathVariable pathVariable = AnnotationUtils.findAnnotation(parameter, PathVariable.class);
            RequestParam requestParam = AnnotationUtils.findAnnotation(parameter, RequestParam.class);
            if (pathVariable != null) {
                return new PathVariableExtractor(StringUtils.isNotEmpty(pathVariable.value())
                        ? pathVariable.value() : parameterName);
            } else if (requestParam != null) {
                return new RequestParamExtractor(StringUtils.isNotEmpty(requestParam.value())
                        ? requestParam.value() : parameterName);
            } else {
                throw new IllegalArgumentException("no @PathVariable or @PathRequestParam found on param = "
                        + parameterName);
            }
        }
    }
}
