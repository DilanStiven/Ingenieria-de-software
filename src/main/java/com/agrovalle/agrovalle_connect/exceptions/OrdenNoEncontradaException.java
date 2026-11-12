package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que la orden logística solicitada no existe.
 */
public class OrdenNoEncontradaException extends RuntimeException {

    /**
     * Crea la excepción para una orden inexistente.
     *
     * @param id identificador de la orden
     */
    public OrdenNoEncontradaException(final Long id) {
        super("No se encontró la orden logística con ID: " + id);
    }
}
