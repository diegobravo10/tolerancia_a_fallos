package com.example.inventory_service.controller;

import com.example.inventory_service.service.InventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    // Endpoint usado por reservation-service
    @PostMapping("/hold")
    public void holdSeat(@RequestParam Long eventId,
                         @RequestParam String seatId) {
        service.holdSeat(eventId, seatId);
    }

    // Endpoint para simular caída/recuperación
    @PostMapping("/toggle")
    public String toggle(@RequestParam boolean available) {
        service.setInventoryAvailable(available);
        return "Inventario disponible: " + available;
    }

    @GetMapping("/health")
    public String health() {
        return "OK - inventory-service";
    }
}

