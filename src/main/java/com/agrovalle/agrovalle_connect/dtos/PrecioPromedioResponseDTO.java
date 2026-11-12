package com.agrovalle.agrovalle_connect.dtos;

import java.math.BigDecimal;

/**
 * Promedio de precio observado para un producto en pesos colombianos.
 */
public class PrecioPromedioResponseDTO {

    private final String producto;
    private final BigDecimal precioPromedio;
    private final String moneda;
    private final String unidadPrecio;
    private final long cantidadTransacciones;

    /**
     * Crea la respuesta del precio promedio.
     *
     * @param productoValue nombre del producto
     * @param precioPromedioValue promedio unitario
     * @param monedaValue moneda del promedio
     * @param unidadPrecioValue unidad del precio unitario
     * @param cantidadTransaccionesValue transacciones consideradas
     */
    public PrecioPromedioResponseDTO(final String productoValue,
            final BigDecimal precioPromedioValue, final String monedaValue,
            final String unidadPrecioValue, final long cantidadTransaccionesValue) {
        this.producto = productoValue;
        this.precioPromedio = precioPromedioValue;
        this.moneda = monedaValue;
        this.unidadPrecio = unidadPrecioValue;
        this.cantidadTransacciones = cantidadTransaccionesValue;
    }

    /**
     * Obtiene el producto consultado.
     *
     * @return nombre del producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * Obtiene el promedio unitario calculado.
     *
     * @return promedio en pesos colombianos
     */
    public BigDecimal getPrecioPromedio() {
        return precioPromedio;
    }

    /**
     * Obtiene la moneda.
     *
     * @return código ISO de la moneda
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * Obtiene la unidad usada en el precio.
     *
     * @return unidad del precio
     */
    public String getUnidadPrecio() {
        return unidadPrecio;
    }

    /**
     * Obtiene la cantidad de transacciones consideradas.
     *
     * @return cantidad de transacciones
     */
    public long getCantidadTransacciones() {
        return cantidadTransacciones;
    }
}
