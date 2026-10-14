package com.agrovalle.agrovalle_connect.events;

import java.math.BigDecimal;

/**
 * Evento de dominio emitido cuando un agricultor publica un producto (Patrón Observer - GoF).
 */
public class ProductoPublicadoEvent {

    private final Long productoId;
    private final String nombre;
    private final String municipio;
    private final BigDecimal cantidad;

    /**
     * Crea el evento con los datos clave del producto publicado.
     *
     * @param productoId identificador del producto
     * @param nombre     nombre del producto
     * @param municipio  municipio de origen
     * @param cantidad   cantidad en kg
     */
    public ProductoPublicadoEvent(final Long productoId, final String nombre,
            final String municipio, final BigDecimal cantidad) {
        this.productoId = productoId;
        this.nombre = nombre;
        this.municipio = municipio;
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public String getNombre() {
        return nombre;
    }

    public String getMunicipio() {
        return municipio;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }
}
