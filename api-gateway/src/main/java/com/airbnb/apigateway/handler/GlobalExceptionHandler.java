package com.airbnb.apigateway.handler;

import com.airbnb.apigateway.exception.ErrorResponse;
import com.airbnb.apigateway.filter.CorrelationIdFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
@Order(-2)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler
{
    private final ObjectMapper objectMapper;


    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex)
    {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(ex.getMessage())
                .path(exchange.getRequest().getPath().value())
                .correlationId(
                        (String) exchange.getAttribute(
                                CorrelationIdFilter.CORRELATION_ID
                        )
                )
                .build();


        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        try {
            byte[] bytes = objectMapper.writeValueAsBytes(response);

            return exchange.getResponse()
                    .writeWith(
                            Mono.just(
                                    exchange.getResponse()
                                            .bufferFactory()
                                            .wrap(bytes)
                            )
                    );

        } catch (Exception e) {
            return Mono.error(e);
        }

    }
}
