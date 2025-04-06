package com.example.microservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Value("${app.api.version}")
    private String apiVersion;
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Microservice API")
                        .version(apiVersion)
                        .description("API for managing products in a distributed web application")
                        .contact(new Contact()
                                .name("Student")
                                .email("student@example.com")));
    }
} 