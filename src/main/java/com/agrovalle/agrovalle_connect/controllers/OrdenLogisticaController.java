package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.AsignacionRutaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaResponseDTO;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import com.agrovalle.agrovalle_connect.services.OrdenLogisticaService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API para crear órdenes logísticas y programar rutas (HU-07).
 */
@RestController
@RequestMapping("/api/v1/ordenes")
public class OrdenLogisticaController {

    private static final String AUTHORITY_AGRICULTOR = "ROLE_AGRICULTOR";

    private final OrdenLogisticaService ordenService;

    /**
     * Crea el controlador de órdenes.
     *
     * @param service servicio de órdenes y rutas
     */
    public OrdenLogisticaController(final OrdenLogisticaService service) {
        this.ordenService = service;
    }

    /**
     * Crea una orden logística pendiente de alistamiento.
     *
     * @param dto datos de la carga
     * @param authentication agricultor autenticado
     * @return orden creada con HTTP 201
     */
    @PostMapping
    public ResponseEntity<OrdenLogisticaResponseDTO> crear(
            @Valid @RequestBody final OrdenLogisticaRequestDTO dto,
            final Authentication authentication) {
        Long agricultorId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ordenService.crear(dto, agricultorId));
    }

    /**
     * Lista las órdenes visibles al usuario autenticado.
     *
     * @param authentication usuario autenticado
     * @return órdenes propias o alistadas disponibles
     */
    @GetMapping
    public ResponseEntity<List<OrdenLogisticaResponseDTO>> listar(
            final Authentication authentication) {
        Long usuarioId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(ordenService.listar(usuarioId, obtenerRol(authentication)));
    }

    /**
     * Obtiene una orden visible al usuario autenticado.
     *
     * @param id identificador de orden
     * @param authentication usuario autenticado
     * @return orden solicitada
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrdenLogisticaResponseDTO> obtener(
            @PathVariable final Long id, final Authentication authentication) {
        Long usuarioId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(
                ordenService.obtener(id, usuarioId, obtenerRol(authentication)));
    }

    /**
     * Marca como alistada una orden perteneciente al agricultor autenticado.
     *
     * @param id identificador de orden
     * @param authentication agricultor autenticado
     * @return orden actualizada
     */
    @PatchMapping("/{id}/alistado")
    public ResponseEntity<OrdenLogisticaResponseDTO> marcarAlistada(
            @PathVariable final Long id, final Authentication authentication) {
        Long agricultorId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(ordenService.marcarAlistada(id, agricultorId));
    }

    /**
     * Asigna una orden alistada a un vehículo y programa su fecha de salida.
     *
     * @param id identificador de orden
     * @param dto vehículo y fecha de salida
     * @param authentication usuario autenticado
     * @return orden En Ruta con guía de despacho
     */
    @PostMapping("/{id}/ruta")
    public ResponseEntity<OrdenLogisticaResponseDTO> asignarRuta(
            @PathVariable final Long id,
            @Valid @RequestBody final AsignacionRutaRequestDTO dto,
            final Authentication authentication) {
        Long usuarioId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(ordenService.asignarRuta(
                id, dto, usuarioId, obtenerRol(authentication)));
    }

    private RolUsuario obtenerRol(final Authentication authentication) {
        boolean esAgricultor = authentication.getAuthorities().stream()
                .anyMatch(authority -> AUTHORITY_AGRICULTOR.equals(authority.getAuthority()));
        if (esAgricultor) {
            return RolUsuario.AGRICULTOR;
        }
        boolean esTransportista = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_TRANSPORTISTA".equals(authority.getAuthority()));
        if (esTransportista) {
            return RolUsuario.TRANSPORTISTA;
        }
        throw new AccessDeniedException(
                "Solo agricultores y transportistas pueden gestionar órdenes logísticas");
    }
}
