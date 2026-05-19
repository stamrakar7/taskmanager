package com.demo.taskmanager.controller;

import com.demo.taskmanager.dto.UserDto;
import com.demo.taskmanager.entity.User;
import com.demo.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserDto.Response> createUser(
            @RequestBody UserDto.Request request) {
    	
    	UserDto.Response response = userService.createUser(request);

        System.out.println("User created successfully!");
        System.out.println("Returned ID: " + response.getId());
        System.out.println("status:"+ HttpStatus.CREATED);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<UserDto.Response>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getUserById(
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto.Response> updateUser(
            @PathVariable Long id,
            @RequestBody UserDto.Request request) {
        return ResponseEntity.ok(
            userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }
}