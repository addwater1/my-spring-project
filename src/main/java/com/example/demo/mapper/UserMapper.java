package com.example.demo.mapper;

import com.example.demo.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    public UserEntity findUserByUserName(String username);
    public List<UserEntity> findAllUsers();
    public void saveUser(UserEntity user);
    public void deleteUserByUserName(String username);
    public void updateUser(String username, String role);
}
