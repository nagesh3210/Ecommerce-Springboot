//package com.airbnb.orderservice.config;
//
//import com.airbnb.orderservice.events.InventoryReservedEvent;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class KafkaConsumerConfig
//{
//
//    @Bean
//    public ConsumerFactory<String, InventoryReservedEvent>
//    inventoryConsumerFactory() {
//
//        JsonDeserializer<InventoryReservedEvent> deserializer =
//                new JsonDeserializer<>(InventoryReservedEvent.class);
//
//        deserializer.addTrustedPackages("*");
//
//
//        Map<String,Object> props = new HashMap<>();
//
//        props.put(
//                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                "localhost:9092"
//        );
//
//        props.put(
//                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class
//        );
//
//        return new DefaultKafkaConsumerFactory<>(
//                props,
//                new StringDeserializer(),
//                deserializer
//        );
//    }
//
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory
//            <String, InventoryReservedEvent>
//    inventoryKafkaListenerFactory(){
//
//        ConcurrentKafkaListenerContainerFactory
//                <String, InventoryReservedEvent> factory =
//                new ConcurrentKafkaListenerContainerFactory<>();
//
//        factory.setConsumerFactory(
//                inventoryConsumerFactory()
//        );
//
//        return factory;
//    }
//
//}
