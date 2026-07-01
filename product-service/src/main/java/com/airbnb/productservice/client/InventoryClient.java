package com.airbnb.productservice.client;

import com.airbnb.productservice.dto.InventoryResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service",
        url = "http://localhost:8082"
)
public interface InventoryClient
{

    @GetMapping("api/v1/inventory/{productId}")
    InventoryResponse getInventory(@PathVariable("productId") long productId);

}
