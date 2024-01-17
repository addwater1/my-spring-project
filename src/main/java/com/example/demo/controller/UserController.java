package com.example.demo.controller;

import com.example.demo.dto.LoginDto;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    public ResponseEntity<String> findUserByUserName(@RequestBody LoginDto loginDto) {
        String username = loginDto.getUsername();
        User user = userService.findUserByUserName(username);
        return new ResponseEntity<>(user.toString(), HttpStatus.OK);
    }
}
