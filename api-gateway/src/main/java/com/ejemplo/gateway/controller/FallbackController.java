package com.ejemplo.gateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/reservas")
    public ResponseEntity<String> reservasFallback() {
        return ResponseEntity
                .status(503)
                .body("Servicio de reservas no disponible. Intente más tarde.");
    }

    @GetMapping("/notificaciones")
    public ResponseEntity<String> notificacionesFallback() {
        return ResponseEntity
                .ok("Reserva exitosa. El correo será enviado posteriormente.");
    }
}
