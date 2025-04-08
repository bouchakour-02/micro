package com.example.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "insurance_preferences")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsurancePreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userEmail;
    
    private String userName;
    
    private Integer age;
    
    private String profession;
    
    private Double annualIncome;
    
    private Boolean hasVehicle;
    
    private String vehicleType;
    
    private Boolean hasProperty;
    
    private String propertyType;
    
    private Boolean hasHealthIssues;
    
    private String healthIssuesDescription;
    
    private Boolean hasFamilyDependents;
    
    private Integer numberOfDependents;
    
    private Boolean interestedInLifeInsurance;
    
    private Boolean interestedInHealthInsurance;
    
    private Boolean interestedInVehicleInsurance;
    
    private Boolean interestedInPropertyInsurance;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }
} 