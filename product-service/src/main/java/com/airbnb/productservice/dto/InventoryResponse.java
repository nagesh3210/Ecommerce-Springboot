package com.airbnb.productservice.dto;

public record InventoryResponse(
        Long productId,
                                 Integer availableQuantity,
                                 boolean inStock)
{

}
