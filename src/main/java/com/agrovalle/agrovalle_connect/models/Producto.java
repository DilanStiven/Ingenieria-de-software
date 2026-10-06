package com.agrovalle.agrovalle_connect.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa un producto publicado en AgroValle Connect.
 */
@Entity
@Table(name = "productos")
public class Producto {

    private static final int NOMBRE_LONGITUD = 100;
    private static final int CATEGORIA_LONGITUD = 50;
    private static final int MUNICIPIO_LONGITUD = 100;
    private static final int CANTIDAD_PRECISION = 10;
    private static final int PRECIO_PRECISION = 12;
    private static final int VALOR_ESCALA = 2;
    private static final int ESTADO_LONGITUD = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = NOMBRE_LONGITUD)
    private String nombre;

    @Column(nullable = false, length = CATEGORIA_LONGITUD)
    private String categoria;

    @Column(nullable = false, length = MUNICIPIO_LONGITUD)
    private String municipio;

    @Column(nullable = false, precision = CANTIDAD_PRECISION, scale = VALOR_ESCALA)
    private BigDecimal cantidad;

    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;

    @Column(name = "precio_unitario", nullable = false, precision = PRECIO_PRECISION, scale = VALOR_ESCALA)
    private BigDecimal precioUnitario;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(length = ESTADO_LONGITUD)
    private String estado;

    /**
     * Obtiene el identificador del producto.
     *
     * @return identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     *
     * @param idValue identificador
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     *
     * @param nombreValue nombre del producto
     */
    public void setNombre(final String nombreValue) {
        this.nombre = nombreValue;
    }

    /**
     * Obtiene la categoría del producto.
     *
     * @return categoría
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Establece la categoría del producto.
     *
     * @param categoriaValue categoría
     */
    public void setCategoria(final String categoriaValue) {
        this.categoria = categoriaValue;
    }

    /**
     * Obtiene el municipio del producto.
     *
     * @return municipio
     */
    public String getMunicipio() {
        return municipio;
    }

    /**
     * Establece el municipio del producto.
     *
     * @param municipioValue municipio
     */
    public void setMunicipio(final String municipioValue) {
        this.municipio = municipioValue;
    }

    /**
     * Obtiene la cantidad disponible.
     *
     * @return cantidad
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad disponible.
     *
     * @param cantidadValue cantidad
     */
    public void setCantidad(final BigDecimal cantidadValue) {
        this.cantidad = cantidadValue;
    }

    /**
     * Obtiene la fecha de cosecha.
     *
     * @return fecha de cosecha
     */
    public LocalDate getFechaCosecha() {
        return fechaCosecha;
    }

    /**
     * Establece la fecha de cosecha.
     *
     * @param fechaCosechaValue fecha de cosecha
     */
    public void setFechaCosecha(final LocalDate fechaCosechaValue) {
        this.fechaCosecha = fechaCosechaValue;
    }

    /**
     * Obtiene el precio unitario.
     *
     * @return precio unitario
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario.
     *
     * @param precioUnitarioValue precio unitario
     */
    public void setPrecioUnitario(final BigDecimal precioUnitarioValue) {
        this.precioUnitario = precioUnitarioValue;
    }

    /**
     * Obtiene el identificador del usuario propietario.
     *
     * @return identificador del usuario
     */
    public Long getUsuarioId() {
        return usuarioId;
    }

    /**
     * Establece el identificador del usuario propietario.
     *
     * @param usuarioIdValue identificador del usuario
     */
    public void setUsuarioId(final Long usuarioIdValue) {
        this.usuarioId = usuarioIdValue;
    }

    /**
     * Obtiene el estado del producto.
     *
     * @return estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Establece el estado del producto.
     *
     * @param estadoValue estado
     */
    public void setEstado(final String estadoValue) {
        this.estado = estadoValue;
    }
}
