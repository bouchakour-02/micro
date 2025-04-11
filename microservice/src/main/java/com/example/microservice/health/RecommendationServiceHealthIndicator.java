package com.example.microservice.health;

import com.example.microservice.repository.RecommendationRuleRepository;
import com.example.microservice.repository.InsurancePreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class RecommendationServiceHealthIndicator extends AbstractHealthIndicator {

    private final RecommendationRuleRepository ruleRepository;
    private final InsurancePreferenceRepository preferenceRepository;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        try {
            Map<String, Object> details = new HashMap<>();
            
            // Check active rules
            long activeRules = ruleRepository.findByIsActiveTrue().size();
            details.put("activeRules", activeRules);
            
            // Check total preferences
            long totalPreferences = preferenceRepository.count();
            details.put("totalPreferences", totalPreferences);
            
            // Add system metrics
            details.put("timestamp", LocalDateTime.now());
            details.put("availableProcessors", Runtime.getRuntime().availableProcessors());
            details.put("freeMemory", Runtime.getRuntime().freeMemory());
            details.put("totalMemory", Runtime.getRuntime().totalMemory());

            if (activeRules > 0) {
                builder.up().withDetails(details);
            } else {
                builder.down().withDetails(details).withDetail("error", "No active rules found");
            }
        } catch (Exception e) {
            builder.down().withException(e);
        }
    }
} 