package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;
    @Autowired
    @Qualifier("redisTemplateSecond")
    private RedisTemplate redisTemplateSecond;

    public void set(String key, String value, long time, TimeUnit timeUnit) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
        }
        else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    public void addUser(String key, String value) {
        redisTemplateSecond.opsForValue().set(key, value);
    }

    public boolean deleteUser(String key) {
        return redisTemplateSecond.delete(key);
    }

    public List<?> getAllUsers() {
        Set<String> keys = redisTemplateSecond.keys("*");
        List<Map<String, Object>> userList = new ArrayList<>();
        for (String key : keys) {
            Map<String, Object> user = new HashMap<>();
            user.put("key", key);
            user.put("value", redisTemplateSecond.opsForValue().get(key));
            userList.add(user);
        }
        return userList;
    }

    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }
}
