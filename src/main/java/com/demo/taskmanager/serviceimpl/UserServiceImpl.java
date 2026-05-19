package com.demo.taskmanager.serviceimpl;

import com.demo.taskmanager.dto.UserDto;
import com.demo.taskmanager.entity.User;
import com.demo.taskmanager.exception.ResourceNotFoundException;
import com.demo.taskmanager.repository.UserRepository;
import com.demo.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto.Response createUser(UserDto.Request request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(
            passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ?
            request.getRole() : User.Role.USER);
        user.setCreatedAt(LocalDateTime.now());
        return mapToResponse(userRepository.save(user));
    }

    @Override
    public UserDto.Response getUserById(Long id) {
        return mapToResponse(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "User not found with id: " + id)));
    }

    @Override
    public List<UserDto.Response> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto.Response> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(mapToResponse(user));
        }
        return responses;
    }

    @Override
    public UserDto.Response updateUser(Long id,
            UserDto.Request request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "User not found with id: " + id));
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        if (request.getPassword() != null
                && !request.getPassword().isBlank()) {
            user.setPassword(
                passwordEncoder.encode(request.getPassword()));
        }
        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }
        return mapToResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                    "User not found with id: " + id));
        userRepository.delete(user);
    }

    private UserDto.Response mapToResponse(User user) {
        UserDto.Response response = new UserDto.Response();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}