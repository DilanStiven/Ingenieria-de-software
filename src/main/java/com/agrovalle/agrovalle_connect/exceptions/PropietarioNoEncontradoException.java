package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que el propietario asociado al token no existe.
 */
public class PropietarioNoEncontradoException extends RuntimeException {

    /**
     * Crea la excepción para un propietario inexistente.
     *
     * @param tipo tipo de propietario
     * @param id identificador del propietario
     */
    public PropietarioNoEncontradoException(final String tipo, final Long id) {
        super("No se encontró el " + tipo + " autenticado con ID: " + id);
    }
}
