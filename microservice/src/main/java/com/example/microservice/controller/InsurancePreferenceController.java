package com.example.microservice.controller;

import com.example.microservice.model.InsurancePreference;
import com.example.microservice.model.InsuranceRecommendation;
import com.example.microservice.service.RecommendationService;
import com.example.microservice.service.PdfExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/preferences")
@RequiredArgsConstructor
public class InsurancePreferenceController {

    private final RecommendationService recommendationService;
    private final PdfExportService pdfExportService;
    
    @PostMapping
    public ResponseEntity<InsurancePreference> submitPreferences(@RequestBody InsurancePreference preference) {
        InsurancePreference savedPreference = recommendationService.savePreference(preference);
        return ResponseEntity.ok(savedPreference);
    }
    
    @GetMapping
    public ResponseEntity<List<InsurancePreference>> getAllPreferences() {
        List<InsurancePreference> preferences = recommendationService.getAllPreferences();
        return ResponseEntity.ok(preferences);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<InsurancePreference> getPreferenceById(@PathVariable Long id) {
        InsurancePreference preference = recommendationService.getPreferenceById(id);
        return ResponseEntity.ok(preference);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<InsurancePreference> updatePreference(
            @PathVariable Long id, 
            @RequestBody InsurancePreference preference) {
        InsurancePreference updatedPreference = recommendationService.updatePreference(id, preference);
        return ResponseEntity.ok(updatedPreference);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreference(@PathVariable Long id) {
        recommendationService.deletePreference(id);
        return ResponseEntity.noContent().build();
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
    
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> exportPreferencesToPdf() {
        List<InsurancePreference> preferences = recommendationService.getAllPreferences();
        byte[] pdfBytes = pdfExportService.generatePreferencesPdf(preferences);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "insurance-preferences.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfBytes.length)
                .body(pdfBytes);
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<InsurancePreference>> getUserPreferences(@PathVariable String email) {
        List<InsurancePreference> preferences = recommendationService.getPreferencesByUserEmail(email);
        return ResponseEntity.ok(preferences);
    }

    @GetMapping("/user/{email}/latest")
    public ResponseEntity<InsurancePreference> getLatestUserPreference(@PathVariable String email) {
        return recommendationService.getLatestPreferenceByUserEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 