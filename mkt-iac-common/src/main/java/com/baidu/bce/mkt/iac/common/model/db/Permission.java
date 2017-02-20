// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;

import lombok.Data;

/**
 * permission model
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
public class Permission {
    private long id;
    private String resource;
    private String operation;
    private String description;
    private Timestamp createTime;
    private Timestamp updateTime;
}
