package com.demo.taskmanager.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.security.Key;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

// ✅ MOCKITO — No Spring, No DB, Pure Java!
class JwtUtilTest {

    private JwtUtil jwtUtil;
    private String testSecret = 
        "authservice-secret-key-for-jwt-2024-production-use-only";

    // Helper to generate token for testing
    // since taskmanager only validates!
    private String generateTestToken(String email, String role) {
        Key key = Keys.hmacShaKeyFor(testSecret.getBytes());
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(
                    System.currentTimeMillis() + 86400000))
                .signWith(key)
                .compact();
    }

    @BeforeEach
    void setUp() throws Exception {
        jwtUtil = new JwtUtil();
        // Inject secret via reflection
        java.lang.reflect.Field field = 
            JwtUtil.class.getDeclaredField("secret");
        field.setAccessible(true);
        field.set(jwtUtil, testSecret);
    }

    @Test
    void extractEmail_ShouldReturnCorrectEmail() {
        String token = generateTestToken(
            "bishwa@test.com", "ADMIN");
        assertEquals("bishwa@test.com",
            jwtUtil.extractEmail(token));
        System.out.println("✅ MOCKITO: Email extracted!");
    }

    @Test
    void extractRole_ShouldReturnCorrectRole() {
        String token = generateTestToken(
            "bishwa@test.com", "ADMIN");
        assertEquals("ADMIN",
            jwtUtil.extractRole(token));
        System.out.println("✅ MOCKITO: Role extracted!");
    }

    @Test
    void validateToken_ValidToken_ShouldReturnTrue() {
        String token = generateTestToken(
            "bishwa@test.com", "ADMIN");
        assertTrue(jwtUtil.validateToken(token));
        System.out.println("✅ MOCKITO: Valid token!");
    }

    @Test
    void validateToken_FakeToken_ShouldReturnFalse() {
        assertFalse(jwtUtil.validateToken("fake.token"));
        System.out.println(
            "✅ MOCKITO: Fake token rejected!");
    }

    @Test
    void validateToken_TamperedToken_ShouldReturnFalse() {
        String token = generateTestToken(
            "bishwa@test.com", "ADMIN");
        assertFalse(
            jwtUtil.validateToken(token + "tampered"));
        System.out.println(
            "✅ MOCKITO: Tampered token rejected!");
    }
}