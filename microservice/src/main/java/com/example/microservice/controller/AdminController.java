package com.example.microservice.controller;

import com.example.microservice.model.RecommendationRule;
import com.example.microservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin/rules")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<List<RecommendationRule>> getAllRules() {
        return ResponseEntity.ok(adminService.getAllRules());
    }

    @GetMapping("/active")
    public ResponseEntity<List<RecommendationRule>> getActiveRules() {
        return ResponseEntity.ok(adminService.getActiveRules());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationRule> getRuleById(@PathVariable Long id) {
        return adminService.getRuleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecommendationRule> createRule(@RequestBody RecommendationRule rule) {
        return ResponseEntity.ok(adminService.createRule(rule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecommendationRule> updateRule(@PathVariable Long id, @RequestBody RecommendationRule rule) {
        return adminService.updateRule(id, rule)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<RecommendationRule> activateRule(@PathVariable Long id) {
        return adminService.activateRule(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<RecommendationRule> deactivateRule(@PathVariable Long id) {
        return adminService.deactivateRule(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        adminService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
} 