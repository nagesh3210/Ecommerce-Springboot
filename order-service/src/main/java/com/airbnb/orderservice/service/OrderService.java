package com.airbnb.orderservice.service;

import com.airbnb.orderservice.dto.OrderRequest;
import com.airbnb.orderservice.entity.Order;
import com.airbnb.orderservice.entity.OrderStatus;
import com.airbnb.orderservice.events.OrderCreatedEvent;
import com.airbnb.orderservice.kafka.KafkaTopics;
import com.airbnb.orderservice.kafka.OrderEventProducer;
import com.airbnb.orderservice.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService
{

    private final OrderRepository repository;
    private final OrderEventProducer producer;
    private final ObjectMapper objectMapper;
    private final OutboxService outboxService;



    @Transactional
    public void createOrder(OrderRequest request) throws JsonProcessingException
    {
        Order order = Order.builder().
                customerId(request.customerId()).productId(request.productId()).quantity(request.quantity())
                .amount(request.amount()).status(OrderStatus.CREATED).build();

        Order save = repository.save(order);


        OrderCreatedEvent event= new OrderCreatedEvent(save.getId(), save.getCustomerId(), save.getProductId(), save.getQuantity(), save.getAmount());


        String payload = objectMapper.writeValueAsString(event);


       outboxService.saveEvent("ORDER", save.getId(), KafkaTopics.ORDER_CREATED,payload);

//        producer.publishOrderCreated(new OrderCreatedEvent(
//                save.getId(),
//                save.getCustomerId(),
//                save.getProductId(),
//                save.getQuantity(),
//                save.getAmount()
//        ));


    }

    @Transactional
    public void confirmOrder(Long orderId){

        Order order = repository.findById(orderId).orElseThrow();

        order.setStatus(OrderStatus.CONFIRMED);

        repository.save(order);

        log.info("Order Confirmed {}",orderId);
    }

    public void cancelOrder(Long orderId)
    {
        Order order = repository.findById(orderId).orElseThrow();

        order.setStatus(OrderStatus.CANCELLED);


        repository.save(order);

        log.info("order Cancelled {}",orderId);
    }


    @Transactional
    public void completePayment(Long orderId)
    {

        Order order = repository.findById(orderId).orElseThrow(() -> new RuntimeException("This orerid is not present"));


        order.setStatus(OrderStatus.PAYMENT_COMPLETED);

        Order save = repository.save(order);

        log.info("Payment Completed for  order id {}",orderId);
    }


    @Transactional
    public void paymentFailed(Long orderId)
    {
        Order order = repository.findById(orderId).orElseThrow(() -> new RuntimeException("Order id not present"));


        order.setStatus(OrderStatus.PAYMENT_FAILED);

        Order save = repository.save(order);

        log.info("Order cancelled because of payment failed {}",orderId);

    }


}
