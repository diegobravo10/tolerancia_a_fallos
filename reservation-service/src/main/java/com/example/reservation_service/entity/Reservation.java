package com.example.reservation_service.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Long eventId;
    private String seatId;
    private String email;
    private Double amount;
    private String status;

    private Instant createdAt = Instant.now();

    public Reservation() {}

    public Reservation(Long eventId, String seatId, String email, Double amount, String status) {
        this.eventId = eventId;
        this.seatId = seatId;
        this.email = email;
        this.amount = amount;
        this.status = status;
    }

    public String getId() { return id; }
    public Long getEventId() { return eventId; }
    public String getSeatId() { return seatId; }
    public String getEmail() { return email; }
    public Double getAmount() { return amount; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        this.status = status;
    }
}
