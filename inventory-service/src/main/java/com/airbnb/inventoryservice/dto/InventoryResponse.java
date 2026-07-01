package com.airbnb.inventoryservice.dto;

public record InventoryResponse (Long productId, Integer availableQuantity, boolean inStock)
{
}
