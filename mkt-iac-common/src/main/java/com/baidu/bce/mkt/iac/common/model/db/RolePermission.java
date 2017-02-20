// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;

import lombok.Data;

/**
 * role permission model
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Data
public class RolePermission {
    private long id;
    private String role;
    private String resource;
    private String operation;
    private PermissionAction action;
    private Timestamp createTime;
    private Timestamp updateTime;
}
