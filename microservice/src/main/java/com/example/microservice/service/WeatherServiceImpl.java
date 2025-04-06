package com.example.microservice.service;

import com.example.microservice.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WebClient.Builder webClientBuilder;
    
    @Value("${openweather.api.key}")
    private String apiKey;
    
    @Value("${openweather.api.url}")
    private String apiUrl;
    
    @Override
    public Mono<WeatherResponse> getWeatherByCity(String city) {
        return webClientBuilder.build()
                .get()
                .uri(apiUrl + "/weather?q={city}&appid={apiKey}&units=metric", city, apiKey)
                .retrieve()
                .bodyToMono(WeatherResponse.class);
    }
    
    @Override
    public Mono<WeatherResponse> getWeatherByCoordinates(double lat, double lon) {
        return webClientBuilder.build()
                .get()
                .uri(apiUrl + "/weather?lat={lat}&lon={lon}&appid={apiKey}&units=metric", lat, lon, apiKey)
                .retrieve()
                .bodyToMono(WeatherResponse.class);
    }
} 