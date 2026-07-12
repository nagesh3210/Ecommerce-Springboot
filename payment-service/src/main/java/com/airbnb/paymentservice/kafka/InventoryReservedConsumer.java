package com.airbnb.paymentservice.kafka;

import com.airbnb.paymentservice.entity.Payment;
import com.airbnb.paymentservice.enums.PaymentStatus;
import com.airbnb.paymentservice.events.InventoryReservedEvent;
import com.airbnb.paymentservice.events.PaymentFailedEvent;
import com.airbnb.paymentservice.events.PaymentSuccessEvent;
import com.airbnb.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Slf4j
@RequiredArgsConstructor
public class InventoryReservedConsumer
{

    private final PaymentService paymentService;

    private final PaymentEventProducer paymentEventProducer;


    @KafkaListener(topics = KafkaTopics.INVENTORY_RESERVED, groupId = "payment-group",properties = {
            "spring.json.value.default.type=com.airbnb.paymentservice.events.InventoryReservedEvent"
    })
    public void consume(InventoryReservedEvent event)
    {

        log.info("Inventory Reserved Event Received{}",event);

        Payment payment = paymentService.processPayment(event.orderId(), event.amount());


        if(payment.getStatus()== PaymentStatus.SUCCESS)
        {
            paymentEventProducer.publishPaymentSucess(new PaymentSuccessEvent(event.orderId()));

        }
        else {
            paymentEventProducer.publicPaymentFailed(new PaymentFailedEvent(event.orderId(), event.productId(),event.quantity(),"payment Failed due to payment isuue '"+event.orderId()+"'"));
        }

    }

}
