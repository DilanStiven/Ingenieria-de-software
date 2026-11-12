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
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Orden logística de carga que puede ser alistada y asignada a una ruta.
 */
@Entity
@Table(name = "orden_logistica",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_orden_logistica_guia", columnNames = "guia_despacho"),
        indexes = {
                @Index(name = "idx_orden_logistica_estado", columnList = "estado"),
                @Index(name = "idx_orden_logistica_agricultor", columnList = "agricultor_id")
        },
        check = {
                @CheckConstraint(
                        name = "ck_orden_logistica_peso_positivo",
                        constraint = "peso_kg > 0"),
                @CheckConstraint(
                        name = "ck_orden_logistica_ruta_completa",
                        constraint = "((estado IN ('PENDIENTE_ALISTAMIENTO', 'ALISTADO')"
                                + " AND vehiculo_id IS NULL AND fecha_salida IS NULL"
                                + " AND guia_despacho IS NULL)"
                                + " OR (estado = 'EN_RUTA' AND vehiculo_id IS NOT NULL"
                                + " AND fecha_salida IS NOT NULL AND guia_despacho IS NOT NULL))")
        })
public class OrdenLogistica {

    private static final int PRODUCTO_LONGITUD_MAXIMA = 100;
    private static final int DESTINO_LONGITUD_MAXIMA = 100;
    private static final int ESTADO_LONGITUD_MAXIMA = 30;
    private static final int GUIA_LONGITUD_MAXIMA = 40;
    private static final int PRECISION_PESO = 12;
    private static final int ESCALA_PESO = 2;

    /**
     * Estados del ciclo logístico soportado por HU-07.
     */
    public enum Estado {
        PENDIENTE_ALISTAMIENTO,
        ALISTADO,
        EN_RUTA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = PRODUCTO_LONGITUD_MAXIMA)
    private String producto;

    @Column(name = "peso_kg", nullable = false, precision = PRECISION_PESO, scale = ESCALA_PESO)
    private BigDecimal pesoKg;

    @Column(nullable = false, length = DESTINO_LONGITUD_MAXIMA)
    private String destino;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = ESTADO_LONGITUD_MAXIMA)
    private Estado estado = Estado.PENDIENTE_ALISTAMIENTO;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @Column(name = "guia_despacho", length = GUIA_LONGITUD_MAXIMA)
    private String guiaDespacho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agricultor_id", nullable = false)
    private Agricultor agricultor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    /**
     * Establece automáticamente la fecha de creación.
     */
    @jakarta.persistence.PrePersist
    protected void alCrear() {
        if (fechaCreacion == null) {
            fechaCreacion = Instant.now();
        }
    }

    /**
     * Obtiene el identificador de la orden.
     *
     * @return id de la orden
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la orden.
     *
     * @param idValue identificador
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el nombre del producto transportado.
     *
     * @return nombre del producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * Establece el nombre del producto transportado.
     *
     * @param productoValue nombre del producto
     */
    public void setProducto(final String productoValue) {
        this.producto = productoValue;
    }

    /**
     * Obtiene el peso total de la carga.
     *
     * @return peso en kg
     */
    public BigDecimal getPesoKg() {
        return pesoKg;
    }

    /**
     * Establece el peso total de la carga.
     *
     * @param pesoKgValue peso en kg
     */
    public void setPesoKg(final BigDecimal pesoKgValue) {
        this.pesoKg = pesoKgValue;
    }

    /**
     * Obtiene el destino de despacho.
     *
     * @return destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Establece el destino de despacho.
     *
     * @param destinoValue destino
     */
    public void setDestino(final String destinoValue) {
        this.destino = destinoValue;
    }

    /**
     * Obtiene el estado logístico.
     *
     * @return estado actual
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el estado logístico.
     *
     * @param estadoValue estado actual
     */
    public void setEstado(final Estado estadoValue) {
        this.estado = estadoValue;
    }

    /**
     * Obtiene la fecha de creación.
     *
     * @return instante de creación
     */
    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación.
     *
     * @param fechaCreacionValue instante de creación
     */
    public void setFechaCreacion(final Instant fechaCreacionValue) {
        this.fechaCreacion = fechaCreacionValue;
    }

    /**
     * Obtiene la fecha programada de salida.
     *
     * @return fecha de salida o null si no se ha asignado ruta
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Establece la fecha programada de salida.
     *
     * @param fechaSalidaValue fecha de salida
     */
    public void setFechaSalida(final LocalDate fechaSalidaValue) {
        this.fechaSalida = fechaSalidaValue;
    }

    /**
     * Obtiene la guía de despacho generada para la ruta.
     *
     * @return guía o null si no se ha asignado ruta
     */
    public String getGuiaDespacho() {
        return guiaDespacho;
    }

    /**
     * Establece la guía de despacho.
     *
     * @param guiaDespachoValue guía única
     */
    public void setGuiaDespacho(final String guiaDespachoValue) {
        this.guiaDespacho = guiaDespachoValue;
    }

    /**
     * Obtiene el agricultor que registró la orden.
     *
     * @return agricultor propietario
     */
    public Agricultor getAgricultor() {
        return agricultor;
    }

    /**
     * Asocia el agricultor propietario.
     *
     * @param agricultorValue agricultor
     */
    public void setAgricultor(final Agricultor agricultorValue) {
        this.agricultor = agricultorValue;
    }

    /**
     * Obtiene el vehículo asignado.
     *
     * @return vehículo o null antes de asignar ruta
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * Asocia el vehículo asignado.
     *
     * @param vehiculoValue vehículo
     */
    public void setVehiculo(final Vehiculo vehiculoValue) {
        this.vehiculo = vehiculoValue;
    }
}
