// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 鉴权注解类，用于controller层，标记controller接口需要鉴权的资源、操作等
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Target(value = {ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface CheckAuth {
    // 需要鉴权的资源名称
    String resource();
    // 需要鉴权的操作名称，接口上线时，需要将资源名称 + 操作名称配置到iac模块中，添加permission
    String operation();
    // 需要鉴权的实例对应的参数的名称，供框架从请求参数中提取对应的值
    String instanceParameterName() default "";
}
