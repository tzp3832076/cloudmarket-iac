// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.baidu.bce.mkt.framework.iac.web.SubjectResolverConfiguration;

/**
 * annotation to open @Subject function
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(SubjectResolverConfiguration.class)
public @interface EnableSubject {
}
