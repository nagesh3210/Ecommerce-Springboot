package com.airbnb.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter, Ordered
{
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain)
    {

        long startTime = System.currentTimeMillis();

        String name = exchange.getRequest().getMethod().name();
        String path = exchange.getRequest().getURI().getPath();


        log.info("Incoming Request -> Methods: {} , Path: {}",name,path);


        return chain.filter(exchange).then(Mono.fromRunnable(()->{
            long duration= System.currentTimeMillis() -startTime;

            int status = exchange.getResponse().getStatusCode() != null ? exchange.getResponse().getStatusCode().value() : 0;


            log.info(
                    "Outgoing Response -> Status: {}, Duration: {} ms",
                    status,
                    duration
            );

        }));

    }

    @Override
    public int getOrder() {
        return -1;
    }
}
