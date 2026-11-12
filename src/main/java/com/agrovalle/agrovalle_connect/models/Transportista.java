package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Entidad que representa un transportista registrado en AgroValle Connect.
 */
@Entity
@Table(name = "transportista", uniqueConstraints = {
        @UniqueConstraint(name = "uk_transportista_cedula", columnNames = "cedula")
})
public class Transportista {

    private static final int CEDULA_LONGITUD_MAXIMA = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = CEDULA_LONGITUD_MAXIMA)
    private String cedula;

    /**
     * Obtiene el identificador del transportista.
     *
     * @return identificador del transportista
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del transportista.
     *
     * @param idValue identificador del transportista
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el nombre del transportista.
     *
     * @return nombre del transportista
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del transportista.
     *
     * @param nombreValue nombre del transportista
     */
    public void setNombre(final String nombreValue) {
        this.nombre = nombreValue;
    }

    /**
     * Obtiene la cédula del transportista.
     *
     * @return cédula del transportista
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cédula del transportista.
     *
     * @param cedulaValue cédula del transportista
     */
    public void setCedula(final String cedulaValue) {
        this.cedula = cedulaValue;
    }
}
