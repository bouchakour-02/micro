package com.example.microservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/api/v1/monitor")
@Tag(name = "Monitoring Controller", description = "System monitoring endpoints")
public class MonitoringController {

    @Value("${spring.application.name}")
    private String applicationName;
    
    @Value("${server.port}")
    private String serverPort;
    
    @GetMapping("/health")
    @Operation(summary = "Get system health", description = "Retrieves the current health status of the system")
    public ResponseEntity<Map<String, Object>> getHealth() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now().toString());
        health.put("service", applicationName);
        health.put("port", serverPort);
        
        return ResponseEntity.ok(health);
    }
    
    @GetMapping("/info")
    @Operation(summary = "Get system info", description = "Retrieves system information including memory and JVM details")
    public ResponseEntity<Map<String, Object>> getSystemInfo() {
        Map<String, Object> info = new HashMap<>();
        
        // Basic application info
        info.put("application", applicationName);
        info.put("timestamp", LocalDateTime.now().toString());
        
        // JVM info
        Properties properties = System.getProperties();
        Map<String, String> jvmInfo = new HashMap<>();
        jvmInfo.put("version", properties.getProperty("java.version"));
        jvmInfo.put("vendor", properties.getProperty("java.vendor"));
        jvmInfo.put("vm_name", properties.getProperty("java.vm.name"));
        
        // Memory info
        Runtime runtime = Runtime.getRuntime();
        Map<String, String> memoryInfo = new HashMap<>();
        memoryInfo.put("total_memory", (runtime.totalMemory() / (1024 * 1024)) + " MB");
        memoryInfo.put("free_memory", (runtime.freeMemory() / (1024 * 1024)) + " MB");
        memoryInfo.put("used_memory", ((runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)) + " MB");
        memoryInfo.put("max_memory", (runtime.maxMemory() / (1024 * 1024)) + " MB");
        
        info.put("jvm", jvmInfo);
        info.put("memory", memoryInfo);
        
        return ResponseEntity.ok(info);
    }
} 