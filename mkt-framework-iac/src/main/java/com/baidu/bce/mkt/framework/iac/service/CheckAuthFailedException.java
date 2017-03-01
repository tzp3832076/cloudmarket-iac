// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.service;

import com.baidu.bce.plat.webframework.exception.BceException;

/**
 * authorization exception
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class CheckAuthFailedException extends BceException {
    public CheckAuthFailedException() {
        super("鉴权失败", 403, "CheckAuthFailed");
    }

    public CheckAuthFailedException(String message, int httpStatus, String code) {
        super(message, httpStatus, code);
    }
}
