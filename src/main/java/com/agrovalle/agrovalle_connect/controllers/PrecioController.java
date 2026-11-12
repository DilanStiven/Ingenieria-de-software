package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.PrecioPromedioResponseDTO;
import com.agrovalle.agrovalle_connect.services.PrecioService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para consultar precios regionales (HU-03).
 */
@RestController
@RequestMapping("/api/v1/precios")
@Validated
public class PrecioController {

    private static final int PRODUCTO_LONGITUD_MAXIMA = 100;

    private final PrecioService precioService;

    /**
     * Crea el controlador de precios.
     *
     * @param service servicio de consulta de precios
     */
    public PrecioController(final PrecioService service) {
        this.precioService = service;
    }

    /**
     * Consulta el promedio del producto en las últimas 24 horas.
     *
     * @param producto nombre del producto
     * @return promedio unitario en COP por kilogramo
     */
    @GetMapping("/promedio")
    public ResponseEntity<PrecioPromedioResponseDTO> obtenerPromedio(
            @RequestParam
            @NotBlank(message = "El producto es obligatorio")
            @Size(max = PRODUCTO_LONGITUD_MAXIMA,
                    message = "El nombre del producto no puede superar 100 caracteres")
            final String producto) {
        return ResponseEntity.ok(precioService.obtenerPromedio(producto));
    }
}
