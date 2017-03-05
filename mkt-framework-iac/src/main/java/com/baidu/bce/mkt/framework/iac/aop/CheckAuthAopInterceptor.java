// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.StringUtils;

import com.baidu.bce.mkt.framework.iac.annotation.CheckAuth;
import com.baidu.bce.mkt.framework.iac.instance.ArrayResolver;
import com.baidu.bce.mkt.framework.iac.instance.BeanResolver;
import com.baidu.bce.mkt.framework.iac.instance.MethodParser;
import com.baidu.bce.mkt.framework.iac.instance.Resolver;
import com.baidu.bce.mkt.framework.iac.instance.StringResolver;
import com.baidu.bce.mkt.framework.iac.model.AuthorizedToken;
import com.baidu.bce.mkt.framework.iac.model.ModelUtils;
import com.baidu.bce.mkt.framework.iac.model.TokenHolder;
import com.baidu.bce.mkt.framework.iac.service.CheckAuthService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * interceptor for aop impl
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
@RequiredArgsConstructor
public class CheckAuthAopInterceptor extends MethodParser<AopInstanceExtractor> implements MethodInterceptor {
    private final CheckAuthService checkAuthService;

    private Map<Method, Context> methodCache = new ConcurrentHashMap<>();

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        log.debug("[CHECK AUTH AOP] begin");
        Method method = methodInvocation.getMethod();
        Context context = methodCache.get(method);
        if (context == null) {
            context = parseMethod(method);
            methodCache.put(method, context);
        } else {
            log.debug("found method context in cache");
        }
        List<String> instances = context.getInstanceExtractor() == null
                ? null : context.getInstanceExtractor().extract(methodInvocation);
        AuthorizedToken authorizedToken = checkAuthService.checkAuth(ModelUtils.createCurrentBceAuthContextWrapper(),
                ModelUtils.createHeaderUser(), context.getResource(), context.getOperation(), instances);
        TokenHolder.setAuthorizedToken(authorizedToken);
        try {
            return methodInvocation.proceed();
        } finally {
            TokenHolder.removeAuthorizedToken();
            log.debug("[CHECK AUTH AOP] end");
        }
    }

    private Context parseMethod(Method method) {
        CheckAuth checkAuth = method.getAnnotation(CheckAuth.class);
        Context context = new Context();
        context.setResource(checkAuth.resource());
        context.setOperation(checkAuth.operation());
        String instanceParameterName = checkAuth.instanceParameterName();
        AopInstanceExtractor instanceExtractor = StringUtils.isNotEmpty(instanceParameterName)
                ? parseByInstanceParameterName(method, instanceParameterName) : parseInstanceForCheck(method);
        context.setInstanceExtractor(instanceExtractor);
        context.setAuthorizedTokenIndex(parseAuthorizedTokenIndex(method));
        return context;
    }

    @Override
    protected AopInstanceExtractor parseToInstanceExtractor(Method method, Parameter parameter, int index,
                                                            String propertyName) {
        Class type = parameter.getType();
        return new AopInstanceExtractor(index, parseToResolver(type, propertyName));
    }

    private Integer parseAuthorizedTokenIndex(Method method) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (AuthorizedToken.class == parameter.getType()) {
                return i;
            }
        }
        return null;
    }

    private Resolver parseToResolver(Class type, String propertyName) {
        if (isStringSupportType(type)) {
            return new StringResolver();
        } else if (isArrayType(type) || isListType(type)) {
            return new ArrayResolver();
        } else {
            if (StringUtils.isEmpty(propertyName)) {
                throw new IllegalArgumentException("property name is empty");
            }
            try {
                Class fieldType = type.getDeclaredField(propertyName).getType();
                return new BeanResolver(propertyName, parseToResolver(fieldType, null));
            } catch (NoSuchFieldException e) {
                throw new IllegalStateException("can not find field by property name = " + propertyName);
            }
        }
    }

    private boolean isStringSupportType(Class clazz) {
        return String.class == clazz || int.class == clazz || long.class == clazz;
    }

    private boolean isArrayType(Class clazz) {
        return clazz.isArray();
    }

    private boolean isListType(Class clazz) {
        return List.class == clazz;
    }

    @Data
    public static class Context {
        private String resource;
        private String operation;
        private AopInstanceExtractor instanceExtractor;
        private Integer authorizedTokenIndex;
    }
}
