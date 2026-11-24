package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que no existe el agricultor solicitado.
 */
public class AgricultorNoEncontradoException extends RuntimeException {

    /**
     * Crea la excepción para un agricultor inexistente.
     *
     * @param id identificador del agricultor
     */
    public AgricultorNoEncontradoException(final Long id) {
        super("No se encontró el agricultor con ID: " + id);
    }
}
