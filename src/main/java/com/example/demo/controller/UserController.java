package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/user/id/{username}")
    @PostAuthorize("returnObject.body.username == authentication.name")
    public ResponseEntity<UserEntity> findUserByUserName(@PathVariable String username) {
        UserEntity user = userService.findUserByUserName(username);
        ResponseEntity<UserEntity> res = new ResponseEntity<>(user, HttpStatus.OK);
        return res;
    }
    @GetMapping("/user/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserEntity>> findAllUsers() {
        List<UserEntity> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/user/delete/{username}")
    public ResponseEntity<String> deleteUserByUserName(@PathVariable String username) {
        userService.deleteUserByUserName(username);
        return new ResponseEntity<>("User " + username + " is removed", HttpStatus.OK);
    }
    @PostMapping("/user/update")
    public ResponseEntity<String> updateUser() {
        return null;
    }
}
