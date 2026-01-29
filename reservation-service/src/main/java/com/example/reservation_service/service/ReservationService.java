package com.example.reservation_service.service;

import com.example.reservation_service.client.InventoryClient;
import com.example.reservation_service.client.NotificationClient;
import com.example.reservation_service.client.PaymentClient;
import com.example.reservation_service.dto.ReserveRequest;
import com.example.reservation_service.entity.Reservation;
import com.example.reservation_service.repository.ReservationRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository repository;
    private final InventoryClient inventoryClient;
    private final PaymentClient paymentClient;
    private final NotificationClient notificationClient;

    public ReservationService(ReservationRepository repository,
                              InventoryClient inventoryClient,
                              PaymentClient paymentClient,
                              NotificationClient notificationClient) {
        this.repository = repository;
        this.inventoryClient = inventoryClient;
        this.paymentClient = paymentClient;
        this.notificationClient = notificationClient;
    }

    public Reservation createReservation(ReserveRequest request) {

        // 1️⃣ Inventario (CRÍTICO)
        inventoryClient.reserveSeat(request.eventId(), request.seatId());

        // 2️⃣ Guardar reserva (CREATED)
        Reservation reservation = save(new Reservation(
                request.eventId(),
                request.seatId(),
                request.email(),
                request.amount(),
                "CREATED"
        ));

        // 3️⃣ Pago (usa ID real de la reserva)
        boolean paid = paymentClient
                .pay(reservation.getId(), request.amount())
                .join();

        if (!paid) {
            reservation.setStatus("PAYMENT_FAILED");
            save(reservation);
            throw new RuntimeException("Pago fallido");
        }

        // 4️⃣ Confirmar reserva
        reservation.setStatus("CONFIRMED");
        save(reservation);

        // 5️⃣ Notificación (NO CRÍTICA)
        notificationClient.sendEmail(request.email());

        return reservation;
    }

    // ===============================
    // DB resiliente
    // ===============================
    @CircuitBreaker(name = "dbCB", fallbackMethod = "dbFallback")
    @Retry(name = "dbRetry")
    public Reservation save(Reservation reservation) {
        return repository.save(reservation);
    }

    public Reservation dbFallback(Reservation reservation, Throwable ex) {
        throw new RuntimeException("Base de datos no disponible");
    }
}
