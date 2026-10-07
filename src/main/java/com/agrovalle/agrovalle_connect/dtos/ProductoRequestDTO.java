package com.agrovalle.agrovalle_connect.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Datos de entrada para publicar un producto agrícola (HU-02).
 */
public class ProductoRequestDTO {

    @NotBlank(message = "El tipo de producto es obligatorio")
    private String tipo;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor a cero")
    private BigDecimal cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    private BigDecimal precioUnitario;

    @NotNull(message = "La fecha de cosecha es obligatoria")
    private LocalDate fechaCosecha;

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
     * Obtiene la cantidad en kg.
     *
     * @return cantidad en kg
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad en kg.
     *
     * @param cantidad cantidad en kg
     */
    public void setCantidad(final BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio unitario.
     *
     * @return precio unitario por kg
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario.
     *
     * @param precioUnitario precio unitario por kg
     */
    public void setPrecioUnitario(final BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
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
     * @param fechaCosecha fecha de cosecha
     */
    public void setFechaCosecha(final LocalDate fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }
}
