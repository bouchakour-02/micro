package com.example.microservice.discovery;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEurekaClient
public class EurekaClientConfig {
    // Configuration is handled through application.properties
} 