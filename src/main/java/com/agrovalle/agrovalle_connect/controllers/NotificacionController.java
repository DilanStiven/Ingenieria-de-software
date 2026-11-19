package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.NotificacionResponseDTO;
import com.agrovalle.agrovalle_connect.services.NotificacionService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API de consulta de notificaciones para el agricultor autenticado.
 */
@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    /**
     * Crea el controlador de notificaciones.
     *
     * @param service servicio de notificaciones
     */
    public NotificacionController(final NotificacionService service) {
        this.notificacionService = service;
    }

    /**
     * Consulta la bandeja del agricultor autenticado.
     *
     * @param authentication identidad autenticada
     * @return notificaciones del agricultor
     */
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listar(
            final Authentication authentication) {
        Long agricultorId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(notificacionService.listarPorAgricultor(agricultorId));
    }
}
