package com.example.reservation_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {

    private final RestTemplate restTemplate;

    @Value("${services.inventory.base-url}")
    private String inventoryBaseUrl;

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "inventoryCB", fallbackMethod = "fallback")
    @Retry(name = "inventoryRetry")
    public void reserveSeat(Long eventId, String seatId) {

        String url = inventoryBaseUrl +
                "/inventory/hold?eventId=" + eventId +
                "&seatId=" + seatId;

        restTemplate.postForEntity(url, null, Void.class);
    }

    public void fallback(Long eventId, String seatId, Throwable ex) {
        throw new RuntimeException("Inventario no disponible");
    }
}
