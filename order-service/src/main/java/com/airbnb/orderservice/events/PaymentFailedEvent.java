package com.airbnb.orderservice.events;

public record PaymentFailedEvent(

        Long orderId,

        String reason

) {
}