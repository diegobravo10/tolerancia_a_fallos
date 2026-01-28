package com.ejemplo.notificacion.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {

    @PostMapping("/email")
    public ResponseEntity<String> enviarCorreo() {

        // Simular fallo del servicio
        if (Math.random() < 0.8) {
            throw new RuntimeException("Servidor SMTP no disponible");
        }

        return ResponseEntity.ok("Correo enviado correctamente");
    }
}
