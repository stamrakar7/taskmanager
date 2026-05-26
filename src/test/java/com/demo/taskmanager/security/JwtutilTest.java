package com.demo.taskmanager.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// MOCKITO — No Spring, No DB, Pure Java!
class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    @Test
    void generateToken_ShouldReturnToken() {
        String token = jwtUtil.generateToken(
            "sophia@test.com", "ADMIN");
        assertNotNull(token);
        System.out.println("MOCKITO: Token generated!");
    }

    @Test
    void extractEmail_ShouldReturnCorrectEmail() {
        String token = jwtUtil.generateToken(
            "sophia@test.com", "ADMIN");
        assertEquals("sophia@test.com",
            jwtUtil.extractEmail(token));
        System.out.println("MOCKITO: Email extracted!");
    }

    @Test
    void extractRole_ShouldReturnCorrectRole() {
        String token = jwtUtil.generateToken(
            "sophia@test.com", "ADMIN");
        assertEquals("ADMIN",
            jwtUtil.extractRole(token));
        System.out.println("MOCKITO: Role extracted!");
    }

    @Test
    void validateToken_ValidToken_ShouldReturnTrue() {
        String token = jwtUtil.generateToken(
            "sophia@test.com", "ADMIN");
        assertTrue(jwtUtil.validateToken(token));
        System.out.println("MOCKITO: Valid token!");
    }

    @Test
    void validateToken_FakeToken_ShouldReturnFalse() {
        assertFalse(jwtUtil.validateToken("fake.token"));
        System.out.println(
            "MOCKITO: Fake token rejected!");
    }

    @Test
    void validateToken_TamperedToken_ShouldReturnFalse() {
        String token = jwtUtil.generateToken(
            "sophia@test.com", "ADMIN");
        assertFalse(
            jwtUtil.validateToken(token + "tampered"));
        System.out.println(
            "MOCKITO: Tampered token rejected!");
    }
}