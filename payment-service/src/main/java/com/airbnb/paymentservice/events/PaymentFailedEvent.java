package com.airbnb.paymentservice.events;

public record PaymentFailedEvent(Long orderId, String reason) {
}
