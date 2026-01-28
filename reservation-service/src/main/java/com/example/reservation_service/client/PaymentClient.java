package com.example.reservation_service.client;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class PaymentClient {

    @Bulkhead(name = "paymentBH", type = Bulkhead.Type.SEMAPHORE)
    @CircuitBreaker(name = "paymentCB", fallbackMethod = "fallback")
    @TimeLimiter(name = "paymentTL", fallbackMethod = "fallback")
    public CompletableFuture<Boolean> pay(Double amount) {

        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(20000); // pasarela lenta
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return true;
        });
    }

    public CompletableFuture<Boolean> fallback(Double amount, Throwable ex) {
        return CompletableFuture.completedFuture(false);
    }
}
