package com.airbnb.inventoryservice.events;

public record PaymentFailedEvent(        Long orderId,

                                         Long productId,

                                         Integer quantity,

                                         String reason
) {
}
