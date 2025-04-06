package com.example.microservice.service;

import com.example.microservice.model.WeatherResponse;
import reactor.core.publisher.Mono;

public interface WeatherService {
    Mono<WeatherResponse> getWeatherByCity(String city);
    Mono<WeatherResponse> getWeatherByCoordinates(double lat, double lon);
} 