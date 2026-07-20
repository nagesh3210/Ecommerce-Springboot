package com.airbnb.apigateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)

                .authorizeExchange(exchange -> exchange

                        // Public APIs
                        .pathMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()

                        // Swagger / Eureka / Actuator
                        .pathMatchers(
                                "/actuator/**",
                                "/eureka/**"
                        ).permitAll()

                        // Everything else requires authentication
                        .anyExchange().authenticated()
                )

                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwt -> {})
                )

                .build();
    }
}
