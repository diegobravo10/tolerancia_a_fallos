package com.example.reservation_service.controller;
import com.example.reservation_service.dto.ReserveRequest;
import com.example.reservation_service.dto.ReserveResponse;
import com.example.reservation_service.entity.Reservation;
import com.example.reservation_service.service.ReservationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService service;

    public ReservationController(ReservationService service) {
        this.service = service;
    }

    @PostMapping("/reserve")
    public ReserveResponse reserve(@RequestBody ReserveRequest request) {

        Reservation reservation = service.createReservation(request);

        return new ReserveResponse(
                reservation.getId(),
                reservation.getStatus(),
                "Reserva procesada correctamente"
        );
    }

    @GetMapping("/health")
    public String health() {
        return "OK - reservation-service";
    }
}
