// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.PermissionMapper;
import com.baidu.bce.mkt.iac.common.model.db.Permission;

/**
 * permission mapper test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@InitDatabase(tables = "mkt_permission")
public class PermissionMapperTest extends BaseMapperTest {
    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    public void testSave() {
        Permission permission = new Permission();
        permission.setResource("vendor");
        permission.setOperation("update");
        permission.setDescription("更新服务商信息");
        int ret = permissionMapper.save(permission);
        Assert.assertEquals(1, ret);
    }

    @Test
    public void testGet() {
        Permission permission = permissionMapper.getByResourceAndOperation("product", "read");
        Assert.assertEquals(1, permission.getId());
    }
}
