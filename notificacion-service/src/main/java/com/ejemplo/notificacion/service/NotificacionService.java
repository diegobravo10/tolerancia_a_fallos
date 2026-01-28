package com.ejemplo.notificacion.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

@Service
public class NotificacionService {

    @CircuitBreaker(name = "notificacionCB", fallbackMethod = "fallbackEmail")
    public void enviarEmail() {
        throw new RuntimeException("SMTP caído");
    }

    public void fallbackEmail(Throwable t) {
        System.out.println("📥 Email encolado para envío posterior");
    }
}
