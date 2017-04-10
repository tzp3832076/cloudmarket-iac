// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.model;

/**
 * invalid vendor exception
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class InvalidVendorException extends RuntimeException {
    public InvalidVendorException() {
        super("invalid vendor");
    }
}
