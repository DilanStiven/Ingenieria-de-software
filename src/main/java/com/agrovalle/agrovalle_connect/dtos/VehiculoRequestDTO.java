package com.agrovalle.agrovalle_connect.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

/**
 * Datos de entrada para registrar un vehículo de carga.
 */
public class VehiculoRequestDTO {

    private static final int LONGITUD_MAXIMA_TIPO = 50;

    @NotBlank(message = "La placa es obligatoria")
    @Pattern(regexp = "[A-Za-z]{3}-?[0-9]{3}",
            message = "La placa debe tener 3 letras y 3 dígitos")
    private String placa;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    @Size(max = LONGITUD_MAXIMA_TIPO,
            message = "El tipo de vehículo no puede superar 50 caracteres")
    private String tipo;

    @NotNull(message = "La capacidad en kg es obligatoria")
    @Positive(message = "La capacidad en kg debe ser mayor que cero")
    private Integer capacidadKg;

    /**
     * Obtiene la placa informada.
     *
     * @return placa del vehículo
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Define la placa.
     *
     * @param placaValue placa del vehículo
     */
    public void setPlaca(final String placaValue) {
        this.placa = placaValue;
    }

    /**
     * Obtiene el tipo de vehículo.
     *
     * @return tipo de vehículo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Define el tipo de vehículo.
     *
     * @param tipoValue tipo de vehículo
     */
    public void setTipo(final String tipoValue) {
        this.tipo = tipoValue;
    }

    /**
     * Obtiene la capacidad en kilogramos.
     *
     * @return capacidad en kg
     */
    public Integer getCapacidadKg() {
        return capacidadKg;
    }

    /**
     * Define la capacidad en kilogramos.
     *
     * @param capacidadKgValue capacidad en kg
     */
    public void setCapacidadKg(final Integer capacidadKgValue) {
        this.capacidadKg = capacidadKgValue;
    }
}
