package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa una cosecha publicada por un agricultor en AgroValle Connect.
 */
@Entity
@Table(name = "productos")
public class Producto {

    /** Longitud máxima para campos de texto cortos. */
    private static final int CAMPO_CORTO = 50;

    /** Longitud máxima para campos de texto largo. */
    private static final int CAMPO_LARGO = 100;

    /** Precisión para el campo cantidad. */
    private static final int PRECISION_CANTIDAD = 10;

    /** Escala decimal estándar para montos y cantidades. */
    private static final int ESCALA_DECIMAL = 2;

    /** Precisión para el campo precio unitario. */
    private static final int PRECISION_PRECIO = 12;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = CAMPO_LARGO)
    private String tipo;

    @Column(nullable = false, length = CAMPO_CORTO)
    private String categoria;

    @Column(nullable = false, length = CAMPO_CORTO)
    private String municipio;

    @Column(nullable = false, precision = PRECISION_CANTIDAD, scale = ESCALA_DECIMAL)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = PRECISION_PRECIO, scale = ESCALA_DECIMAL)
    private BigDecimal precioUnitario;

    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;

    @Column(nullable = false, length = CAMPO_CORTO)
    private String estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agricultor_id", nullable = false)
    private Agricultor agricultor;

    /**
     * Obtiene el identificador del producto.
     *
     * @return id del producto
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param id identificador
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo del producto.
     *
     * @return tipo del producto
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del producto.
     *
     * @param tipo tipo del producto
     */
    public void setTipo(final String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la categoría del producto.
     *
     * @return categoría del producto
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto.
     *
     * @param categoria categoría del producto
     */
    public void setCategoria(final String categoria) {
        this.categoria = categoria;
    }

    /**
     * Obtiene el municipio de origen del producto.
     *
     * @return municipio de origen
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Establece el municipio de origen del producto.
     *
     * @param municipio municipio de origen
     */
    public void setMunicipio(final String municipio) {
        this.municipio = municipio;
    }

    /**
     * Obtiene la cantidad disponible en kg.
     *
     * @return cantidad en kg
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad disponible en kg.
     *
     * @param cantidad cantidad en kg
     */
    public void setCantidad(final BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio unitario por kg.
     *
     * @return precio unitario
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario por kg.
     *
     * @param precioUnitario precio unitario
     */
    public void setPrecioUnitario(final BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Obtiene la fecha de cosecha del producto.
     *
     * @return fecha de cosecha
     */
    public LocalDate getFechaCosecha() {
        return fechaCosecha;
    }

    /**
     * Establece la fecha de cosecha del producto.
     *
     * @param fechaCosecha fecha de cosecha
     */
    public void setFechaCosecha(final LocalDate fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }

    /**
     * Obtiene el estado del producto.
     *
     * @return estado (ACTIVO, INACTIVO)
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del producto.
     *
     * @param estado estado del producto
     */
    public void setEstado(final String estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el agricultor propietario del producto.
     *
     * @return agricultor propietario
     */
    public Agricultor getAgricultor() {
        return agricultor;
    }

    /**
     * Establece el agricultor propietario del producto.
     *
     * @param agricultor agricultor propietario
     */
    public void setAgricultor(final Agricultor agricultor) {
        this.agricultor = agricultor;
    }
}
