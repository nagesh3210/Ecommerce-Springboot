package com.airbnb.paymentservice.entity;

import com.airbnb.paymentservice.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name="payments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment
{
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    private Long orderId;


    private BigDecimal amount;


    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }
}
