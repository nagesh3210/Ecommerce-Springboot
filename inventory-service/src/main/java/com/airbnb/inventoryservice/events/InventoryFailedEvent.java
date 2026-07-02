package com.airbnb.inventoryservice.events;

public record InventoryFailedEvent(

        Long orderId,

        Long productId,

        String reason

) {
}