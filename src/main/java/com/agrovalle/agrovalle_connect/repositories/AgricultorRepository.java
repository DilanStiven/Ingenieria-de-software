package com.agrovalle.agrovalle_connect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrovalle.agrovalle_connect.models.Agricultor;

/**
 * Repositorio para gestionar los agricultores.
 */
public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {

    /**
     * Indica si ya existe un agricultor con esa cédula.
     *
     * @param cedula cédula a consultar
     * @return true si existe
     */
    boolean existsByCedula(String cedula);
}
