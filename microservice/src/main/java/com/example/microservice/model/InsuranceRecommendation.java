package com.example.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_recommendations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "preference_id")
    private InsurancePreference preference;
    
    private String insuranceType;
    
    private String insuranceName;
    
    private String description;
    
    private Double monthlyCost;
    
    private Double annualCost;
    
    private String coverageDetails;
    
    private Integer recommendationScore;
    
    private String recommendationReason;
    
    private Boolean isPrimaryRecommendation;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }
} 