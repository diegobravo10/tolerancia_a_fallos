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

    @PostMapping("/hold")
    public void holdSeat(@RequestParam Long eventId,
                         @RequestParam String seatId) {
        service.holdSeat(eventId, seatId);
    }

    @GetMapping("/health")
    public String health() {
        return "OK - inventory-service";
    }
}
