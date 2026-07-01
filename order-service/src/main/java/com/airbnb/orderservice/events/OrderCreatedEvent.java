package com.airbnb.orderservice.events;

import java.math.BigDecimal;

public record OrderCreatedEvent (Long orderId, Long productId,Integer quantity,BigDecimal amount,Long customerId){
}
