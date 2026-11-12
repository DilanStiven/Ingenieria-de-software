package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que el vehículo solicitado no existe.
 */
public class VehiculoNoEncontradoException extends RuntimeException {

    /**
     * Crea la excepción para un vehículo inexistente.
     *
     * @param id identificador del vehículo
     */
    public VehiculoNoEncontradoException(final Long id) {
        super("No se encontró el vehículo con ID: " + id);
    }
}
