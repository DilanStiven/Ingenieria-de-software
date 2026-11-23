package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.ResenaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.ResenaResponseDTO;
import com.agrovalle.agrovalle_connect.services.ResenaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ResenaController {

    private final ResenaService resenaService;

    public ResenaController(final ResenaService service) {
        this.resenaService = service;
    }

    @PostMapping("/resenas")
    public ResponseEntity<ResenaResponseDTO> guardar(
            @Valid @RequestBody final ResenaRequestDTO dto,
            final Authentication authentication) {
        Long compradorId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resenaService.guardar(dto, compradorId));
    }

    /**
     * Expone la reseña como operación sobre la orden protegida por JWT.
     *
     * @param ordenId identificador de la orden
     * @param dto calificación y comentario
     * @param authentication comprador autenticado
     * @return reseña creada
     */
    @PostMapping("/ordenes/{ordenId}/resena")
    public ResponseEntity<ResenaResponseDTO> guardarPorOrden(
            @PathVariable final Long ordenId,
            @Valid @RequestBody final ResenaRequestDTO dto,
            final Authentication authentication) {
        Long compradorId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resenaService.guardarPorOrden(
                        ordenId, dto, compradorId));
    }
}
