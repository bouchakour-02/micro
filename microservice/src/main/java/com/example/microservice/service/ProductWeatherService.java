package com.example.microservice.service;

import com.example.microservice.model.ProductWeatherInfo;
import reactor.core.publisher.Mono;

public interface ProductWeatherService {
    Mono<ProductWeatherInfo> getProductWithWeather(Long productId, String city);
} 