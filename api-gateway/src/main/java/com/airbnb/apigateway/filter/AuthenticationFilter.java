package com.airbnb.apigateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>
{
    private final RouteValidator routeValidator;


    public AuthenticationFilter(RouteValidator routeValidator) {
        super(Config.class);
        this.routeValidator = routeValidator;

    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String path = exchange.getRequest().getURI().getPath();

            // Public APIs
            if (!routeValidator.isSecured.test(exchange.getRequest())) {
                return chain.filter(exchange);
            }


            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

                log.warn("Authorization header missing");

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }

            String authHeader =
                    exchange.getRequest()
                            .getHeaders()
                            .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {

                log.warn("Invalid Authorization header");

                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

                return exchange.getResponse().setComplete();
            }

            log.info("Authorization header validated");

            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}