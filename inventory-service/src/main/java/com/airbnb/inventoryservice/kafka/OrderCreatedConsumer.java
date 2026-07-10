package com.airbnb.inventoryservice.kafka;

import com.airbnb.inventoryservice.entity.Inventory;
import com.airbnb.inventoryservice.events.InventoryFailedEvent;
import com.airbnb.inventoryservice.events.InventoryReservedEvent;
import com.airbnb.inventoryservice.events.OrderCreatedEvent;
import com.airbnb.inventoryservice.exception.InventoryNotAvailableException;
import com.airbnb.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderCreatedConsumer
{

    private final InventoryService inventoryService;

    private final InventoryEventProducer inventoryEventProducer;

    @KafkaListener(topics = KafkaTopics.ORDER_CREATED,groupId = "inventory-group")
    public void consume(OrderCreatedEvent event)
    {
        log.info(
                "Received OrderCreatedEvent : {}",
                event
        );

        try {
            Inventory inventory = inventoryService.reserveInventory(event.productId(), event.quantity());

            inventoryEventProducer.publishInventoryReserved(new InventoryReservedEvent(event.orderId(),inventory.getProductId(),event.quantity(),event.amount()));


        }
        catch (InventoryNotAvailableException ex)
        {
            log.error(ex.getMessage());

            inventoryEventProducer.publishInventoryFailed(new InventoryFailedEvent(event.orderId(),event.productId(), ex.getMessage()));
        }

    }

}
