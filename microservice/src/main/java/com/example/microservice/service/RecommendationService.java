package com.example.microservice.service;

import com.example.microservice.model.InsurancePreference;
import com.example.microservice.model.InsuranceRecommendation;
import com.example.microservice.model.RecommendationRule;
import com.example.microservice.repository.InsurancePreferenceRepository;
import com.example.microservice.repository.InsuranceRecommendationRepository;
import com.example.microservice.repository.RecommendationRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final InsurancePreferenceRepository preferenceRepository;
    private final InsuranceRecommendationRepository recommendationRepository;
    private final RecommendationRuleRepository ruleRepository;

    public InsurancePreference savePreference(InsurancePreference preference) {
        InsurancePreference savedPreference = preferenceRepository.save(preference);
        generateRecommendations(savedPreference);
        return savedPreference;
    }

    public List<InsuranceRecommendation> getRecommendationsForUser(String userEmail) {
        return preferenceRepository.findTopByUserEmailOrderByCreatedDateDesc(userEmail)
                .map(preference -> recommendationRepository.findByPreferenceIdOrderByRecommendationScoreDesc(preference.getId()))
                .orElse(new ArrayList<>());
    }

    public List<InsuranceRecommendation> getRecommendationsByPreferenceId(Long preferenceId) {
        return recommendationRepository.findByPreferenceIdOrderByRecommendationScoreDesc(preferenceId);
    }

    private void generateRecommendations(InsurancePreference preference) {
        // Get all active rules
        List<RecommendationRule> activeRules = ruleRepository.findByIsActiveTrue();
        
        // Group rules by insurance type
        Map<String, List<RecommendationRule>> rulesByType = activeRules.stream()
                .collect(Collectors.groupingBy(RecommendationRule::getInsuranceType));
        
        // Process each insurance type
        List<InsuranceRecommendation> recommendations = new ArrayList<>();
        
        // Life insurance recommendations
        if (preference.getInterestedInLifeInsurance() != null && preference.getInterestedInLifeInsurance()) {
            recommendations.add(createLifeInsuranceRecommendation(preference, "Basic Life Insurance"));
            if (preference.getHasFamilyDependents() != null && preference.getHasFamilyDependents()) {
                recommendations.add(createLifeInsuranceRecommendation(preference, "Family Protection Plan"));
            }
        }
        
        // Health insurance recommendations
        if (preference.getInterestedInHealthInsurance() != null && preference.getInterestedInHealthInsurance()) {
            recommendations.add(createHealthInsuranceRecommendation(preference, "Standard Health Coverage"));
            if (preference.getHasHealthIssues() != null && preference.getHasHealthIssues()) {
                recommendations.add(createHealthInsuranceRecommendation(preference, "Comprehensive Health Plan"));
            }
        }
        
        // Vehicle insurance recommendations
        if (preference.getInterestedInVehicleInsurance() != null && preference.getInterestedInVehicleInsurance() && 
            preference.getHasVehicle() != null && preference.getHasVehicle()) {
            recommendations.add(createVehicleInsuranceRecommendation(preference, "Standard Auto Insurance"));
            if ("Car".equalsIgnoreCase(preference.getVehicleType())) {
                recommendations.add(createVehicleInsuranceRecommendation(preference, "Premium Car Coverage"));
            }
        }
        
        // Property insurance recommendations
        if (preference.getInterestedInPropertyInsurance() != null && preference.getInterestedInPropertyInsurance() && 
            preference.getHasProperty() != null && preference.getHasProperty()) {
            recommendations.add(createPropertyInsuranceRecommendation(preference, "Home Insurance Basic"));
            if ("House".equalsIgnoreCase(preference.getPropertyType())) {
                recommendations.add(createPropertyInsuranceRecommendation(preference, "Complete Home Protection"));
            }
        }
        
        // Sort by score and mark the top recommendation as primary
        recommendations.sort((r1, r2) -> r2.getRecommendationScore().compareTo(r1.getRecommendationScore()));
        if (!recommendations.isEmpty()) {
            recommendations.get(0).setIsPrimaryRecommendation(true);
        }
        
        // Save all recommendations
        recommendationRepository.saveAll(recommendations);
    }
    
    private InsuranceRecommendation createLifeInsuranceRecommendation(InsurancePreference preference, String name) {
        InsuranceRecommendation recommendation = new InsuranceRecommendation();
        recommendation.setPreference(preference);
        recommendation.setInsuranceType("LIFE");
        recommendation.setInsuranceName(name);
        
        if ("Basic Life Insurance".equals(name)) {
            recommendation.setDescription("Basic life insurance coverage to protect your loved ones");
            recommendation.setMonthlyCost(calculateMonthlyCost(preference.getAge(), 20.0, 0.5));
            recommendation.setAnnualCost(recommendation.getMonthlyCost() * 12);
            recommendation.setCoverageDetails("Covers up to 5 times your annual income");
            recommendation.setRecommendationScore(calculateLifeInsuranceScore(preference, 80));
            recommendation.setRecommendationReason("Provides essential protection for your dependents");
        } else {
            recommendation.setDescription("Comprehensive life insurance with additional family benefits");
            recommendation.setMonthlyCost(calculateMonthlyCost(preference.getAge(), 35.0, 0.7));
            recommendation.setAnnualCost(recommendation.getMonthlyCost() * 12);
            recommendation.setCoverageDetails("Covers up to 10 times your annual income with additional benefits for each dependent");
            recommendation.setRecommendationScore(calculateLifeInsuranceScore(preference, 90));
            recommendation.setRecommendationReason("Ideal for someone with family dependents to ensure their financial security");
        }
        
        recommendation.setIsPrimaryRecommendation(false);
        return recommendation;
    }
    
    private InsuranceRecommendation createHealthInsuranceRecommendation(InsurancePreference preference, String name) {
        InsuranceRecommendation recommendation = new InsuranceRecommendation();
        recommendation.setPreference(preference);
        recommendation.setInsuranceType("HEALTH");
        recommendation.setInsuranceName(name);
        
        if ("Standard Health Coverage".equals(name)) {
            recommendation.setDescription("Essential health coverage for routine medical needs");
            recommendation.setMonthlyCost(calculateMonthlyCost(preference.getAge(), 40.0, 0.4));
            recommendation.setAnnualCost(recommendation.getMonthlyCost() * 12);
            recommendation.setCoverageDetails("Covers doctor visits, emergency care, and basic hospitalization");
            recommendation.setRecommendationScore(calculateHealthInsuranceScore(preference, 75));
            recommendation.setRecommendationReason("Provides necessary medical coverage at an affordable cost");
        } else {
            recommendation.setDescription("Comprehensive health plan for extensive medical needs");
            recommendation.setMonthlyCost(calculateMonthlyCost(preference.getAge(), 70.0, 0.6));
            recommendation.setAnnualCost(recommendation.getMonthlyCost() * 12);
            recommendation.setCoverageDetails("Comprehensive coverage including specialist care, chronic conditions, and premium facilities");
            recommendation.setRecommendationScore(calculateHealthInsuranceScore(preference, 95));
            recommendation.setRecommendationReason("Recommended due to your specific health needs for optimal coverage");
        }
        
        recommendation.setIsPrimaryRecommendation(false);
        return recommendation;
    }
    
    private InsuranceRecommendation createVehicleInsuranceRecommendation(InsurancePreference preference, String name) {
        InsuranceRecommendation recommendation = new InsuranceRecommendation();
        recommendation.setPreference(preference);
        recommendation.setInsuranceType("VEHICLE");
        recommendation.setInsuranceName(name);
        
        if ("Standard Auto Insurance".equals(name)) {
            recommendation.setDescription("Basic coverage for your vehicle with liability protection");
            recommendation.setMonthlyCost(50.0);
            recommendation.setAnnualCost(recommendation.getMonthlyCost() * 12);
            recommendation.setCoverageDetails("Includes liability, collision, and comprehensive coverage");
            recommendation.setRecommendationScore(85);
            recommendation.setRecommendationReason("Essential protection for your vehicle and liability coverage");
        } else {
            recommendation.setDescription("Premium coverage with additional benefits for your car");
            recommendation.setMonthlyCost(85.0);
            recommendation.setAnnualCost(recommendation.getMonthlyCost() * 12);
            recommendation.setCoverageDetails("Full coverage with roadside assistance, rental reimbursement, and gap insurance");
            recommendation.setRecommendationScore(92);
            recommendation.setRecommendationReason("Comprehensive protection for your valuable vehicle investment");
        }
        
        recommendation.setIsPrimaryRecommendation(false);
        return recommendation;
    }
    
    private InsuranceRecommendation createPropertyInsuranceRecommendation(InsurancePreference preference, String name) {
        InsuranceRecommendation recommendation = new InsuranceRecommendation();
        recommendation.setPreference(preference);
        recommendation.setInsuranceType("PROPERTY");
        recommendation.setInsuranceName(name);
        
        if ("Home Insurance Basic".equals(name)) {
            recommendation.setDescription("Essential coverage for your home and belongings");
            recommendation.setMonthlyCost(60.0);
            recommendation.setAnnualCost(recommendation.getMonthlyCost() * 12);
            recommendation.setCoverageDetails("Covers structural damage, theft, and basic liability");
            recommendation.setRecommendationScore(80);
            recommendation.setRecommendationReason("Basic protection for your property investment");
        } else {
            recommendation.setDescription("Complete protection for your home with additional benefits");
            recommendation.setMonthlyCost(95.0);
            recommendation.setAnnualCost(recommendation.getMonthlyCost() * 12);
            recommendation.setCoverageDetails("Premium coverage with additional protection for valuables, extended replacement cost, and increased liability");
            recommendation.setRecommendationScore(88);
            recommendation.setRecommendationReason("Comprehensive coverage for complete peace of mind");
        }
        
        recommendation.setIsPrimaryRecommendation(false);
        return recommendation;
    }
    
    private Double calculateMonthlyCost(Integer age, Double baseCost, Double ageFactor) {
        if (age == null) return baseCost;
        return baseCost + (age * ageFactor);
    }
    
    private Integer calculateLifeInsuranceScore(InsurancePreference preference, Integer baseScore) {
        Integer score = baseScore;
        
        if (preference.getHasFamilyDependents() != null && preference.getHasFamilyDependents()) {
            score += 10;
            if (preference.getNumberOfDependents() != null && preference.getNumberOfDependents() > 2) {
                score += 5;
            }
        }
        
        if (preference.getAge() != null) {
            if (preference.getAge() > 40) {
                score += 5;
            }
            if (preference.getAge() > 50) {
                score += 5;
            }
        }
        
        return Math.min(score, 100);
    }
    
    private Integer calculateHealthInsuranceScore(InsurancePreference preference, Integer baseScore) {
        Integer score = baseScore;
        
        if (preference.getHasHealthIssues() != null && preference.getHasHealthIssues()) {
            score += 15;
        }
        
        if (preference.getAge() != null) {
            if (preference.getAge() > 40) {
                score += 5;
            }
            if (preference.getAge() > 60) {
                score += 10;
            }
        }
        
        return Math.min(score, 100);
    }
} 