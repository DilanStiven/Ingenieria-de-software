package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.VehiculoRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.VehiculoResponseDTO;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import com.agrovalle.agrovalle_connect.services.VehiculoService;
import jakarta.validation.Valid;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para el registro de vehículos de carga (HU-06).
 */
@RestController
@RequestMapping("/api/v1/vehiculos")
public class VehiculoController {

    private static final String ROL_AGRICULTOR = "ROLE_AGRICULTOR";
    private static final String ROL_TRANSPORTISTA = "ROLE_TRANSPORTISTA";

    private final VehiculoService vehiculoService;

    /**
     * Crea el controlador con el servicio de vehículos.
     *
     * @param service servicio de vehículos
     */
    public VehiculoController(final VehiculoService service) {
        this.vehiculoService = service;
    }

    /**
     * Registra un vehículo a nombre del usuario autenticado.
     *
     * @param dto datos del vehículo
     * @param authentication autenticación JWT
     * @return vehículo creado con HTTP 201
     */
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> registrar(
            @Valid @RequestBody final VehiculoRequestDTO dto,
            final Authentication authentication) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(java.util.stream.Collectors.toSet());
        RolUsuario rol;
        if (roles.contains(ROL_AGRICULTOR)) {
            rol = RolUsuario.AGRICULTOR;
        } else if (roles.contains(ROL_TRANSPORTISTA)) {
            rol = RolUsuario.TRANSPORTISTA;
        } else {
            throw new AccessDeniedException("Solo agricultores y transportistas pueden registrar vehículos");
        }

        Long usuarioId = Long.valueOf(authentication.getName());
        VehiculoResponseDTO response = vehiculoService.registrar(dto, usuarioId, rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
