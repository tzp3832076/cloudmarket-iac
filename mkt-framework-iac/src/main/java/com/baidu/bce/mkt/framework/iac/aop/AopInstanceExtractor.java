// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.aop;

import java.util.List;

import org.aopalliance.intercept.MethodInvocation;

import com.baidu.bce.mkt.framework.iac.instance.Resolver;

import lombok.AllArgsConstructor;

/**
 * aop instance extractor
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@AllArgsConstructor
public class AopInstanceExtractor {
    private int parameterIndex;
    private Resolver resolver;

    public List<String> extract(MethodInvocation methodInvocation) {
        return resolver.resolve(methodInvocation);
    }

    protected Object getInstanceArgument(MethodInvocation methodInvocation) {
        Object[] args = methodInvocation.getArguments();
        if (args.length <= parameterIndex) {
            throw new IllegalArgumentException("can not find index = "
                    + parameterIndex + " in method args, method name = "
                    + methodInvocation.getMethod().getName());
        }
        Object value = args[parameterIndex];
        if (value == null) {
            throw new IllegalArgumentException("argument is null in method = "
                    + methodInvocation.getMethod().getName() + " index = " + parameterIndex);
        }
        return value;
    }
}
