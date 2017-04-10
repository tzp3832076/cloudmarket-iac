// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.test.rule;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * current vendor
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Target(value = {ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentVendor {
    String vendorId() default "";
    boolean hasId() default true;
    String[] targetVendors() default {};
}
