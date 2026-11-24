package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Registro histórico de los puntos clave recorridos por una orden en viaje.
 */
@Entity
@Table(name = "checkpoint_viaje",
        indexes = @Index(name = "idx_checkpoint_viaje_orden_fecha",
                columnList = "orden_logistica_id, fecha_hora"))
public class CheckpointViaje {

    private static final int UBICACION_LONGITUD_MAXIMA = 100;
    private static final int ESTADO_LONGITUD_MAXIMA = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_logistica_id", nullable = false)
    private OrdenLogistica ordenLogistica;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = ESTADO_LONGITUD_MAXIMA)
    private OrdenLogistica.Estado estado;

    @Column(nullable = false, length = UBICACION_LONGITUD_MAXIMA)
    private String ubicacion;

    @Column(name = "fecha_hora", nullable = false, updatable = false)
    private Instant fechaHora;

    @PrePersist
    protected void alCrear() {
        if (fechaHora == null) {
            fechaHora = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long idValue) {
        this.id = idValue;
    }

    public OrdenLogistica getOrdenLogistica() {
        return ordenLogistica;
    }

    public void setOrdenLogistica(final OrdenLogistica ordenValue) {
        this.ordenLogistica = ordenValue;
    }

    public OrdenLogistica.Estado getEstado() {
        return estado;
    }

    public void setEstado(final OrdenLogistica.Estado estadoValue) {
        this.estado = estadoValue;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(final String ubicacionValue) {
        this.ubicacion = ubicacionValue;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(final Instant fechaHoraValue) {
        this.fechaHora = fechaHoraValue;
    }
}
