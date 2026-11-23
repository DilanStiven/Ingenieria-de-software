package com.agrovalle.agrovalle_connect.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Datos enviados para calificar un viaje.
 */
public class CalificacionTransporteRequestDTO {

    private static final int PUNTUACION_MINIMA = 1;
    private static final int PUNTUACION_MAXIMA = 5;
    private static final int ESTADO_CAJAS_LONGITUD_MAXIMA = 30;

    @NotNull(message = "La puntualidad es obligatoria")
    @Min(value = PUNTUACION_MINIMA, message = "La puntualidad debe calificarse entre 1 y 5")
    @Max(value = PUNTUACION_MAXIMA, message = "La puntualidad debe calificarse entre 1 y 5")
    private Integer puntualidad;

    @JsonProperty("estado_cajas")
    @JsonAlias("estadoCajas")
    @NotBlank(message = "El estado de las cajas es obligatorio")
    @Size(max = ESTADO_CAJAS_LONGITUD_MAXIMA,
            message = "El estado de las cajas no debe exceder 30 caracteres")
    private String estadoCajas;

    /**
     * Obtiene la puntuación de puntualidad.
     *
     * @return puntuación de 1 a 5
     */
    public Integer getPuntualidad() {
        return puntualidad;
    }

    /**
     * Establece la puntuación de puntualidad.
     *
     * @param puntualidadValue puntuación de 1 a 5
     */
    public void setPuntualidad(final Integer puntualidadValue) {
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
}
