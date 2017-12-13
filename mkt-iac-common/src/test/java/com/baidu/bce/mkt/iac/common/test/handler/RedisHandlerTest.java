// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test.handler;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import com.baidu.bce.mkt.framework.utils.IdUtils;
import com.baidu.bce.mkt.iac.common.handler.RedisHandler;
import com.baidu.bce.mkt.iac.common.model.OwnerInfoForRedis;
import com.baidu.bce.mkt.iac.common.test.BaseCommonServiceTest;
import com.baidu.bce.mkt.iac.common.test.RedisHelper;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

/**
 *  redis handler test
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class RedisHandlerTest extends BaseCommonServiceTest {
    @Autowired
    private RedisHandler redisHandler;
    @Autowired
    private JedisConnectionFactory jedisConnectionFactory;
    @Autowired
    private RedisHelper redisHelper;

    private RedisServer redisServer;

    @Before
    public void setUp() {
        redisServer = redisHelper.startRedisServerWithRandomPort(jedisConnectionFactory);
    }

    @After
    public void clean() {
        redisServer.stop();
    }

    @Test
    public void testMultiSet() {
        OwnerInfoForRedis owner = new OwnerInfoForRedis();
        owner.setUserId(IdUtils.generateShortUUID());
        owner.setVendorId(IdUtils.generateUUID());
        boolean ret = redisHandler.multiSet(Arrays.asList("testkey1", "testkey2"), owner, 3600L);
        Assert.assertTrue(ret);
        OwnerInfoForRedis value = redisHandler.get("testkey1", OwnerInfoForRedis.class);
        Assert.assertEquals(owner, value);
    }

    @Test
    public void testMultiGet() {
        OwnerInfoForRedis owner1 = new OwnerInfoForRedis();
        owner1.setUserId(IdUtils.generateShortUUID());
        owner1.setVendorId(IdUtils.generateUUID());
        OwnerInfoForRedis owner2 = new OwnerInfoForRedis();
        owner2.setUserId(IdUtils.generateShortUUID());
        owner2.setVendorId(IdUtils.generateUUID());
        redisHandler.set("testkey1", owner1);
        redisHandler.set("testkey2", owner2);
        List<OwnerInfoForRedis> ownerInfoList = redisHandler
                .multiGet(Arrays.asList("testkey1", "testkey2", "testkey3"), OwnerInfoForRedis.class);
        Assert.assertEquals(owner1, ownerInfoList.get(0));
        Assert.assertEquals(owner2, ownerInfoList.get(1));
        Assert.assertNull(ownerInfoList.get(2));
    }
}
