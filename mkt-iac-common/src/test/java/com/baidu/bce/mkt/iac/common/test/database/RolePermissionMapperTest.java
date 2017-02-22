// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.RolePermissionMapper;
import com.baidu.bce.mkt.iac.common.model.db.PermissionAction;
import com.baidu.bce.mkt.iac.common.model.db.RolePermission;

/**
 * role permission mapper test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@InitDatabase(tables = "mkt_role_permission")
public class RolePermissionMapperTest extends BaseMapperTest {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Test
    public void testSave() {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRole("user");
        rolePermission.setResource("product");
        rolePermission.setOperation("read");
        rolePermission.setAction(PermissionAction.ALLOW);
        int ret = rolePermissionMapper.save(rolePermission);
        Assert.assertEquals(1, ret);
    }

    @Test
    public void testGet() {
        RolePermission rolePermission =
                rolePermissionMapper.getByRoleResourceOperation("OP", "audit", "audit");
        Assert.assertEquals(1, rolePermission.getId());
        Assert.assertEquals(PermissionAction.ALLOW, rolePermission.getAction());
    }
}
