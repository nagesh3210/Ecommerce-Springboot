package com.airbnb.orderservice.service;

import com.airbnb.orderservice.entity.EventStatus;
import com.airbnb.orderservice.entity.OutboxEvent;
import com.airbnb.orderservice.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class OutboxService
{
    private final OutboxRepository outboxRepository;

    public OutboxEvent saveEvent(String aggregateType , Long aggregateId,
                                 String eventType,
                                 String payload)
    {

        OutboxEvent event = OutboxEvent.builder()
                .aggregateType(aggregateType)
                .aggregateId(aggregateId)
                .eventType(eventType)
                .payload(payload)
                .status(EventStatus.PENDING)
                .retryCount(0)
                .createdAt(LocalDateTime.now())
                .nextRetryAt(LocalDateTime.now())
                .build();

        return outboxRepository.save(event);
    }


}
