package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Se lanza cuando se intenta registrar una cédula que ya existe.
 */
public class CedulaDuplicadaException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea la excepción con un mensaje que incluye la cédula.
     *
     * @param cedula cédula duplicada
     */
    public CedulaDuplicadaException(String cedula) {
        super("Ya existe un agricultor registrado con la cédula " + cedula);
    }
}
