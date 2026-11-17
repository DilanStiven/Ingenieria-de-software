package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que no existe una oferta activa para el producto solicitado.
 */
public class OfertaNoDisponibleException extends RuntimeException {

    /**
     * Crea la excepción para un producto sin oferta disponible.
     *
     * @param productoId identificador del producto
     */
    public OfertaNoDisponibleException(final Long productoId) {
        super("No hay una oferta activa para el producto con ID: " + productoId);
    }
}
