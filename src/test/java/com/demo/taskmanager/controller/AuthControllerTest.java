package com.demo.taskmanager.controller;

import com.demo.taskmanager.dto.LoginRequest;
import com.demo.taskmanager.dto.UserDto;
import com.demo.taskmanager.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// H2 — Full HTTP test with in-memory DB!
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode =
    DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void register_ShouldReturn201() throws Exception {
        UserDto.Request request = new UserDto.Request();
        request.setName("new user");
        request.setEmail("newuser@test.com");
        request.setPassword("password123");
        request.setRole(User.Role.ADMIN);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                    .writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email")
                    .value("newuser@test.com"));

        System.out.println("H2: Register returns 201!");
    }

    @Test
    void login_ShouldReturnToken() throws Exception {
        // First register
        UserDto.Request registerReq = new UserDto.Request();
        registerReq.setName("Sophia");
        registerReq.setEmail("login@test.com");
        registerReq.setPassword("password123");
        registerReq.setRole(User.Role.ADMIN);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                    .writeValueAsString(registerReq)))
                .andExpect(status().isCreated());

        // Then login
        LoginRequest loginReq = new LoginRequest();
        loginReq.setEmail("login@test.com");
        loginReq.setPassword("password123");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                    .writeValueAsString(loginReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.role")
                    .value("ADMIN"));

        System.out.println("H2: Login returns token!");
    }

    @Test
    void login_ShouldReturn401_WrongCredentials()
            throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("wrong@test.com");
        request.setPassword("wrongpassword");

        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                    .writeValueAsString(request)))
                .andExpect(status().isUnauthorized());

        System.out.println(
            "H2: Wrong credentials returns 401!");
    }

    @Test
    void register_ShouldReturn409_WhenEmailExists()
            throws Exception {
        // Register first time
        UserDto.Request request = new UserDto.Request();
        request.setName("Sophia");
        request.setEmail("duplicate@test.com");
        request.setPassword("password123");
        request.setRole(User.Role.USER);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                    .writeValueAsString(request)))
                .andExpect(status().isCreated());

        // Register second time same email
        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                    .writeValueAsString(request)))
                .andExpect(status().isConflict());

        System.out.println(
            "H2: Duplicate email returns 409!");
    }
}