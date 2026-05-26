package com.demo.taskmanager.repository;

import com.demo.taskmanager.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

// POSTGRESQL — Uses local PostgreSQL!
@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:postgresql://localhost:5432/taskmanager_db",
    "spring.datasource.username=postgres",
    "spring.datasource.password=postgres",
    "spring.datasource.driver-class-name=org.postgresql.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect"
})
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void saveUser_ShouldPersistToPostgreSQL() {
        User user = new User();
        user.setName("Sophia");
        user.setEmail("sophia@test.com");
        user.setPassword("encoded");
        user.setRole(User.Role.ADMIN);

        User saved = userRepository.save(user);

        assertNotNull(saved.getId());
        assertEquals("Sophia", saved.getName());
        System.out.println(
            "POSTGRESQL: User saved: " + saved.getId());
    }

    @Test
    void findByEmail_ShouldReturnUser() {
        User user = new User();
        user.setName("Sophia");
        user.setEmail("sophia@test.com");
        user.setPassword("encoded");
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);

        Optional<User> found =
            userRepository.findByEmail("sophia@test.com");

        assertTrue(found.isPresent());
        assertEquals("Sophia", found.get().getName());
        System.out.println(
            "POSTGRESQL: Find by email works!");
    }

    @Test
    void existsByEmail_ShouldReturnTrue_WhenExists() {
        User user = new User();
        user.setName("Sophia");
        user.setEmail("sophia@test.com");
        user.setPassword("encoded");
        user.setRole(User.Role.ADMIN);
        userRepository.save(user);

        boolean exists =
            userRepository.existsByEmail("sophia@test.com");

        assertTrue(exists);
        System.out.println(
            "POSTGRESQL: Email exists works!");
    }

    @Test
    void existsByEmail_ShouldReturnFalse_WhenNotExists() {
        boolean exists =
            userRepository.existsByEmail("nobody@test.com");

        assertFalse(exists);
        System.out.println(
            "POSTGRESQL: Email not found correctly!");
    }
}