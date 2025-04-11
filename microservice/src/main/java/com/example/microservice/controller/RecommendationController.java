package com.example.microservice.controller;

import com.example.microservice.model.InsuranceRecommendation;
import com.example.microservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
@CrossOrigin("*")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    public ResponseEntity<List<InsuranceRecommendation>> getAllRecommendations() {
        return ResponseEntity.ok(recommendationService.getAllRecommendations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceRecommendation> getRecommendationById(@PathVariable Long id) {
        return ResponseEntity.ok(recommendationService.getRecommendationById(id));
    }

    @GetMapping("/preference/{preferenceId}")
    public ResponseEntity<List<InsuranceRecommendation>> getRecommendationsByPreferenceId(
            @PathVariable Long preferenceId) {
        return ResponseEntity.ok(recommendationService.getRecommendationsByPreferenceId(preferenceId));
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<InsuranceRecommendation>> getRecommendationsForUser(
            @PathVariable String email) {
        return ResponseEntity.ok(recommendationService.getRecommendationsForUser(email));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecommendation(@PathVariable Long id) {
        recommendationService.deleteRecommendation(id);
        return ResponseEntity.noContent().build();
    }
} 