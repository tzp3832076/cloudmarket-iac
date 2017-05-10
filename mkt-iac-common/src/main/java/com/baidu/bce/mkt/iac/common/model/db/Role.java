// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.model.db;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

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
    private String subRoles;
    private Timestamp createTime;
    private Timestamp updateTime;

    public List<String> getSubRoleList() {
        if (StringUtils.isNotBlank(subRoles)) {
            String[] roles = subRoles.split(",");
            return Arrays.asList(roles);
        }
        return null;
    }
}
