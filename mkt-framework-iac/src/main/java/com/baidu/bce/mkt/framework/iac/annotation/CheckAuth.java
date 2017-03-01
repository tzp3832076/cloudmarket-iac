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
    String resource();
    String operation();
    String instanceParameterName() default "";
}
