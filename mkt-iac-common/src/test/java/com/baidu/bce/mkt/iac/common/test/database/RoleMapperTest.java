// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.RoleMapper;
import com.baidu.bce.mkt.iac.common.model.db.Role;

/**
 * role mapper test
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@InitDatabase(tables = "mkt_role")
public class RoleMapperTest extends BaseMapperTest {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testSave() {
        Role role = new Role();
        role.setRole("vendor");
        role.setDescription("服务商");
        int ret = roleMapper.save(role);
        Assert.assertEquals(1, ret);
    }

    @Test
    public void testGet() {
        Role role = roleMapper.getByRole("OP");
        Assert.assertEquals("运营", role.getDescription());
    }
}
