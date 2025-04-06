package com.example.microservice.controller;

import com.example.microservice.model.ProductWeatherInfo;
import com.example.microservice.service.ProductWeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/product-weather")
@RequiredArgsConstructor
@Tag(name = "Product Weather Controller", description = "Integrated product and weather data endpoints")
public class ProductWeatherController {

    private final ProductWeatherService productWeatherService;
    
    @GetMapping("/{productId}")
    @Operation(summary = "Get product with weather", description = "Retrieves product details along with current weather for a city")
    public Mono<ResponseEntity<ProductWeatherInfo>> getProductWithWeather(
            @PathVariable Long productId,
            @RequestParam String city) {
        return productWeatherService.getProductWithWeather(productId, city)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
} 