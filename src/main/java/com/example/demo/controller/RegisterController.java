package com.example.demo.controller;

import com.example.demo.dto.RegisterDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CaptchaUtil captchaUtil;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        String captcha = registerDto.getCaptcha();
        String captchaId = registerDto.getCaptchaId();
        if(!captchaUtil.checkCaptcha(captcha, captchaId))
            return new ResponseEntity<>("check code is wrong", HttpStatus.BAD_REQUEST);

        String username = registerDto.getUsername();
        if (userService.findUserByUserName(username) != null) {
            return new ResponseEntity<>("user already existed", HttpStatus.BAD_REQUEST);
        }

        //insert new user profile into database
        try {
            UserEntity user = new UserEntity();
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setRole("user");
            userService.saveUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>("sign up failed", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("sign up successfully", HttpStatus.OK);
    }
}
