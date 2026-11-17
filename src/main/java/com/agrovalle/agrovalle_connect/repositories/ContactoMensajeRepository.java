package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.ContactoMensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar mensajes de contacto.
 */
@Repository
public interface ContactoMensajeRepository extends JpaRepository<ContactoMensaje, Long> {
}
