package com.demo.taskmanager.controller;

import com.demo.taskmanager.dto.TaskDto;
import com.demo.taskmanager.entity.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @WithMockUser(roles = "USER")
    void createTask_ShouldReturn201() throws Exception {
        TaskDto.Request request = new TaskDto.Request();
        request.setTitle("Test Task");
        request.setStatus(Task.Status.TODO);
        request.setPriority(Task.Priority.HIGH);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer test.token.here")
                .content(objectMapper
                    .writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title")
                    .value("Test Task"));

        System.out.println("Task created!");
    }

    @Test
    void getAllTasks_ShouldReturn401_WithoutToken()
            throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isUnauthorized());

        System.out.println("No token returns 401!");
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllTasks_ShouldReturn200_WhenAuthenticated()
            throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());

        System.out.println("Tasks returned!");
    }

    @Test
    @WithMockUser(roles = "USER")
    void getAllUsers_ShouldReturn403_ForUserRole()
            throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());

        System.out.println("USER blocked from /api/users!");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllUsers_ShouldReturn200_ForAdminRole()
            throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());

        System.out.println("ADMIN can access users!");
    }
}