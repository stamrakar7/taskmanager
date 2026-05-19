package com.demo.taskmanager.service;

import com.demo.taskmanager.dto.UserDto;
import java.util.List;

public interface UserService {
    UserDto.Response createUser(UserDto.Request request);
    UserDto.Response getUserById(Long id);
    List<UserDto.Response> getAllUsers();
    UserDto.Response updateUser(Long id, UserDto.Request request);
    void deleteUser(Long id);
}