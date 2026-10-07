package com.agrovalle.agrovalle_connect.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.services.ProductoService;

/**
 * Controlador REST para consultar productos publicados.
 */
@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Crea el controlador con su servicio.
     *
     * @param productoService servicio de productos
     */
    public ProductoController(final ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Lista productos con filtros opcionales por municipio y categoría.
     *
     * @param municipio municipio por el que filtrar
     * @param categoria categoría por la que filtrar
     * @return productos encontrados con código HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<Producto>> listar(
            @RequestParam(required = false) final String municipio,
            @RequestParam(required = false) final String categoria) {
        return ResponseEntity.ok(productoService.filtrar(municipio, categoria, null));
    }
}
