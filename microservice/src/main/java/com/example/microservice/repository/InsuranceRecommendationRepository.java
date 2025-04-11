package com.example.microservice.repository;

import com.example.microservice.model.InsuranceRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InsuranceRecommendationRepository extends JpaRepository<InsuranceRecommendation, Long> {
    
    List<InsuranceRecommendation> findByPreferenceId(Long preferenceId);
    
    List<InsuranceRecommendation> findByPreferenceIdOrderByRecommendationScoreDesc(Long preferenceId);
    
    List<InsuranceRecommendation> findByPreferenceUserEmail(String userEmail);
    
    void deleteByPreferenceId(Long preferenceId);
} 