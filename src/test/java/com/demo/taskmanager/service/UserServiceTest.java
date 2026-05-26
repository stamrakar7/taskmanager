package com.demo.taskmanager.service;

import com.demo.taskmanager.dto.UserDto;
import com.demo.taskmanager.entity.User;
import com.demo.taskmanager.exception.ResourceNotFoundException;
import com.demo.taskmanager.repository.UserRepository;
import com.demo.taskmanager.serviceimpl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//MOCKITO — No DB, pure logic testing!
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto.Request userRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("Sophia");
        user.setEmail("sophia@test.com");
        user.setPassword("encodedPassword");
        user.setRole(User.Role.ADMIN);

        userRequest = new UserDto.Request();
        userRequest.setName("Sophia");
        userRequest.setEmail("sophia@test.com");
        userRequest.setPassword("password123");
        userRequest.setRole(User.Role.ADMIN);
    }

    @Test
    void createUser_ShouldReturnCreatedUser() {
        when(passwordEncoder.encode(anyString()))
            .thenReturn("encodedPassword");
        when(userRepository.save(any(User.class)))
            .thenReturn(user);

        UserDto.Response response =
            userService.createUser(userRequest);

        assertNotNull(response);
        assertEquals("Sophia", response.getName());
        assertEquals("sophia@test.com", response.getEmail());
        assertEquals(User.Role.ADMIN, response.getRole());
        System.out.println(
            "MOCKITO: User created: " + response.getName());
    }

    @Test
    void getUserById_ShouldReturnUser() {
        when(userRepository.findById(1L))
            .thenReturn(Optional.of(user));

        UserDto.Response response =
            userService.getUserById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Sophia", response.getName());
        System.out.println(
            "MOCKITO: User found: " + response.getName());
    }

    @Test
    void getUserById_ShouldThrow_WhenNotFound() {
        when(userRepository.findById(99L))
            .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
            () -> userService.getUserById(99L));
        System.out.println(
            "MOCKITO: Exception for missing user!");
    }

    @Test
    void getAllUsers_ShouldReturnAllUsers() {
        User user2 = new User();
        user2.setId(2L);
        user2.setName("Test User");
        user2.setEmail("test@test.com");
        user2.setRole(User.Role.USER);

        when(userRepository.findAll())
            .thenReturn(Arrays.asList(user, user2));

        List<UserDto.Response> responses =
            userService.getAllUsers();

        assertEquals(2, responses.size());
        System.out.println(
            "MOCKITO: All users: " + responses.size());
    }

    @Test
    void deleteUser_ShouldDeleteSuccessfully() {
        when(userRepository.findById(1L))
            .thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(user);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(user);
        System.out.println("MOCKITO: User deleted!");
    }
}