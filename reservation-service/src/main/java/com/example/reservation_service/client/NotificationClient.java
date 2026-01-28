package com.example.reservation_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Component;

@Component
public class NotificationClient {

    @CircuitBreaker(name = "notifyCB", fallbackMethod = "fallback")
    public void sendEmail(String email) {
        throw new RuntimeException("Servicio de correo caído");
    }

    public void fallback(String email, Throwable ex) {
        System.out.println("Correo no enviado, se enviará luego");
    }
}
