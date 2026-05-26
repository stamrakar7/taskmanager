package com.demo.taskmanager.repository;

import com.demo.taskmanager.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

// Runs ONLY on GitHub Actions (Linux)
// Skipped on Mac automatically
@DisabledOnOs(OS.MAC)
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace =
    AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryContainerTest {

    @Container
    static PostgreSQLContainer<?> postgres =
        new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("taskmanager_test")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(
            DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
            postgres::getJdbcUrl);
        registry.add("spring.datasource.username",
            postgres::getUsername);
        registry.add("spring.datasource.password",
            postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void saveUser_ShouldPersistToPostgreSQL() {
        User user = new User();
        user.setName("Bishwa");
        user.setEmail("bishwa@test.com");
        user.setPassword("encoded");
        user.setRole(User.Role.ADMIN);

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        System.out.println(
            "TESTCONTAINERS: User saved!");
    }

    @Test
    void findByEmail_ShouldReturnUser() {
        User user = new User();
        user.setName("Bishwa");
        user.setEmail("bishwa@test.com");
        user.setPassword("encoded");
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);

        Optional<User> found =
            userRepository.findByEmail("bishwa@test.com");

        assertTrue(found.isPresent());
        System.out.println(
            "TESTCONTAINERS: Find by email works!");
    }

    @Test
    void existsByEmail_ShouldReturnTrue() {
        User user = new User();
        user.setName("Bishwa");
        user.setEmail("bishwa@test.com");
        user.setPassword("encoded");
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);

        assertTrue(
            userRepository.existsByEmail("bishwa@test.com"));
        System.out.println(
            "TESTCONTAINERS: Email exists works!");
    }
}