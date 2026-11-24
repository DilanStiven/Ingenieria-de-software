package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.CheckpointViaje;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import java.time.Instant;

/**
 * Resultado de un checkpoint registrado durante un viaje.
 */
public class CheckpointViajeResponseDTO {

    private final Long id;
    private final Long viajeId;
    private final OrdenLogistica.Estado estado;
    private final String ubicacion;
    private final Instant fechaHora;

    public CheckpointViajeResponseDTO(final CheckpointViaje checkpoint) {
        this.id = checkpoint.getId();
        this.viajeId = checkpoint.getOrdenLogistica().getId();
        this.estado = checkpoint.getEstado();
        this.ubicacion = checkpoint.getUbicacion();
        this.fechaHora = checkpoint.getFechaHora();
    }

    public Long getId() {
        return id;
    }

    public Long getViajeId() {
        return viajeId;
    }

    public OrdenLogistica.Estado getEstado() {
        return estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Instant getFechaHora() {
        return fechaHora;
    }
}
