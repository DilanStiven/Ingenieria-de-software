package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Precio;
import java.math.BigDecimal;
import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio para consultar precios de transacciones completadas.
 */
public interface PrecioRepository extends JpaRepository<Precio, Long> {

    /**
     * Proyección tipada del agregado de precios.
     */
    interface PromedioPrecio {

        /**
         * Obtiene el promedio unitario.
         *
         * @return promedio o null si no hay registros
         */
        BigDecimal getPromedio();

        /**
         * Obtiene el número de transacciones.
         *
         * @return cantidad de transacciones
         */
        Long getCantidad();
    }

    /**
     * Calcula el promedio y el número de transacciones dentro del intervalo indicado.
     *
     * @param producto nombre del producto, sin distinguir mayúsculas
     * @param desde inicio inclusivo del intervalo
     * @param hasta fin inclusivo del intervalo
     * @return promedio y cantidad calculados en una sola consulta
     */
    @Query("""
            SELECT AVG(p.precioUnitario) AS promedio, COUNT(p) AS cantidad
            FROM Precio p
            WHERE LOWER(p.producto) = LOWER(:producto)
              AND p.fechaTransaccion >= :desde
              AND p.fechaTransaccion <= :hasta
            """)
    PromedioPrecio calcularPromedio(
            @Param("producto") String producto,
            @Param("desde") Instant desde,
            @Param("hasta") Instant hasta);
}
