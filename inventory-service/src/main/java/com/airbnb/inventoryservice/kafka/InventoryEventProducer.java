package com.airbnb.inventoryservice.kafka;

import com.airbnb.inventoryservice.events.InventoryFailedEvent;
import com.airbnb.inventoryservice.events.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventProducer {

    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void publishInventoryReserved(InventoryReservedEvent event)
    {
        log.info(
                "Publishing InventoryReservedEvent : {}",
                event
        );

        kafkaTemplate.send(KafkaTopics.INVENTORY_RESERVED,event.orderId().toString(),event);
    }


    public void publishInventoryFailed(InventoryFailedEvent event)
    {
        log.info(
                "Publishing InventoryFailedEvent : {}",
                event
        );

        kafkaTemplate.send(KafkaTopics.INVENTORY_FAILED,event.orderId().toString(),event);

    }



}
