package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/{username}")
    public ResponseEntity<UserEntity> findUserByUserName(@PathVariable String username) {
        UserEntity user = userService.findUserByUserName(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
