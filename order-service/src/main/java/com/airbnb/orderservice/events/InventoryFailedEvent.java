package com.airbnb.orderservice.events;

public record InventoryFailedEvent (

        Long orderId,

        Long productId,

        String reason

) {
}
