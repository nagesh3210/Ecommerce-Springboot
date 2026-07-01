package com.airbnb.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;



public record ProductRequest(

        @NotBlank
        String name,

        String description,

        @Positive
        BigDecimal price,

        @Positive
        Integer stockQuantity
) {
}
