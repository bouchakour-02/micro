package com.example.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recommendation_rules")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecommendationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "rule_name")
    private String ruleName;
    
    @Column(name = "rule_description")
    private String description;
    
    @Column(name = "insurance_type")
    private String insuranceType;
    
    // Rule criteria in JSON format
    @Column(name = "rule_condition", columnDefinition = "TEXT")
    private String criteria;
    
    // Weight of this rule in the recommendation algorithm (1-100)
    @Column(name = "rule_score_weight")
    private Integer weight;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @Column(name = "last_modified")
    private LocalDateTime lastModified;
    
    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        lastModified = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        lastModified = LocalDateTime.now();
    }
} 