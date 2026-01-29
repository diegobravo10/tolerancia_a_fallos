package com.example.reservation_service.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Component
public class PaymentClient {

    private final RestTemplate restTemplate;

    @Value("${services.payment.base-url}")
    private String paymentBaseUrl;

    public PaymentClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Bulkhead(name = "paymentBH", type = Bulkhead.Type.SEMAPHORE)
    @CircuitBreaker(name = "paymentCB", fallbackMethod = "fallback")
    @TimeLimiter(name = "paymentTL", fallbackMethod = "fallback")
    public CompletableFuture<Boolean> pay(String reservationId, Double amount) {

        return CompletableFuture.supplyAsync(() -> {
            String url = paymentBaseUrl +
                    "/payments/charge?reservationId=" + reservationId +
                    "&amount=" + amount;

            return restTemplate.postForObject(url, null, Boolean.class);
        });
    }

    public CompletableFuture<Boolean> fallback(String reservationId, Double amount, Throwable ex) {
        System.out.println("Pago fallido o timeout");
        return CompletableFuture.completedFuture(false);
    }
}
