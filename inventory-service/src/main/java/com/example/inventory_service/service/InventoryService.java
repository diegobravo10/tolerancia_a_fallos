package com.example.inventory_service.service;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

    private boolean inventoryAvailable = true;

    public void holdSeat(Long eventId, String seatId) {

        if (!inventoryAvailable) {
            throw new RuntimeException("Inventario caído");
        }

        System.out.println("✅ Asiento reservado: evento " + eventId + " asiento " + seatId);
    }

    // Método solo para pruebas
    public void setInventoryAvailable(boolean available) {
        this.inventoryAvailable = available;
    }
}
