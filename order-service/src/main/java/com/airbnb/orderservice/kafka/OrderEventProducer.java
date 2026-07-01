package com.airbnb.orderservice.kafka;

import com.airbnb.orderservice.events.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer
{
    private final KafkaTemplate<String,Object> kafkaTemplate;


    public void  publishOrderCreated(OrderCreatedEvent event)
    {
        kafkaTemplate.send(KafkaTopics.ORDER_CREATED,event.orderId().toString(),event);
        log.info(
                "Publishing OrderCreatedEvent : {}",
                event
        );


    }


}
