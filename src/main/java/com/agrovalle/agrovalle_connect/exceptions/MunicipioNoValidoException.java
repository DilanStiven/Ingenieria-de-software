package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Se lanza cuando el municipio solicitado no pertenece al Valle del Cauca soportado.
 */
public class MunicipioNoValidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción con el mensaje indicado.
     *
     * @param mensaje descripción del error
     */
    public MunicipioNoValidoException(final String mensaje) {
        super(mensaje);
    }
}
