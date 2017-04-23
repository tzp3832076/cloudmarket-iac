// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

/**
 * invalid user type
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class InvalidUserTypeException extends RuntimeException {
    public InvalidUserTypeException() {
        super("invalid user type");
    }
}
