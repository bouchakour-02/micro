package com.example.microservice.controller;

import com.example.microservice.model.ForecastResponse;
import com.example.microservice.model.WeatherResponse;
import com.example.microservice.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/weather")
@RequiredArgsConstructor
@Tag(name = "Weather Controller", description = "Weather data endpoints")
public class WeatherController {

    private final WeatherService weatherService;
    
    @GetMapping("/city/{city}")
    @Operation(summary = "Get weather by city", description = "Retrieves current weather data for a specified city")
    public Mono<ResponseEntity<WeatherResponse>> getWeatherByCity(@PathVariable String city) {
        return weatherService.getWeatherByCity(city)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/coordinates")
    @Operation(summary = "Get weather by coordinates", description = "Retrieves current weather data for specified latitude and longitude")
    public Mono<ResponseEntity<WeatherResponse>> getWeatherByCoordinates(
            @RequestParam double lat,
            @RequestParam double lon) {
        return weatherService.getWeatherByCoordinates(lat, lon)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/forecast/{city}")
    @Operation(summary = "Get 5-day weather forecast", description = "Retrieves a 5-day weather forecast for a specified city")
    public Mono<ResponseEntity<ForecastResponse>> getFiveDayForecast(@PathVariable String city) {
        return weatherService.getFiveDayForecast(city)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
} 