package com.demo.taskmanager.controller;

import com.demo.taskmanager.dto.AuthResponse;
import com.demo.taskmanager.dto.LoginRequest;
import com.demo.taskmanager.dto.UserDto;
import com.demo.taskmanager.entity.User;
import com.demo.taskmanager.repository.UserRepository;
import com.demo.taskmanager.security.JwtUtil;
import com.demo.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register new user
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody UserDto.Request request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Email already exists!");
        }
        UserDto.Response user = userService.createUser(request);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(user);
    }

    // Login and get JWT token
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request) {
        User user = userRepository
            .findByEmail(request.getEmail())
            .orElse(null);

        if (user == null || !passwordEncoder.matches(
                request.getPassword(), user.getPassword())) {
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Invalid email or password!");
        }

        String token = jwtUtil.generateToken(
            user.getEmail(), 
            user.getRole().name());

        return ResponseEntity.ok(new AuthResponse(
            token,
            user.getEmail(),
            user.getRole().name(),
            user.getName()
        ));
    }
}