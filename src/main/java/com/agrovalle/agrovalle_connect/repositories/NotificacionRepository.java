package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Notificacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de notificaciones de agricultores.
 */
@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    /**
     * Lista las notificaciones del agricultor desde la más reciente.
     *
     * @param agricultorId identificador del destinatario
     * @return notificaciones del agricultor
     */
    List<Notificacion> findByAgricultorIdOrderByFechaCreacionDesc(Long agricultorId);
}
