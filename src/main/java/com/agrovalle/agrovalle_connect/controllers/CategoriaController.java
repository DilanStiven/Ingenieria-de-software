package com.agrovalle.agrovalle_connect.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrovalle.agrovalle_connect.services.CategoriaService;

/**
 * Controlador REST para consultar las categorías disponibles.
 */
@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    /**
     * Crea el controlador con su servicio de categorías.
     *
     * @param categoriaService servicio de categorías
     */
    public CategoriaController(final CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    /**
     * Lista las categorías disponibles.
     *
     * @return categorías con código HTTP 200
     */
    @GetMapping
    public ResponseEntity<List<String>> listar() {
        return ResponseEntity.ok(categoriaService.listar());
    }
}
