// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test;

import java.util.Random;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisServer;

/**
 * redis test helper
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
public class RedisHelper {
    public static RedisServer startRedisServerWithRandomPort(JedisConnectionFactory jedisConnectionFactory) {
        for (int i = 0; i < 3; i++) {
            int port = 10000 + new Random().nextInt(5000);
            try {
                RedisServer redisServer = RedisServer.builder()
                        .port(port)
                        .setting("maxheap 16M")
                        .setting("bind 127.0.0.1")
                        .build();
                redisServer.start();
                jedisConnectionFactory.setPort(port);
                jedisConnectionFactory.setShardInfo(null);
                jedisConnectionFactory.afterPropertiesSet();
                return redisServer;
            } catch (Exception e) {
                log.warn("start redis server failed", e);
            }
        }
        return null;
    }
}
