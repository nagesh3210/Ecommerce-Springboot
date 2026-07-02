package com.airbnb.orderservice.service;

import com.airbnb.orderservice.dto.OrderRequest;
import com.airbnb.orderservice.entity.Order;
import com.airbnb.orderservice.entity.OrderStatus;
import com.airbnb.orderservice.events.OrderCreatedEvent;
import com.airbnb.orderservice.kafka.OrderEventProducer;
import com.airbnb.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService
{

    private final OrderRepository repository;
    private final OrderEventProducer producer;


    public void createOrder(OrderRequest request)
    {
        Order order = Order.builder().
                customerId(request.customerId()).productId(request.productId()).quantity(request.quantity())
                .amount(request.amount()).status(OrderStatus.CREATED).build();

        Order save = repository.save(order);

        producer.publishOrderCreated(new OrderCreatedEvent(
                save.getId(),
                save.getCustomerId(),
                save.getProductId(),
                save.getQuantity(),
                save.getAmount()
        ));


    }

}
