package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.ProductoDTO;
import com.agrovalle.agrovalle_connect.dtos.ProductoRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.ProductoResponseDTO;
import com.agrovalle.agrovalle_connect.services.ProductoService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para publicación y consulta de productos agrícolas.
 */
@RestController
@RequestMapping("/api/v1")
public class ProductoController {

    private final ProductoService productoService;

    /**
     * Crea el controlador con el servicio de productos.
     *
     * @param productoService servicio de lógica de productos
     */
    public ProductoController(final ProductoService productoService) {
        this.productoService = productoService;
    }

    /**
     * Publica una nueva cosecha. Requiere autenticación JWT (HU-02).
     * El agricultor es identificado por el token; retorna 401 si no hay token.
     *
     * @param dto  datos del producto a publicar
     * @param auth autenticación del agricultor (inyectada por Spring Security)
     * @return producto creado con HTTP 201
     */
    @PostMapping("/productos")
    public ResponseEntity<ProductoResponseDTO> publicar(
            @Valid @RequestBody final ProductoRequestDTO dto,
            final Authentication auth) {
        Long agricultorId = Long.parseLong(auth.getName());
        ProductoResponseDTO response = productoService.publicar(dto, agricultorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Consulta el catálogo de productos activos con filtros (HU-04). Ruta pública.
     *
     * @param municipio municipio de origen (obligatorio)
     * @param categoria categoría del producto (opcional)
     * @return lista de productos que coinciden con los filtros
     */
    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> filtrar(
            @RequestParam final String municipio,
            @RequestParam(required = false) final String categoria) {
        return ResponseEntity.ok(productoService.filtrarProductos(municipio, categoria));
    }
}
