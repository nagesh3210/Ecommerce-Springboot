package com.airbnb.paymentservice.service;


import com.airbnb.paymentservice.entity.Payment;
import com.airbnb.paymentservice.enums.PaymentStatus;
import com.airbnb.paymentservice.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentService
{

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment processPayment(Long orderId, BigDecimal amount)
    {
        log.info("Process Payment for order {}",orderId);

        boolean paymentSuccess = true;


        Payment payment = Payment.builder().orderId(orderId).amount(amount).status(paymentSuccess ? PaymentStatus.SUCCESS : PaymentStatus.FAILED).build();

        Payment savedPayment = paymentRepository.save(payment);


        log.info("Payment completed {}", savedPayment);

        return savedPayment;

    }

}
