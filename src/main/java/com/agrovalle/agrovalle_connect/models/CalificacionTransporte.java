package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;

/**
 * Calificación del transporte registrada para un viaje.
 */
@Entity
@Table(name = "calificacion_transporte",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_calificacion_transporte_viaje",
                columnNames = "viaje_id"),
        indexes = @Index(
                name = "idx_calificacion_transporte_conductor",
                columnList = "conductor_id"))
public class CalificacionTransporte {

    private static final int ESTADO_CAJAS_LONGITUD_MAXIMA = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viaje_id", nullable = false)
    private OrdenLogistica viaje;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id", nullable = false)
    private Transportista conductor;

    @Column(nullable = false)
    private Short puntualidad;

    @Column(name = "estado_cajas", nullable = false, length = ESTADO_CAJAS_LONGITUD_MAXIMA)
    private String estadoCajas;

    @Column(name = "fecha_calificacion", nullable = false, updatable = false)
    private Instant fechaCalificacion;

    /**
     * Completa la fecha de calificación al persistir.
     */
    @PrePersist
    protected void alCrear() {
        if (fechaCalificacion == null) {
            fechaCalificacion = Instant.now();
        }
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
     * Establece el identificador de la calificación.
     *
     * @param idValue identificador
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el viaje calificado.
     *
     * @return viaje asociado
     */
    public OrdenLogistica getViaje() {
        return viaje;
    }

    /**
     * Asocia el viaje calificado.
     *
     * @param viajeValue viaje asociado
     */
    public void setViaje(final OrdenLogistica viajeValue) {
        this.viaje = viajeValue;
    }

    /**
     * Obtiene el conductor que realizó el viaje.
     *
     * @return conductor asociado
     */
    public Transportista getConductor() {
        return conductor;
    }

    /**
     * Asocia al conductor que realizó el viaje.
     *
     * @param conductorValue conductor asociado
     */
    public void setConductor(final Transportista conductorValue) {
        this.conductor = conductorValue;
    }

    /**
     * Obtiene la puntuación de puntualidad.
     *
     * @return puntuación de 1 a 5
     */
    public Short getPuntualidad() {
        return puntualidad;
    }

    /**
     * Establece la puntuación de puntualidad.
     *
     * @param puntualidadValue puntuación de 1 a 5
     */
    public void setPuntualidad(final Short puntualidadValue) {
        this.puntualidad = puntualidadValue;
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
     * Establece el estado reportado de las cajas.
     *
     * @param estadoCajasValue estado de las cajas
     */
    public void setEstadoCajas(final String estadoCajasValue) {
        this.estadoCajas = estadoCajasValue;
    }

    /**
     * Obtiene la fecha de creación de la calificación.
     *
     * @return fecha de calificación
     */
    public Instant getFechaCalificacion() {
        return fechaCalificacion;
    }

    /**
     * Establece la fecha de calificación.
     *
     * @param fechaCalificacionValue fecha de calificación
     */
    public void setFechaCalificacion(final Instant fechaCalificacionValue) {
        this.fechaCalificacion = fechaCalificacionValue;
    }
}
