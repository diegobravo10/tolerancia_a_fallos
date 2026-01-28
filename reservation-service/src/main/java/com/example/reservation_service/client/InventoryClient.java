package com.example.reservation_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Component;

@Component
public class InventoryClient {

    @CircuitBreaker(name = "inventoryCB", fallbackMethod = "fallback")
    @Retry(name = "inventoryRetry")
    public void reserveSeat(Long eventId, String seatId) {
        // Simulación: aquí luego llamas al inventory-service
        System.out.println("Inventario OK");
    }

    public void fallback(Long eventId, String seatId, Throwable ex) {
        throw new RuntimeException("Inventario no disponible");
    }
}
