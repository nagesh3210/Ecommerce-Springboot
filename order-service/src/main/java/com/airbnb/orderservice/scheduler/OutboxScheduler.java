package com.airbnb.orderservice.scheduler;

import com.airbnb.orderservice.entity.EventStatus;
import com.airbnb.orderservice.entity.OutboxEvent;
import com.airbnb.orderservice.events.OrderCreatedEvent;
import com.airbnb.orderservice.kafka.KafkaTopics;
import com.airbnb.orderservice.kafka.OrderEventProducer;
import com.airbnb.orderservice.repository.OutboxRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxScheduler {

    private final OutboxRepository outboxRepository;
    private final OrderEventProducer orderEventProducer;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 5000)
    public void publishPendingEvents() {

        List<OutboxEvent> events = new ArrayList<>();

        events.addAll(
                outboxRepository.findByStatusAndNextRetryAtLessThanEqualOrderByCreatedAtAsc(
                        EventStatus.PENDING,
                        LocalDateTime.now()
                )
        );

        events.addAll(
                outboxRepository.findByStatusAndNextRetryAtLessThanEqualOrderByCreatedAtAsc(
                        EventStatus.RETRY,
                        LocalDateTime.now()
                )
        );

        if (events.isEmpty()) {
            return;
        }

        log.info("Found {} pending/retry events", events.size());

        for (OutboxEvent outboxEvent : events) {

            try {

                switch (outboxEvent.getEventType()) {

                    case KafkaTopics.ORDER_CREATED -> {

                        OrderCreatedEvent event =
                                objectMapper.readValue(
                                        outboxEvent.getPayload(),
                                        OrderCreatedEvent.class
                                );

                        orderEventProducer.publishOrderCreated(event);

                        log.info(
                                "Published OrderCreatedEvent for order {}",
                                event.orderId()
                        );
                    }

                    default -> throw new IllegalArgumentException(
                            "Unknown event type : " + outboxEvent.getEventType()
                    );
                }

                outboxEvent.setStatus(EventStatus.SENT);
                outboxEvent.setProcessedAt(LocalDateTime.now());

                outboxRepository.save(outboxEvent);

                log.info(
                        "Outbox event {} published successfully",
                        outboxEvent.getId()
                );

            } catch (Exception ex) {

                log.error(
                        "Failed to publish outbox event {}",
                        outboxEvent.getId(),
                        ex
                );

                int retry = outboxEvent.getRetryCount() + 1;

                outboxEvent.setRetryCount(retry);
                outboxEvent.setErrorMessage(ex.getMessage());

                if (retry >= 5) {

                    outboxEvent.setStatus(EventStatus.DEAD);

                    log.error(
                            "Outbox event {} moved to DEAD after {} retries",
                            outboxEvent.getId(),
                            retry
                    );

                } else {

                    outboxEvent.setStatus(EventStatus.RETRY);

                    outboxEvent.setNextRetryAt(
                            LocalDateTime.now()
                                    .plusSeconds(30L * retry)
                    );

                    log.warn(
                            "Retry {} scheduled for outbox event {}",
                            retry,
                            outboxEvent.getId()
                    );
                }

                outboxRepository.save(outboxEvent);
            }
        }
    }
}