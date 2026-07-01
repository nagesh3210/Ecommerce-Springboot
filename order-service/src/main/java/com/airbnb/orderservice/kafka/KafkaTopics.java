package com.airbnb.orderservice.kafka;

public final class KafkaTopics
{
    private KafkaTopics(){}

    public static final String ORDER_CREATED="order-created";
    public static final String INVENTORY_RESERVED =
            "inventory-reserved";

    public static final String INVENTORY_FAILED =
            "inventory-failed";

    public static final String PAYMENT_SUCCESS =
            "payment-success";

    public static final String PAYMENT_FAILED =
            "payment-failed";
    public static final String ORDER_CONFIRMED =
            "order-confirmed";

    public static final String ORDER_CANCELLED =
            "order-cancelled";

}
