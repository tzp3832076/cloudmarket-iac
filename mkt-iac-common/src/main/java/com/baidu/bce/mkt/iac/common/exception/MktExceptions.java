// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.exception;

import com.baidu.bce.plat.webframework.exception.BceException;

/**
 * exception collection for mkt iac
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class MktExceptions {
    public static BceException noPermission() {
        return new BceException("No Permission", 403, "NoPermission");
    }
}
