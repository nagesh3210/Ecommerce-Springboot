package com.airbnb.orderservice.repository;

import com.airbnb.orderservice.entity.EventStatus;
import com.airbnb.orderservice.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OutboxRepository extends JpaRepository<OutboxEvent,Long>{


    List<OutboxEvent> findByStatus(EventStatus status);

    List<OutboxEvent> findByStatusOrderByCreatedAtAsc(EventStatus status);

    List<OutboxEvent> findByStatusAndNextRetryAtLessThanEqualOrderByCreatedAtAsc(
            EventStatus status,
            LocalDateTime nextRetryAt
    );

}
