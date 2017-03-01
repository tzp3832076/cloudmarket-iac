/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.bce.mkt.iac.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created on 2017/2/28 by sunfangyuan@baidu.com .
 */
public class CheckUtilsTest {
    @Test
    public void checkEmail() throws Exception {
        boolean res = CheckUtils.checkEmail("sfy@baidu.com");
        Assert.assertTrue(res);
        res = CheckUtils.checkEmail("sfybaidu.com");
        Assert.assertFalse(res);
    }

    @Test
    public void checkMobileNumber() throws Exception {
        boolean res = CheckUtils.checkMobileNumber("17721");
        Assert.assertFalse(res);
        res = CheckUtils.checkMobileNumber("17710655554");
        Assert.assertTrue(res);
    }

}