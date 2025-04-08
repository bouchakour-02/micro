package com.example.microservice.controller;

import com.example.microservice.model.InsurancePreference;
import com.example.microservice.model.InsuranceRecommendation;
import com.example.microservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class InsurancePreferenceController {

    private final RecommendationService recommendationService;
    
    @PostMapping
    public ResponseEntity<InsurancePreference> submitPreferences(@RequestBody InsurancePreference preference) {
        InsurancePreference savedPreference = recommendationService.savePreference(preference);
        return ResponseEntity.ok(savedPreference);
    }
    
    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<InsuranceRecommendation>> getRecommendations(@PathVariable Long id) {
        List<InsuranceRecommendation> recommendations = recommendationService.getRecommendationsByPreferenceId(id);
        return ResponseEntity.ok(recommendations);
    }
    
    @GetMapping("/user/{email}/recommendations")
    public ResponseEntity<List<InsuranceRecommendation>> getUserRecommendations(@PathVariable String email) {
        List<InsuranceRecommendation> recommendations = recommendationService.getRecommendationsForUser(email);
        return ResponseEntity.ok(recommendations);
    }
} 