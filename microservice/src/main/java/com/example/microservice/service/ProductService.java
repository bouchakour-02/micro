package com.example.microservice.service;

import com.example.microservice.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> getAllProducts();
    Page<Product> getProductsPaginated(int page, int size, String sortBy);
    Optional<Product> getProductById(Long id);
    List<Product> getProductsByCategory(String category);
    List<Product> searchProductsByName(String name);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product productDetails);
    void deleteProduct(Long id);
} 