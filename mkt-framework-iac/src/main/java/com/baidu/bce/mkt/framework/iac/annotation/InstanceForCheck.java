// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记在controller的方法的参数上面，表示当前参数作为instance进行鉴权
 * 如果controller的方法的参数中没有添加@InstanceForCheck， 但有参数被标记了@PathVariable，则会抛出异常
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Target(value = {ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface InstanceForCheck {
    String propertyNameInBean() default "";
}
