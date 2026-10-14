package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Excepción lanzada cuando el municipio solicitado no pertenece al catálogo permitido.
 */
public class MunicipioNoValidoException extends RuntimeException {

    /**
     * Crea la excepción con el municipio rechazado.
     *
     * @param municipio municipio no válido
     */
    public MunicipioNoValidoException(final String municipio) {
        super("Municipio no válido: " + municipio);
    }
}
