// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.instance;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.web.bind.annotation.PathVariable;

import com.baidu.bce.mkt.framework.iac.annotation.InstanceForCheck;

/**
 * common method for parse method
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public abstract class MethodParser<T> {
    public T parseByInstanceParameterName(Method method, String instanceParameterName) {
        String[] names = instanceParameterName.split("\\.");
        String name = names[0];
        String propertyName = null;
        if (names.length > 1) {
            propertyName = names[1];
        }
        Parameter[] parameters = method.getParameters();
        T instanceExtractor = null;
        DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(method);
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String parameterName = parameterNames[i];
            if (name.equals(parameterName)) {
                instanceExtractor = parseToInstanceExtractor(method, parameter, i, propertyName);
                break;
            }
        }
        if (instanceExtractor == null) {
            throw new IllegalStateException("parse instance by parameter name = "
                    + instanceParameterName + " failed");
        }
        return instanceExtractor;
    }

    public T parseInstanceForCheck(Method method) {
        Parameter[] parameters = method.getParameters();
        boolean hasPathVariable = false;
        T instanceExtractor = null;
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            InstanceForCheck instanceForCheckAnnotation = parameter.getAnnotation(InstanceForCheck.class);
            PathVariable pathVariableAnnotation = parameter.getAnnotation(PathVariable.class);
            if (pathVariableAnnotation != null) {
                hasPathVariable = true;
            }
            if (instanceForCheckAnnotation != null) {
                instanceExtractor = parseToInstanceExtractor(method, parameter, i,
                        instanceForCheckAnnotation.propertyNameInBean());
                break;
            }
        }
        if (instanceExtractor == null && hasPathVariable) {
            throw new IllegalArgumentException("@PathVariable found but @InstanceForCheck not found");
        }
        return instanceExtractor;
    }

    protected abstract T parseToInstanceExtractor(Method method, Parameter parameter, int index, String propertyName);
}
