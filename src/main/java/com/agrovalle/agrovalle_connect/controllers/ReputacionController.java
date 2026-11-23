package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.ReputacionResponseDTO;
import com.agrovalle.agrovalle_connect.services.ReputacionService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API pública de consulta de reputación de agricultores.
 */
@RestController
@RequestMapping("/api/v1/agricultores")
@Validated
public class ReputacionController {

    private final ReputacionService reputacionService;

    /**
     * Crea el controlador de reputación.
     *
     * @param service servicio de reputación
     */
    public ReputacionController(final ReputacionService service) {
        this.reputacionService = service;
    }

    /**
     * Consulta el promedio de estrellas de un agricultor.
     *
     * @param id identificador del agricultor
     * @return reputación promedio
     */
    @GetMapping("/{id}/reputacion")
    public ResponseEntity<ReputacionResponseDTO> obtenerReputacion(
            @PathVariable @Positive(message = "El ID del agricultor debe ser positivo")
            final Long id) {
        return ResponseEntity.ok(reputacionService.obtenerReputacion(id));
    }
}
