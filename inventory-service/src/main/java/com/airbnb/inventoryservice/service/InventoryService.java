package com.airbnb.inventoryservice.service;


import com.airbnb.inventoryservice.dto.InventoryResponse;
import com.airbnb.inventoryservice.entity.Inventory;
import com.airbnb.inventoryservice.exception.InventoryNotAvailableException;
import com.airbnb.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Transactional
    public Inventory reserveInventory(Long productId,Integer quantity)
    {
        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow(() -> new InventoryNotAvailableException(productId));


        if(inventory.getAvailableQuantity()<quantity)
        {
            throw new InventoryNotAvailableException(productId,quantity, inventory.getAvailableQuantity());
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity()-quantity);

        Inventory save = inventoryRepository.save(inventory);

        log.info("Inventory reserved. product={}, Remaining={}",
                save.getProductId(),
                save.getAvailableQuantity()
                );

        return save;

    }

    @Transactional
    public void releaseInventory(Long productId, Integer quantity)
    {

        Inventory inventory = inventoryRepository.findByProductId(productId).orElseThrow();


        log.info("No of stock before release stock : {}",inventory.getAvailableQuantity());

        inventory.setAvailableQuantity(inventory.getAvailableQuantity()+quantity);


        Inventory save = inventoryRepository.save(inventory);


        log.info(
                "Inventory released product {} quantity {}",
                productId,
                quantity
        );

        log.info("No of stock after release stock : {}",save.getAvailableQuantity());

    }

}
