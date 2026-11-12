package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.TransportistaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.TransportistaResponseDTO;
import com.agrovalle.agrovalle_connect.services.TransportistaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para el registro de transportistas.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class TransportistaController {

    private final TransportistaService transportistaService;

    /**
     * Crea el controlador de transportistas.
     *
     * @param service servicio de registro
     */
    public TransportistaController(final TransportistaService service) {
        this.transportistaService = service;
    }

    /**
     * Registra un transportista y devuelve su token de acceso.
     *
     * @param dto datos de registro
     * @return transportista creado con HTTP 201
     */
    @PostMapping("/register-transportista")
    public ResponseEntity<TransportistaResponseDTO> registrar(
            @Valid @RequestBody final TransportistaRequestDTO dto) {
        TransportistaResponseDTO registrado = transportistaService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrado);
    }
}
