package com.airbnb.inventoryservice.events;

public record InventoryReservedEvent (Long orderId, Long productId, Integer quantity){
}
