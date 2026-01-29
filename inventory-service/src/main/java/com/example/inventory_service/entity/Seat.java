package com.example.inventory_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "seats")
public class Seat {

    @Id
    private String seatId;

    private Long eventId;
    private boolean available;

    public Seat() {}

    public Seat(String seatId, Long eventId, boolean available) {
        this.seatId = seatId;
        this.eventId = eventId;
        this.available = available;
    }

    public String getSeatId() {
        return seatId;
    }

    public Long getEventId() {
        return eventId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}