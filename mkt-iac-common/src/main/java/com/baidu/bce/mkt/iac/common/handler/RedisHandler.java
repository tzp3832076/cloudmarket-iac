package com.baidu.bce.mkt.iac.common.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.baidu.bce.mkt.framework.utils.JsonUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * redis handler
 *
 * @author Wu Jinlin(wujinlin@baidu.com)
 */
@Component
@Slf4j
public class RedisHandler {

    @Value("${mkt.redis.key.prefix:BCE_MKT_IAC:}")
    private String redisKeyPrefix;

    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> objectClass) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            Object o = operations.get(generateKey(key));
            return convertGetValueObject(o, objectClass);
        } catch (Exception e) {
            log.warn("redis get Exception.", e);
        }
        return null;
    }

    public <T> List<T> multiGet(List<String> keys, Class<T> objectClass) {
        try {
            ValueOperations<String, Object> operations = redisTemplate.opsForValue();
            List<String> newKeys = new ArrayList<>();
            for (String key : keys) {
                newKeys.add(generateKey(key));
            }
            List values = operations.multiGet(newKeys);
            List<T> converted = new ArrayList<T>();
            for (Object value : values) {
                converted.add(convertGetValueObject(value, objectClass));
            }
            return converted;
        } catch (Exception e) {
            log.warn("redis multi get Exception.", e);
        }
        return null;
    }

    public boolean set(String key, Object value) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(generateKey(key), convertSetValueObject(value));
            return true;
        } catch (Exception e) {
            log.warn("redis set Exception.", e);
        }
        return false;
    }

    public boolean set(String key, Object value, Long expireTime) {
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(generateKey(key), convertSetValueObject(value), expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            log.warn("redis set with expireTime Exception.", e);
        }
        return false;
    }

    public boolean multiSet(List<String> keys, Object value, Long expireTime) {
        for (String key : keys) {
            boolean result = set(key, value, expireTime);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    private String generateKey(String key) {
        return redisKeyPrefix + key;
    }

    private <T> T convertGetValueObject(Object value, Class<T> convertClass) {
        if (value == null) {
            return null;
        } else if (convertClass == null || convertClass.equals(String.class) || convertClass.equals(Object.class)) {
            return (T) value;
        } else {
            return JsonUtils.fromJson((String) value, convertClass);
        }
    }

    private String convertSetValueObject(Object value) {
        if (value instanceof String) {
            return (String) value;
        } else {
            return JsonUtils.toJson(value);
        }
    }
}
