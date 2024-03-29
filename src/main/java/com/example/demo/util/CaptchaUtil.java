package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CaptchaUtil {
    @Autowired
    private RedisUtil redisUtil;
    private static final long expireTime = 60 * 5;
    public boolean checkCaptcha(String captcha, String captchaId) {
        if(captcha == null) return false;
        Object originCaptcha = redisUtil.get(captchaId);
        if(originCaptcha.equals(captcha)) {
            return true;
        }
        return false;
    }
    public void setCaptcha(String captchaId, String text) {
        redisUtil.set(captchaId, text, expireTime, TimeUnit.SECONDS);
    }

}
