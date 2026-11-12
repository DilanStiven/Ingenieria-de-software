package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que no hay transacciones recientes para calcular el precio promedio.
 */
public class PrecioNoDisponibleException extends RuntimeException {

    /**
     * Crea la excepción para un producto sin transacciones recientes.
     *
     * @param producto nombre del producto
     */
    public PrecioNoDisponibleException(final String producto) {
        super("No hay transacciones de precio para " + producto + " en las últimas 24 horas");
    }
}
