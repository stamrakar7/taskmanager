package com.demo.taskmanager.controller;

import com.demo.taskmanager.dto.LoginRequest;
import com.demo.taskmanager.dto.TaskDto;
import com.demo.taskmanager.dto.UserDto;
import com.demo.taskmanager.entity.Task;
import com.demo.taskmanager.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// ✅ POSTGRESQL — Full integration with real DB!
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode =
    DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager_db",
    "spring.datasource.username=postgres",
    "spring.datasource.password=postgres",
    "spring.datasource.driver-class-name=org.postgresql.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect"
})
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String adminToken;

    @BeforeEach
    void setUp() throws Exception {
        // Register admin
        UserDto.Request registerReq = new UserDto.Request();
        registerReq.setName("Admin");
        registerReq.setEmail("admin@test.com");
        registerReq.setPassword("password123");
        registerReq.setRole(User.Role.ADMIN);

        mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                    .writeValueAsString(registerReq)));

        // Login and get token
        LoginRequest loginReq = new LoginRequest();
        loginReq.setEmail("admin@test.com");
        loginReq.setPassword("password123");

        MvcResult result = mockMvc.perform(
                post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper
                    .writeValueAsString(loginReq)))
                .andReturn();

        String response = result.getResponse()
            .getContentAsString();
        adminToken = objectMapper.readTree(response)
            .get("token").asText();
    }

    @Test
    void createTask_ShouldReturn201_WithValidToken()
            throws Exception {
        TaskDto.Request request = new TaskDto.Request();
        request.setTitle("PostgreSQL Test Task");
        request.setStatus(Task.Status.TODO);
        request.setPriority(Task.Priority.HIGH);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",
                    "Bearer " + adminToken)
                .content(objectMapper
                    .writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                    .value("PostgreSQL Test Task"));

        System.out.println(
            "POSTGRESQL: Task created with token!");
    }

    @Test
    void getAllTasks_ShouldReturn401_WithoutToken()
            throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isUnauthorized());

        System.out.println(
            "POSTGRESQL: No token returns 401!");
    }

    @Test
    void getAllTasks_ShouldReturn200_WithValidToken()
            throws Exception {
        mockMvc.perform(get("/api/tasks")
                .header("Authorization",
                    "Bearer " + adminToken))
                .andExpect(status().isOk());

        System.out.println(
            "POSTGRESQL: Tasks returned with token!");
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllUsers_ShouldReturn403_ForUserRole()
            throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());

        System.out.println(
            "POSTGRESQL: USER blocked from /api/users!");
    }

    @Test
    void deleteTask_ShouldReturn200_WithValidToken()
            throws Exception {
        // First create a task
        TaskDto.Request request = new TaskDto.Request();
        request.setTitle("Task to delete");
        request.setStatus(Task.Status.TODO);

        MvcResult result = mockMvc.perform(
                post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization",
                    "Bearer " + adminToken)
                .content(objectMapper
                    .writeValueAsString(request)))
                .andReturn();

        // Get task id
        Long taskId = objectMapper
            .readTree(result.getResponse().getContentAsString())
            .get("id").asLong();

        // Then delete it
        mockMvc.perform(delete("/api/tasks/" + taskId)
                .header("Authorization",
                    "Bearer " + adminToken))
                .andExpect(status().isOk());

        System.out.println(
            "POSTGRESQL: Task deleted successfully!");
    }
}