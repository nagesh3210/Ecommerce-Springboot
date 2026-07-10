package com.airbnb.paymentservice.kafka;

public final class KafkaTopics
{
    private KafkaTopics(){}

    public static final String INVENTORY_RESERVED =
            "inventory-reserved";


    public static final String PAYMENT_SUCCESS =
            "payment-success";


    public static final String PAYMENT_FAILED =
            "payment-failed";

}
