package com.example.inventory_service.service;

import com.example.inventory_service.entity.Seat;
import com.example.inventory_service.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private final SeatRepository repository;

    public InventoryService(SeatRepository repository) {
        this.repository = repository;
    }

    public void holdSeat(Long eventId, String seatId) {

        Seat seat = repository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Asiento no existe"));

        if (!seat.isAvailable()) {
            throw new RuntimeException("Asiento no disponible");
        }

        // ❗ Sin locks → posible condición de carrera (Parte B)
        seat.setAvailable(false);
        repository.save(seat);

        System.out.println("✅ Asiento reservado: " + seatId);
    }
}