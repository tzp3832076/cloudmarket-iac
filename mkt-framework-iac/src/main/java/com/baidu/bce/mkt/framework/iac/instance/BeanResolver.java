// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.instance;

import java.util.List;

import org.springframework.beans.BeanWrapperImpl;

import lombok.AllArgsConstructor;

/**
 * extract from java bean
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@AllArgsConstructor
public class BeanResolver implements Resolver {
    private String propertyName;
    private Resolver resolver;

    @Override
    public List<String> resolve(Object value) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        Object property = beanWrapper.getPropertyValue(propertyName);
        return resolver.resolve(property);
    }
}
