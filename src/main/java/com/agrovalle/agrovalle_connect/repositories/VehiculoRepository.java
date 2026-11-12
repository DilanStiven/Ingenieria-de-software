package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Vehiculo;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para gestionar vehículos de carga.
 */
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    /**
     * Indica si ya existe un vehículo con la placa normalizada.
     *
     * @param placa placa normalizada
     * @return true si existe
     */
    boolean existsByPlaca(String placa);

    /**
     * Obtiene y bloquea un vehículo durante la asignación de una ruta.
     *
     * @param id identificador del vehículo
     * @return vehículo bloqueado, si existe
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM Vehiculo v WHERE v.id = :id")
    Optional<Vehiculo> buscarPorIdParaActualizar(@Param("id") Long id);
}
