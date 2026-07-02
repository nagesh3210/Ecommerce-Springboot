package com.airbnb.orderservice.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderRequest(
        @NotNull
        Long customerId,

        @NotNull
        Long productId,

        @NotNull
        @Positive
        Integer quantity,

        @NotNull
        @Positive
        BigDecimal amount
)
{

}
