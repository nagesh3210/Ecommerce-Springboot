package com.airbnb.inventoryservice.kafka;

import com.airbnb.inventoryservice.events.PaymentFailedEvent;
import com.airbnb.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Slf4j
@Component
public class PaymentFailedConsumer
{
    private final InventoryService inventoryService;


    @KafkaListener(topics = "payment-failed",groupId = "inventory-group",properties = { "spring.json.value.default.type=com.airbnb.inventoryservice.events.PaymentFailedEvent"
    })
    public void consume(PaymentFailedEvent event)
    {

        log.info("Payment Failed Received {}",event);

        inventoryService.releaseInventory(event.productId(), event.quantity());

    }
}
