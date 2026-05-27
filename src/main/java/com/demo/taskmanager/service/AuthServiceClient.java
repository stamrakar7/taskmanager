package com.demo.taskmanager.service;

import com.demo.taskmanager.dto.AuthUserDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AuthServiceClient {

    private final WebClient webClient;

    public AuthServiceClient() {
        this.webClient = WebClient.create(
            "http://localhost:8081");
    }

    // Call auth-service /auth/validate
    // to get user details from token!
    public AuthUserDto validateAndGetUser(String token) {
        try {
            return webClient.get()
                .uri("/auth/validate")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(AuthUserDto.class)
                .block();
        } catch (Exception e) {
            System.out.println(
                "Auth service call failed: "
                    + e.getMessage());
            return null;
        }
    }
}