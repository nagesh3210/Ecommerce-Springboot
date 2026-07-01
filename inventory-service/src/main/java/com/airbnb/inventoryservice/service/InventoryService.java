package com.airbnb.inventoryservice.service;


import com.airbnb.inventoryservice.dto.InventoryResponse;
import com.airbnb.inventoryservice.entity.Inventory;
import com.airbnb.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService
{

    private final InventoryRepository inventoryRepository;

    public InventoryResponse checkStock(Long productId)
    {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow();

        return new InventoryResponse(
                inventory.getProductId(),
                inventory.getAvailableQuantity(),
                inventory.getAvailableQuantity()>0
        );
    }

}
