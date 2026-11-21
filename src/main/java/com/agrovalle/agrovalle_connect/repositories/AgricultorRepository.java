package com.agrovalle.agrovalle_connect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

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

    /**
     * Busca un agricultor por correo sin distinguir mayúsculas.
     *
     * @param correo correo normalizado o proporcionado por el usuario
     * @return agricultor encontrado, si existe
     */
    Optional<Agricultor> findByCorreoIgnoreCase(String correo);

    /**
     * Indica si el correo ya pertenece a un agricultor.
     *
     * @param correo correo a consultar
     * @return true si existe
     */
    boolean existsByCorreoIgnoreCase(String correo);
}
