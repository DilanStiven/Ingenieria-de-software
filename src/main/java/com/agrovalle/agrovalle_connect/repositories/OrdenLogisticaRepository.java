package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para órdenes logísticas.
 */
public interface OrdenLogisticaRepository extends JpaRepository<OrdenLogistica, Long> {

    /**
     * Obtiene una orden bloqueándola para evitar asignaciones simultáneas.
     *
     * @param id identificador de la orden
     * @return orden bloqueada, si existe
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM OrdenLogistica o WHERE o.id = :id")
    Optional<OrdenLogistica> buscarPorIdParaActualizar(@Param("id") Long id);

    /**
     * Lista las órdenes de un agricultor de más recientes a más antiguas.
     *
     * @param agricultorId identificador del agricultor
     * @return órdenes del agricultor
     */
    @Query("""
            SELECT o FROM OrdenLogistica o
            WHERE o.agricultor.id = :agricultorId
            ORDER BY o.fechaCreacion DESC
            """)
    List<OrdenLogistica> buscarPorAgricultor(
            @Param("agricultorId") Long agricultorId);

    /**
     * Lista las órdenes de un estado de más antiguas a más recientes.
     *
     * @param estado estado a consultar
     * @return órdenes en el estado indicado
     */
    List<OrdenLogistica> findByEstadoOrderByFechaCreacionAsc(OrdenLogistica.Estado estado);
}
