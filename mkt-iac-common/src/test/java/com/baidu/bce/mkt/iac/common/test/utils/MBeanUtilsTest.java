/*
 * Copyright 2017 Baidu Inc. All rights reserved.
 */
package com.baidu.bce.mkt.iac.common.test.utils;

import org.junit.Assert;
import org.junit.Test;

import com.baidu.bce.mkt.iac.common.utils.MBeanUtils;

import lombok.Data;

/**
 * Created by chenxiang05@baidu.com on 2018/8/16.
 */
public class MBeanUtilsTest {

    @Test
    public void testApplyProperty() {

        Foo foo1 = new Foo();
        Foo foo2 = new Foo();
        foo2.setField1("aa");
        foo2.setField3(123L);
        MBeanUtils.applyProperties(foo1, foo2, Foo.class);
        Assert.assertEquals("aa", foo1.getField1());
        Assert.assertEquals(123L, foo1.getField3().longValue());
        Assert.assertNull(foo1.getField2());

    }

    @Test
    public void testApplyPropertyCase2() {

        Foo foo = new Foo();
        Foo1 foo1 = new Foo1();
        foo1.setField1("aa");
        foo1.setField3(123L);
        MBeanUtils.applyProperties(foo, foo1);
        Assert.assertEquals("aa", foo.getField1());
        Assert.assertEquals(123L, foo.getField3().longValue());
        Assert.assertNull(foo.getField2());

    }

    @Data
    public static class Foo {
        String field1;
        String field2;
        Long field3;
    }

    @Data
    public static class Foo1 {
        String field1;
        String field2;
        Long field3;
    }
}
