package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.CalificacionTransporteRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.CalificacionTransporteResponseDTO;
import com.agrovalle.agrovalle_connect.services.CalificacionTransporteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API para calificar el transporte de un viaje.
 */
@RestController
@RequestMapping("/api/v1/viajes")
public class CalificacionTransporteController {

    private final CalificacionTransporteService calificacionService;

    /**
     * Crea el controlador de calificaciones.
     *
     * @param service servicio de calificaciones de transporte
     */
    public CalificacionTransporteController(
            final CalificacionTransporteService service) {
        this.calificacionService = service;
    }

    /**
     * Registra la calificación del transporte del viaje autenticando al comprador.
     *
     * @param id identificador del viaje
     * @param solicitud métricas enviadas
     * @param authentication identidad autenticada
     * @return calificación registrada con HTTP 201
     */
    @PostMapping("/{id}/calificacion")
    public ResponseEntity<CalificacionTransporteResponseDTO> calificar(
            @PathVariable final Long id,
            @Valid @RequestBody final CalificacionTransporteRequestDTO solicitud,
            final Authentication authentication) {
        CalificacionTransporteResponseDTO respuesta =
                new CalificacionTransporteResponseDTO(calificacionService.registrar(
                        id,
                        Long.valueOf(authentication.getName()),
                        solicitud.getPuntualidad(),
                        solicitud.getEstadoCajas()));
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
