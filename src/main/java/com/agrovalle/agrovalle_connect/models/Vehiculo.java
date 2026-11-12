package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.CheckConstraint;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Vehículo de carga disponible para programar rutas.
 */
@Entity
@Table(name = "vehiculo",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_vehiculo_placa", columnNames = "placa"),
        indexes = {
                @Index(name = "idx_vehiculo_agricultor", columnList = "agricultor_id"),
                @Index(name = "idx_vehiculo_transportista", columnList = "transportista_id")
        },
        check = {
                @CheckConstraint(
                        name = "ck_vehiculo_capacidad_positiva",
                        constraint = "capacidad_kg > 0"),
                @CheckConstraint(
                        name = "ck_vehiculo_propietario_exclusivo",
                        constraint = "(agricultor_id IS NOT NULL AND transportista_id IS NULL)"
                                + " OR (agricultor_id IS NULL AND transportista_id IS NOT NULL)")
        })
public class Vehiculo {

    private static final int PLACA_LONGITUD_MAXIMA = 7;
    private static final int TIPO_LONGITUD_MAXIMA = 50;
    private static final int ESTADO_LONGITUD_MAXIMA = 20;

    /** Estado asignado al registrar un vehículo. */
    public enum Estado {
        DISPONIBLE,
        EN_RUTA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = PLACA_LONGITUD_MAXIMA)
    private String placa;

    @Column(nullable = false, length = TIPO_LONGITUD_MAXIMA)
    private String tipo;

    @Column(name = "capacidad_kg", nullable = false)
    private Integer capacidadKg;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = ESTADO_LONGITUD_MAXIMA)
    private Estado estado = Estado.DISPONIBLE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agricultor_id")
    private Agricultor agricultor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transportista_id")
    private Transportista transportista;

    /**
     * Obtiene el identificador del vehículo.
     *
     * @return identificador del vehículo
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del vehículo.
     *
     * @param idValue identificador del vehículo
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene la placa normalizada.
     *
     * @return placa del vehículo
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Establece la placa normalizada.
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
     * Establece el tipo de vehículo.
     *
     * @param tipoValue tipo de vehículo
     */
    public void setTipo(final String tipoValue) {
        this.tipo = tipoValue;
    }

    /**
     * Obtiene la capacidad de carga en kilogramos.
     *
     * @return capacidad en kg
     */
    public Integer getCapacidadKg() {
        return capacidadKg;
    }

    /**
     * Establece la capacidad de carga en kilogramos.
     *
     * @param capacidadKgValue capacidad en kg
     */
    public void setCapacidadKg(final Integer capacidadKgValue) {
        this.capacidadKg = capacidadKgValue;
    }

    /**
     * Obtiene el estado operativo del vehículo.
     *
     * @return estado del vehículo
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el estado operativo del vehículo.
     *
     * @param estadoValue estado del vehículo
     */
    public void setEstado(final Estado estadoValue) {
        this.estado = estadoValue;
    }

    /**
     * Obtiene el agricultor propietario, si aplica.
     *
     * @return agricultor propietario o null
     */
    public Agricultor getAgricultor() {
        return agricultor;
    }

    /**
     * Asocia el agricultor propietario.
     *
     * @param agricultorValue agricultor propietario
     */
    public void setAgricultor(final Agricultor agricultorValue) {
        this.agricultor = agricultorValue;
    }

    /**
     * Obtiene el transportista propietario, si aplica.
     *
     * @return transportista propietario o null
     */
    public Transportista getTransportista() {
        return transportista;
    }

    /**
     * Asocia el transportista propietario.
     *
     * @param transportistaValue transportista propietario
     */
    public void setTransportista(final Transportista transportistaValue) {
        this.transportista = transportistaValue;
    }
}
