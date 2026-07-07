package com.airbnb.orderservice.events;

public record InventoryReservedEvent(

        Long orderId,

        Long productId,

        Integer quantity

) {
}
