package com.airbnb.paymentservice.events;

import java.math.BigDecimal;

public record InventoryReservedEvent(Long orderId, Long productId, Integer quantity, BigDecimal amount
) {
}
