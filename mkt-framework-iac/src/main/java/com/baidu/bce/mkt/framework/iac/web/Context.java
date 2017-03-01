// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.framework.iac.web;

import lombok.Data;

/**
 * auth context
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
public class Context {
    private String resource;
    private String operation;
    private WebInstanceExtractor instanceExtractor;
}
