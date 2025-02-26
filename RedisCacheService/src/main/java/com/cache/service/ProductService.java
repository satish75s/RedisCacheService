package com.cache.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cache.model.Product;

@Service
public class ProductService {

    private final Map<Long, Product> productDatabase = new HashMap<>();

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(Long id) {
        // Simulate a slow database call
        simulateSlowService();
        return productDatabase.get(id);
    }

    @CachePut(value = "products", key = "#product.id")
    public Product addProduct(Product product) {
        productDatabase.put(product.getId(), product);
        return product;
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        productDatabase.remove(id);
    }

    private void simulateSlowService() {
        try {
            Thread.sleep(3000); // Simulate a 3-second delay
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}