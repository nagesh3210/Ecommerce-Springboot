package com.airbnb.orderservice.dto;

import java.math.BigDecimal;

public record OrderRequest(Long customerId,

                           Long productId,

                           Integer quantity,

                           BigDecimal amount)
{

}
