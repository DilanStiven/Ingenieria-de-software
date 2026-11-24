package com.agrovalle.agrovalle_connect.dtos;

import java.math.BigDecimal;

/**
 * Promedio de estrellas de un agricultor.
 */
public class ReputacionResponseDTO {

    private final Long agricultorId;
    private final BigDecimal calificacionPromedio;

    /**
     * Crea la respuesta de reputación.
     *
     * @param agricultorId identificador del agricultor
     * @param promedio promedio de estrellas
     */
    public ReputacionResponseDTO(
            final Long agricultorId, final BigDecimal promedio) {
        this.agricultorId = agricultorId;
        this.calificacionPromedio = promedio;
    }

    /**
     * Obtiene el identificador del agricultor.
     *
     * @return identificador del agricultor
     */
    public Long getAgricultorId() {
        return agricultorId;
    }

    /**
     * Obtiene el promedio de estrellas.
     *
     * @return promedio de calificaciones
     */
    public BigDecimal getCalificacionPromedio() {
        return calificacionPromedio;
    }
}
