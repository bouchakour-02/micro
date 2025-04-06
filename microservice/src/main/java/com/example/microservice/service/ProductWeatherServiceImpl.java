package com.example.microservice.service;

import com.example.microservice.model.Product;
import com.example.microservice.model.ProductWeatherInfo;
import com.example.microservice.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class ProductWeatherServiceImpl implements ProductWeatherService {

    private final ProductService productService;
    private final WeatherService weatherService;

    @Override
    public Mono<ProductWeatherInfo> getProductWithWeather(Long productId, String city) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        
        return weatherService.getWeatherByCity(city)
                .map(weatherResponse -> mapToProductWeatherInfo(product, weatherResponse));
    }
    
    private ProductWeatherInfo mapToProductWeatherInfo(Product product, WeatherResponse weatherResponse) {
        return ProductWeatherInfo.builder()
                .product(product)
                .cityName(weatherResponse.getName())
                .temperature(weatherResponse.getMain().getTemp())
                .weatherDescription(weatherResponse.getWeather().get(0).getDescription())
                .weatherIcon(weatherResponse.getWeather().get(0).getIcon())
                .build();
    }
} 