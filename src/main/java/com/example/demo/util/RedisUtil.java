package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    public void set(String key, String value, long time, TimeUnit timeUnit) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        }
        else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
