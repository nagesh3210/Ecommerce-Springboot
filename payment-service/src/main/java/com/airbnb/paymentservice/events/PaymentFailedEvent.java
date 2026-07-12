package com.airbnb.paymentservice.events;

public record PaymentFailedEvent(  Long orderId,

                                   Long productId,

                                   Integer quantity,

                                   String reason) {
}
