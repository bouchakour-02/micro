package com.example.microservice.repository;

import com.example.microservice.model.InsurancePreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InsurancePreferenceRepository extends JpaRepository<InsurancePreference, Long> {
    
    List<InsurancePreference> findByUserEmail(String userEmail);
    
    Optional<InsurancePreference> findTopByUserEmailOrderByCreatedDateDesc(String userEmail);
    
} 