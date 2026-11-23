package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.CalificacionTransporte;
import java.time.Instant;

/**
 * Datos devueltos al registrar una calificación de transporte.
 */
public class CalificacionTransporteResponseDTO {

    private final Long id;
    private final Long viajeId;
    private final Long conductorId;
    private final Short puntualidad;
    private final String estadoCajas;
    private final Instant fechaCalificacion;

    /**
     * Construye la respuesta a partir de la calificación persistida.
     *
     * @param calificacion calificación registrada
     */
    public CalificacionTransporteResponseDTO(final CalificacionTransporte calificacion) {
        this.id = calificacion.getId();
        this.viajeId = calificacion.getViaje().getId();
        this.conductorId = calificacion.getConductor().getId();
        this.puntualidad = calificacion.getPuntualidad();
        this.estadoCajas = calificacion.getEstadoCajas();
        this.fechaCalificacion = calificacion.getFechaCalificacion();
    }

    /**
     * Obtiene el identificador de la calificación.
     *
     * @return identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el identificador del viaje calificado.
     *
     * @return identificador del viaje
     */
    public Long getViajeId() {
        return viajeId;
    }

    /**
     * Obtiene el identificador del conductor calificado.
     *
     * @return identificador del conductor
     */
    public Long getConductorId() {
        return conductorId;
    }

    /**
     * Obtiene la puntuación de puntualidad.
     *
     * @return puntuación
     */
    public Short getPuntualidad() {
        return puntualidad;
    }

    /**
     * Obtiene el estado reportado de las cajas.
     *
     * @return estado de las cajas
     */
    public String getEstadoCajas() {
        return estadoCajas;
    }

    /**
     * Obtiene la fecha de calificación.
     *
     * @return fecha de calificación
     */
    public Instant getFechaCalificacion() {
        return fechaCalificacion;
    }
}
