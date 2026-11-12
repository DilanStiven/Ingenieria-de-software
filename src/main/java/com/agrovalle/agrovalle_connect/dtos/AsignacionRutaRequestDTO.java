package com.agrovalle.agrovalle_connect.dtos;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Datos requeridos para asignar una orden a una ruta.
 */
public class AsignacionRutaRequestDTO {

    @NotNull(message = "El identificador del vehículo es obligatorio")
    private Long vehiculoId;

    @NotNull(message = "La fecha de salida es obligatoria")
    @FutureOrPresent(message = "La fecha de salida no puede ser anterior a hoy")
    private LocalDate fechaSalida;

    /**
     * Obtiene el identificador del vehículo.
     *
     * @return identificador del vehículo
     */
    public Long getVehiculoId() {
        return vehiculoId;
    }

    /**
     * Establece el identificador del vehículo.
     *
     * @param vehiculoIdValue identificador del vehículo
     */
    public void setVehiculoId(final Long vehiculoIdValue) {
        this.vehiculoId = vehiculoIdValue;
    }

    /**
     * Obtiene la fecha programada.
     *
     * @return fecha de salida
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Establece la fecha programada.
     *
     * @param fechaSalidaValue fecha de salida
     */
    public void setFechaSalida(final LocalDate fechaSalidaValue) {
        this.fechaSalida = fechaSalidaValue;
    }
}
