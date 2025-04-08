package com.example.microservice.repository;

import com.example.microservice.model.RecommendationRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRuleRepository extends JpaRepository<RecommendationRule, Long> {
    
    List<RecommendationRule> findByIsActiveTrue();
    
    List<RecommendationRule> findByInsuranceTypeAndIsActiveTrue(String insuranceType);
    
} 