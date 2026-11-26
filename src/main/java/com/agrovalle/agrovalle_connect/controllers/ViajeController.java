package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.CheckpointViajeResponseDTO;
import com.agrovalle.agrovalle_connect.services.CheckpointService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Operaciones de checkpoints sobre viajes logísticos.
 */
@RestController
@RequestMapping("/api/v1/viajes")
public class ViajeController {

    private final CheckpointService checkpointService;

    public ViajeController(final CheckpointService service) {
        this.checkpointService = service;
    }

    @PatchMapping("/{id}/llegada")
    public ResponseEntity<CheckpointViajeResponseDTO> registrarLlegada(
            @PathVariable final Long id,
            final Authentication authentication) {
        Long transportistaId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(checkpointService.registrarLlegada(
                id, transportistaId));
    }

    @PatchMapping("/{id}/entrega")
    public ResponseEntity<CheckpointViajeResponseDTO> registrarEntrega(
            @PathVariable final Long id,
            final Authentication authentication) {
        Long transportistaId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(checkpointService.registrarEntrega(
                id, transportistaId));
    }
}
