package com.agrovalle.agrovalle_connect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrovalle.agrovalle_connect.models.Categoria;

/**
 * Repositorio para gestionar las categorías del catálogo.
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    /**
     * Indica si ya existe una categoría con el nombre indicado.
     *
     * @param nombre nombre de categoría a consultar
     * @return true si existe
     */
    boolean existsByNombre(String nombre);
}
