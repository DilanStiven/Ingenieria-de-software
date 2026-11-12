package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para gestionar transportistas.
 */
public interface TransportistaRepository extends JpaRepository<Transportista, Long> {

    /**
     * Indica si ya existe un transportista con esa cédula.
     *
     * @param cedula cédula a consultar
     * @return true si existe
     */
    boolean existsByCedula(String cedula);
}
