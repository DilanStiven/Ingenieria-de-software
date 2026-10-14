package com.agrovalle.agrovalle_connect.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Datos de entrada para registrar un agricultor.
 */
public class AgricultorRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La ubicación en el Valle es obligatoria")
    private String ubicacionValle;

    @NotBlank(message = "La cédula es obligatoria")
    @Pattern(regexp = "\\d{6,10}", message = "La cédula debe tener entre 6 y 10 dígitos")
    private String cedula;

    /**
     * Obtiene el nombre.
     *
     * @return nombre del agricultor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre.
     *
     * @param nombre nombre del agricultor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la ubicación.
     *
     * @return ubicación en el Valle del Cauca
     */
    public String getUbicacionValle() {
        return ubicacionValle;
    }

    /**
     * Define la ubicación.
     *
     * @param ubicacionValle ubicación en el Valle del Cauca
     */
    public void setUbicacionValle(String ubicacionValle) {
        this.ubicacionValle = ubicacionValle;
    }

    /**
     * Obtiene la cédula.
     *
     * @return cédula del agricultor
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Define la cédula.
     *
     * @param cedula cédula del agricultor
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
