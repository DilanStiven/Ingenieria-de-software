package com.agrovalle.agrovalle_connect.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Datos mínimos para crear una orden logística.
 */
public class OrdenLogisticaRequestDTO {

    private static final int LONGITUD_MAXIMA_PRODUCTO = 100;
    private static final int LONGITUD_MAXIMA_DESTINO = 100;

    @NotBlank(message = "El producto es obligatorio")
    @Size(max = LONGITUD_MAXIMA_PRODUCTO,
            message = "El nombre del producto no puede superar 100 caracteres")
    private String producto;

    @NotNull(message = "El peso en kg es obligatorio")
    @DecimalMin(value = "0.01", message = "El peso en kg debe ser mayor que cero")
    private BigDecimal pesoKg;

    @NotBlank(message = "El destino es obligatorio")
    @Size(max = LONGITUD_MAXIMA_DESTINO,
            message = "El destino no puede superar 100 caracteres")
    private String destino;

    /**
     * Obtiene el producto de la carga.
     *
     * @return producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * Establece el producto de la carga.
     *
     * @param productoValue producto
     */
    public void setProducto(final String productoValue) {
        this.producto = productoValue;
    }

    /**
     * Obtiene el peso de la carga.
     *
     * @return peso en kg
     */
    public BigDecimal getPesoKg() {
        return pesoKg;
    }

    /**
     * Establece el peso de la carga.
     *
     * @param pesoKgValue peso en kg
     */
    public void setPesoKg(final BigDecimal pesoKgValue) {
        this.pesoKg = pesoKgValue;
    }

    /**
     * Obtiene el destino de la carga.
     *
     * @return destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Establece el destino de la carga.
     *
     * @param destinoValue destino
     */
    public void setDestino(final String destinoValue) {
        this.destino = destinoValue;
    }
}
