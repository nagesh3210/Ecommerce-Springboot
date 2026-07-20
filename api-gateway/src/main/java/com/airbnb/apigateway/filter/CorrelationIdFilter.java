package com.airbnb.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Slf4j
public class CorrelationIdFilter implements GlobalFilter, Ordered {

    public static final String CORRELATION_ID = "X-Correlation-ID";


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String correlationId = exchange.getRequest()
                .getHeaders()
                .getFirst(CORRELATION_ID);

        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
        }

        exchange.getAttributes().put(CORRELATION_ID, correlationId);


        // Make it effectively final
        final String finalCorrelationId = correlationId;


        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(exchange.getRequest()
                        .mutate()
                        .header(CORRELATION_ID, finalCorrelationId)
                        .build())
                .build();

        log.info("Correlation ID: {}", finalCorrelationId);

        mutatedExchange.getResponse().beforeCommit(() -> {
            mutatedExchange.getResponse()
                    .getHeaders()
                    .add(CORRELATION_ID, finalCorrelationId);
            return Mono.empty();
        });

        return chain.filter(mutatedExchange);

    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}