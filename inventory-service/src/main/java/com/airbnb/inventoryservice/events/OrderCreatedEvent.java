package com.airbnb.inventoryservice.events;

import java.math.BigDecimal;

public record OrderCreatedEvent(

        Long orderId,

        Long customerId,

        Long productId,

        Integer quantity,

        BigDecimal amount

)  {
}
