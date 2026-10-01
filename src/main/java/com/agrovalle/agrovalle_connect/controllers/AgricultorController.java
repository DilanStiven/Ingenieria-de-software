package com.agrovalle.agrovalle_connect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrovalle.agrovalle_connect.dtos.AgricultorRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorResponseDTO;
import com.agrovalle.agrovalle_connect.services.AgricultorService;

import jakarta.validation.Valid;

/**
 * Controlador REST encargado del registro de agricultores (HU-01).
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AgricultorController {

    /**
     * Servicio encargado de la lógica de registro.
     */
    @Autowired
    private AgricultorService agricultorService;

    /**
     * Registra un nuevo agricultor en la plataforma.
     *
     * @param dto datos del agricultor a registrar
     * @return agricultor registrado con código HTTP 201
     */
    @PostMapping("/register")
    public ResponseEntity<AgricultorResponseDTO> registrar(
            @Valid @RequestBody final AgricultorRequestDTO dto) {
        AgricultorResponseDTO registrado = agricultorService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(registrado);
    }
}