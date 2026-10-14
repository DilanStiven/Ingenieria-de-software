package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.Producto;
import java.math.BigDecimal;

/**
 * DTO de consulta para listar y filtrar productos del catálogo (HU-04).
 */
public class ProductoDTO {

    private Long id;
    private String nombre;
    private String municipio;
    private String categoria;
    private BigDecimal cantidad;
    private BigDecimal precioUnitario;

    /**
     * Crea un DTO de producto con todos sus campos.
     *
     * @param id             identificador
     * @param nombre         nombre del producto
     * @param municipio      municipio de origen
     * @param categoria      categoría del producto
     * @param cantidad       cantidad disponible en kg
     * @param precioUnitario precio unitario por kg
     */
    public ProductoDTO(final Long id, final String nombre, final String municipio,
            final String categoria, final BigDecimal cantidad,
            final BigDecimal precioUnitario) {
        this.id = id;
        this.nombre = nombre;
        this.municipio = municipio;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    /**
     * Construye un ProductoDTO desde una entidad Producto.
     *
     * @param p entidad de producto
     * @return DTO de consulta
     */
    public static ProductoDTO desde(final Producto p) {
        return new ProductoDTO(
            p.getId(), p.getNombre(), p.getMunicipio(),
            p.getCategoria(), p.getCantidad(), p.getPrecioUnitario());
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el municipio de origen.
     *
     * @return municipio
     */
    public String getMunicipio() {
        return municipio;
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
     * Obtiene la cantidad disponible.
     *
     * @return cantidad en kg
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Obtiene el precio unitario.
     *
     * @return precio por kg
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }
}
