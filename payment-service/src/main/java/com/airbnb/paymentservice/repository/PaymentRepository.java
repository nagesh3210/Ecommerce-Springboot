package com.airbnb.paymentservice.repository;

import com.airbnb.paymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository  extends JpaRepository<Payment,Long>
{
}
