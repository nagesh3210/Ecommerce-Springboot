package com.airbnb.paymentservice.kafka;

import com.airbnb.paymentservice.events.PaymentFailedEvent;
import com.airbnb.paymentservice.events.PaymentSuccessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentEventProducer
{

    private final KafkaTemplate<String,Object> kafkaTemplate;


    public void publishPaymentSucess(PaymentSuccessEvent event)
    {
        log.info("Publishing PaymentSucess Event {}",event);

        kafkaTemplate.send(KafkaTopics.PAYMENT_SUCCESS,event.orderId().toString(),event);

    }

    public void publicPaymentFailed(PaymentFailedEvent event)
    {
        log.info("Publishing PaymentFailedEvent",event);
        kafkaTemplate.send(KafkaTopics.PAYMENT_FAILED,event.orderId().toString(),event);
    }

}
