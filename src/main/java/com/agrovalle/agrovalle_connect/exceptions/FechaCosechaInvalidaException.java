package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Se lanza cuando la fecha de cosecha es anterior a la fecha actual.
 */
public class FechaCosechaInvalidaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción con el mensaje indicado.
     *
     * @param mensaje descripción del error
     */
    public FechaCosechaInvalidaException(final String mensaje) {
        super(mensaje);
    }
}
