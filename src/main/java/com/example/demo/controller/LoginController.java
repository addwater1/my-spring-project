package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.dto.LoginRes;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.RedisUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            return new ResponseEntity<>("username or password invalid", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userService.findUserByUserName(username);
        long expireInMs = 1000 * 60 * 60;
        String jwt = jwtUtil.generate(user.getUsername(), expireInMs);
        LoginRes res = new LoginRes(user.getUsername(), jwt);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
