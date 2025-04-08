package com.example.microservice.service;

import com.example.microservice.model.RecommendationRule;
import com.example.microservice.repository.RecommendationRuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final RecommendationRuleRepository ruleRepository;
    
    public List<RecommendationRule> getAllRules() {
        return ruleRepository.findAll();
    }
    
    public List<RecommendationRule> getActiveRules() {
        return ruleRepository.findByIsActiveTrue();
    }
    
    public Optional<RecommendationRule> getRuleById(Long id) {
        return ruleRepository.findById(id);
    }
    
    public RecommendationRule createRule(RecommendationRule rule) {
        rule.setIsActive(true);
        return ruleRepository.save(rule);
    }
    
    public Optional<RecommendationRule> updateRule(Long id, RecommendationRule updatedRule) {
        return ruleRepository.findById(id)
                .map(existingRule -> {
                    existingRule.setRuleName(updatedRule.getRuleName());
                    existingRule.setDescription(updatedRule.getDescription());
                    existingRule.setInsuranceType(updatedRule.getInsuranceType());
                    existingRule.setCriteria(updatedRule.getCriteria());
                    existingRule.setWeight(updatedRule.getWeight());
                    existingRule.setIsActive(updatedRule.getIsActive());
                    return ruleRepository.save(existingRule);
                });
    }
    
    public Optional<RecommendationRule> activateRule(Long id) {
        return ruleRepository.findById(id)
                .map(rule -> {
                    rule.setIsActive(true);
                    return ruleRepository.save(rule);
                });
    }
    
    public Optional<RecommendationRule> deactivateRule(Long id) {
        return ruleRepository.findById(id)
                .map(rule -> {
                    rule.setIsActive(false);
                    return ruleRepository.save(rule);
                });
    }
    
    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }
} 