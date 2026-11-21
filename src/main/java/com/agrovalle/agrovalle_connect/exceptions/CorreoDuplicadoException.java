package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Se lanza cuando el correo ya pertenece a un agricultor registrado.
 */
public class CorreoDuplicadoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción para un correo que ya está registrado.
     */
    public CorreoDuplicadoException() {
        super("Ya existe un agricultor registrado con ese correo");
    }
}
