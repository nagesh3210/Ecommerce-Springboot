package com.airbnb.orderservice.kafka;

import com.airbnb.orderservice.events.InventoryFailedEvent;
import com.airbnb.orderservice.events.InventoryReservedEvent;
import com.airbnb.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer
{

    private final OrderService orderService;


    @KafkaListener(topics = "inventory-reserved", groupId = "order-group")
    public void inventoryReservedEvent(InventoryReservedEvent event)
    {
        log.info("Inventory reserved event received{}",event);

        orderService.confirmOrder(event.orderId());

    }

    @KafkaListener(topics = "inventory-failed", groupId = "order-group")
    public void inventoryFailed(InventoryFailedEvent event)
    {
        log.info("Inventory Failed event received{}",event);

        orderService.cancelOrder(event.orderId());

    }

}
