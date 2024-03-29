package com.example.demo.controller;

import com.example.demo.dto.CreateReq;
import com.example.demo.dto.UpdateReq;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @GetMapping("/user/id/{username}")
    @PostAuthorize("returnObject.body.username == authentication.name")
    public ResponseEntity<UserEntity> findUserByUserName(@PathVariable String username) {
        UserEntity user = userService.findUserByUserName(username);
        ResponseEntity<UserEntity> res = new ResponseEntity<>(user, HttpStatus.OK);
        return res;
    }
    @GetMapping("/user/all")
//    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<String> updateUser(@RequestBody UpdateReq updateReq) {
        String username = updateReq.getUsername();
        String role = updateReq.getRole();
        userService.updateUser(username, role);
        return new ResponseEntity<>("User " + username + " is updated", HttpStatus.OK);
    }

    @PostMapping("/user/create")
    public ResponseEntity<String> createUser(@RequestBody CreateReq createReq) throws IOException {
        String username = createReq.getUsername();
        String password = createReq.getPassword();
        String role = createReq.getRole();
        if (username.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("username or password cannot be null", HttpStatus.BAD_REQUEST);
        }
        if (userService.findUserByUserName(username) != null) {
            return new ResponseEntity<>("user already existed", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        userService.saveUser(user);
        return new ResponseEntity<>("User " + username + " is created", HttpStatus.OK);
    }

    @GetMapping("/user/online")
    public ResponseEntity<?> findOnlineUsers() {
        List<?> userList = redisUtil.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}
