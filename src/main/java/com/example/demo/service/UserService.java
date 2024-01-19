package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public UserEntity findUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }
    public void saveUser(UserEntity user) {
        userMapper.saveUser(user);
    }
    public List<UserEntity> findAllUsers() {
        return userMapper.findAllUsers();
    }
    public void deleteUserByUserName(String username) {
        userMapper.deleteUserByUserName(username);
    }
}
