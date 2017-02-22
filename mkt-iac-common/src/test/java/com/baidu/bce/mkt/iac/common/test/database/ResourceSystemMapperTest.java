// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.database;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bae.commons.test.InitDatabase;
import com.baidu.bce.mkt.iac.common.mapper.ResourceSystemMapper;
import com.baidu.bce.mkt.iac.common.model.db.ResourceSystem;

/**
 * resource system mapper test
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@InitDatabase(tables = "mkt_resource_system")
public class ResourceSystemMapperTest extends BaseMapperTest {
    @Autowired
    private ResourceSystemMapper resourceSystemMapper;

    @Test
    public void testSave() {
        ResourceSystem resourceSystem = new ResourceSystem();
        resourceSystem.setResource("activity");
        resourceSystem.setSystem("MKT-PRODUCT");
        int ret = resourceSystemMapper.save(resourceSystem);
        Assert.assertEquals(1, ret);
    }

    @Test
    public void testGet() {
        ResourceSystem resourceSystem = resourceSystemMapper.getByResource("audit");
        Assert.assertEquals("MKT-AUDIT", resourceSystem.getSystem());
    }
}
