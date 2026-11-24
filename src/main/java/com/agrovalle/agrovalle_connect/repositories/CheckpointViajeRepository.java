package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.CheckpointViaje;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Acceso a persistencia de checkpoints de viaje.
 */
public interface CheckpointViajeRepository extends JpaRepository<CheckpointViaje, Long> {

    List<CheckpointViaje> findByOrdenLogisticaIdOrderByFechaHoraAsc(Long ordenLogisticaId);
}
