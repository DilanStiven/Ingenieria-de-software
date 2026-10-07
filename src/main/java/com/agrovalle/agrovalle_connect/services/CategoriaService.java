package com.agrovalle.agrovalle_connect.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.agrovalle.agrovalle_connect.models.Categoria;

/**
 * Servicio para consultar las categorías disponibles.
 */
@Service
public class CategoriaService {

    private static final List<Categoria> CATEGORIAS =
            List.of(new Categoria("Frutas"), new Categoria("Granos"));

    /**
     * Obtiene las categorías disponibles para filtrar productos.
     *
     * @return categorías disponibles
     */
    public List<String> listar() {
         return CATEGORIAS.stream().map(Categoria::getNombre).toList();
    }
}
