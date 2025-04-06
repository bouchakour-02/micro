package com.example.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductWeatherInfo {
    private Product product;
    private String cityName;
    private Double temperature;
    private String weatherDescription;
    private String weatherIcon;
} 