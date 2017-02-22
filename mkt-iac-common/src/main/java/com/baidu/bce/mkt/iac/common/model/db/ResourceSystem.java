// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model.db;

import lombok.Data;

/**
 * resource system
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
public class ResourceSystem {
    private long id;
    private String resource;
    private String system;
}
