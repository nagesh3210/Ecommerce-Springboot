package com.airbnb.orderservice.config;

import com.airbnb.orderservice.kafka.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {

    @Bean
    NewTopic orderCreated() {

        return TopicBuilder
                .name(KafkaTopics.ORDER_CREATED)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    NewTopic inventoryReserved() {

        return TopicBuilder
                .name(KafkaTopics.INVENTORY_RESERVED)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
