// Copyright 2017 Baidu Inc. All rights reserved.

package com.baidu.bce.mkt.iac.common.test;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import redis.embedded.RedisExecProvider;
import redis.embedded.RedisServer;
import redis.embedded.RedisServerBuilder;
import redis.embedded.util.OS;
import redis.embedded.util.OsArchitecture;

/**
 * redis test helper
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Slf4j
@Component
public class RedisHelper {
    @Value("${mkt.test.redis.linux.path:}")
    private String linuxPath;
    @Value("${mkt.test.redis.windows.path:}")
    private String windowsPath;

    public RedisServer startRedisServerWithRandomPort(JedisConnectionFactory jedisConnectionFactory) {
        for (int i = 0; i < 3; i++) {
            int port = 10000 + new Random().nextInt(5000);
            try {
                RedisServerBuilder redisServerBuilder = RedisServer.builder()
                        .redisExecProvider(getProvider())
                        .port(port)
                        .setting("bind 127.0.0.1");
                OsArchitecture osArch = OsArchitecture.detect();
                if (osArch.os() == OS.WINDOWS) {
                    redisServerBuilder.setting("maxheap 16M");
                }
                RedisServer redisServer = redisServerBuilder.build();
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

    private RedisExecProvider getProvider() {
        RedisExecProvider customProvider = RedisExecProvider.defaultProvider();
        if (StringUtils.isNotBlank(linuxPath)) {
            customProvider = customProvider.override(OS.UNIX, linuxPath);
        }
        if (StringUtils.isNotBlank(windowsPath)) {
            customProvider = customProvider.override(OS.WINDOWS, windowsPath);
        }
        return customProvider;
    }
}
