package com.agrovalle.agrovalle_connect.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrovalle.agrovalle_connect.services.ProductoService;

/**
 * Controlador REST para consultar los municipios disponibles.
 */
@RestController
@RequestMapping("/api/v1/municipios")
public class MunicipioController {

    private final ProductoService productoService;

    /**
     * Crea el controlador con el servicio de productos.
     *
     * @param productoService servicio que contiene el catálogo validado
     */
    public MunicipioController(final ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Lista los municipios permitidos para filtrar productos.
     *
     * @return municipios con código HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<String>> listar() {
        return ResponseEntity.ok(productoService.listarMunicipios());
    }
}
