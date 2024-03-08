package com.example.demo.controller;

import com.example.demo.util.CaptchaUtil;
import com.example.demo.util.RedisUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CaptchaController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CaptchaUtil captchaUtil;
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response) throws IOException {
        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);

        String uuid = UUID.randomUUID().toString();
        captchaUtil.setCaptcha(uuid, text);

        response.setHeader("Captcha-UUID", uuid);
        response.setContentType("image/jpeg");
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        outputStream.close();
    }

    @GetMapping("/captcha/{uuid}")
    public void getCaptchaAgain(HttpServletResponse response, @PathVariable String uuid) throws IOException {
        String text = defaultKaptcha.createText();
        BufferedImage image = defaultKaptcha.createImage(text);

        captchaUtil.setCaptcha(uuid, text);

        response.setContentType("image/jpeg");
        OutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        outputStream.close();
    }
}
