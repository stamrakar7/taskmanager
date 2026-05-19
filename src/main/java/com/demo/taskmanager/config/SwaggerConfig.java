package com.demo.taskmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI taskManagerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Manager API")
                        .description("Interview Demo - Task Manager REST API built with Spring Boot")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Task Manager Demo")
                                .email("demo@taskmanager.com")));
    }
}
