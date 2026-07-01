package com.airbnb.orderservice.controller;

import com.airbnb.orderservice.dto.OrderRequest;
import com.airbnb.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService service;

    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderRequest request)
    {
        service.createOrder(request);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
