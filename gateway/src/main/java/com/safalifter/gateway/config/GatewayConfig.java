package com.safalifter.gateway.config;
import com.safalifter.gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class GatewayConfig {
    private static final Logger logger = LoggerFactory.getLogger(GatewayConfig.class);
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://USER-SERVICE"))
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .uri("lb://AUTH-SERVICE"))
                .route("project-service", r -> r.path("/api/v1/project/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://PROJECT-SERVICE"))
                .route("event-service", r -> r.path("/api/v1/event/**", "/api/v1/eventType/**", "/api/v1/status/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://EVENT-SERVICE"))
                .route("account-service", r -> r.path("/api/v1/account/**", "/api/v1/finance/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://ACCOUNT-SERVICE"))
                .route("task-service", r -> r.path("/api/v1/task/**")
                        .filters(f -> f.filter(filter))
                        .uri("lb://TASK-SERVICE"))
                .build();
    }

    private Mono<Void> validateToken(ServerWebExchange exchange) {
        logger.info("Entered filter");
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        // Add your token validation logic here
        // If the token is invalid, you can set the response status to UNAUTHORIZED

        return Mono.empty();
    }
}