package com.example.reservation_service.dto;

public record ReserveRequest(   Long eventId,
                                String seatId,
                                String email,
                                Double amount) {
}
