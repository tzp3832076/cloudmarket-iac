// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;

import lombok.Data;

/**
 * role model
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
public class Role {
    private long id;
    private String role;
    private String description;
    private Timestamp createTime;
    private Timestamp updateTime;
}
