package com.example.reservation_service.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificationClient {

    private final RestTemplate restTemplate;

    @Value("${services.notification.base-url}")
    private String notificationBaseUrl;

    public NotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "notifyCB", fallbackMethod = "fallback")
    public void sendEmail(String email) {

        String url = notificationBaseUrl + "/notificaciones/email?email=" + email;
        restTemplate.postForEntity(url, null, Void.class);
    }

    public void fallback(String email, Throwable ex) {
        System.out.println("📥 Correo no enviado, se enviará luego");
    }
}
