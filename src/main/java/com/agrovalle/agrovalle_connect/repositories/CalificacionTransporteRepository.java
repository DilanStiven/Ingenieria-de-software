package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.CalificacionTransporte;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio para calificaciones de transporte.
 */
public interface CalificacionTransporteRepository
        extends JpaRepository<CalificacionTransporte, Long> {

    /**
     * Comprueba si ya se calificó el viaje indicado.
     *
     * @param viajeId identificador del viaje
     * @return true si el viaje ya tiene una calificación
     */
    boolean existsByViajeId(Long viajeId);
}
