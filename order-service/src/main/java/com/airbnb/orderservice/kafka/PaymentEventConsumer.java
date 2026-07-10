package com.airbnb.orderservice.kafka;


import com.airbnb.orderservice.events.PaymentFailedEvent;
import com.airbnb.orderservice.events.PaymentSuccessEvent;
import com.airbnb.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer
{
    public final OrderService orderService;

    @KafkaListener(
            topics = "payment-success",
            groupId = "order-group",
    properties = {
    "spring.json.value.default.type=com.airbnb.orderservice.events.PaymentSuccessEvent"
}
    )
    public void paymentSucess(PaymentSuccessEvent event)
    {
        log.info("Payment sucess recievd {}",event);

        orderService.completePayment(event.orderId());
    }

    @KafkaListener(
            topics = "payment-failed",
            groupId = "order-group",
    properties = {
    "spring.json.value.default.type=com.airbnb.orderservice.events.PaymentFailedEvent"
}
    )
    public void paymentFailed(PaymentFailedEvent event)
    {
        log.info("Payment Failed {}",event);

        orderService.paymentFailed(event.orderId());
    }

}
