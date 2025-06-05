package com.microservices.product.dto;

import java.math.BigDecimal;

public record ProductRequest(String id, String name, String description, BigDecimal price) {
//    public ProductRequest {
//        if (id == null || id.isBlank()) {
//            throw new IllegalArgumentException("Id cannot be null or blank");
//        }
//        if (name == null || name.isBlank()) {
//            throw new IllegalArgumentException("Name cannot be null or blank");
//        }
//        if (description == null || description.isBlank()) {
//            throw new IllegalArgumentException("Description cannot be null or blank");
//        }
//        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new IllegalArgumentException("Price must be greater than zero");
//        }
    }

