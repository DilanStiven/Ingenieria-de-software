package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Entidad que representa un agricultor registrado en AgroValle Connect.
 */
@Entity
public class Agricultor {

    /**
     * Identificador único del agricultor.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del agricultor.
     */
    private String nombre;

    /**
     * Ubicación del agricultor en el Valle.
     */
    private String ubicacionValle;

    /**
     * Número de cédula del agricultor.
     */
    private String cedula;

    /**
     * Obtiene el identificador del agricultor.
     *
     * @return identificador del agricultor
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del agricultor.
     *
     * @param idValue identificador del agricultor
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el nombre del agricultor.
     *
     * @return nombre del agricultor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del agricultor.
     *
     * @param nombreValue nombre del agricultor
     */
    public void setNombre(final String nombreValue) {
        this.nombre = nombreValue;
    }

    /**
     * Obtiene la ubicación del agricultor.
     *
     * @return ubicación del agricultor
     */
    public String getUbicacionValle() {
        return ubicacionValle;
    }

    /**
     * Establece la ubicación del agricultor.
     *
     * @param ubicacionValue ubicación del agricultor
     */
    public void setUbicacionValle(final String ubicacionValue) {
        this.ubicacionValle = ubicacionValue;
    }

    /**
     * Obtiene la cédula del agricultor.
     *
     * @return cédula del agricultor
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cédula del agricultor.
     *
     * @param cedulaValue cédula del agricultor
     */
    public void setCedula(final String cedulaValue) {
        this.cedula = cedulaValue;
    }
}
