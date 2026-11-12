package com.agrovalle.agrovalle_connect.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Datos de entrada para el registro básico de un transportista.
 */
public class TransportistaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La cédula es obligatoria")
    @Pattern(regexp = "\\d{6,10}", message = "La cédula debe tener entre 6 y 10 dígitos")
    private String cedula;

    /**
     * Obtiene el nombre.
     *
     * @return nombre del transportista
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre.
     *
     * @param nombreValue nombre del transportista
     */
    public void setNombre(final String nombreValue) {
        this.nombre = nombreValue;
    }

    /**
     * Obtiene la cédula.
     *
     * @return cédula del transportista
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Define la cédula.
     *
     * @param cedulaValue cédula del transportista
     */
    public void setCedula(final String cedulaValue) {
        this.cedula = cedulaValue;
    }
}
