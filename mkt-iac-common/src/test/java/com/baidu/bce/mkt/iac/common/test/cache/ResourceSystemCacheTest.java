// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.cache;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.iac.common.cache.ResourceSystemCache;
import com.baidu.bce.mkt.iac.common.mapper.ResourceSystemMapper;
import com.baidu.bce.mkt.iac.common.model.db.ResourceSystem;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;

/**
 * resource system cache test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
public class ResourceSystemCacheTest extends BaseCommonServiceTest {
    @Autowired
    private ResourceSystemCache resourceSystemCache;
    @Autowired
    private ResourceSystemMapper resourceSystemMapper;

    @Test
    public void testGetSystemFromCache() {
        String system = resourceSystemCache.getSystem("audit");
        Assert.assertEquals("MKT_AUDIT", system);
    }

    @Test
    public void testRefreshCache() {
        String system = resourceSystemCache.getSystem("newOne");
        Assert.assertNull(system);
        ResourceSystem resourceSystem = new ResourceSystem();
        resourceSystem.setResource("newOne");
        resourceSystem.setSystem(IdUtils.generateUUID().replaceAll("-", ""));
        resourceSystemMapper.save(resourceSystem);
        resourceSystemCache.doRefresh();
        system = resourceSystemCache.getSystem("newOne");
        Assert.assertEquals(resourceSystem.getSystem(), system);
    }
}
