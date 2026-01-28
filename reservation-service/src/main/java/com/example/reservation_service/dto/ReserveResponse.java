package com.example.reservation_service.dto;

public record ReserveResponse(  String reservationId,
                                String status,
                                String message) {
}
