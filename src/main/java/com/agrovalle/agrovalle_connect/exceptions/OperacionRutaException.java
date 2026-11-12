package com.agrovalle.agrovalle_connect.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Error de negocio al preparar una orden o asignar una ruta.
 */
public class OperacionRutaException extends RuntimeException {

    private final HttpStatus status;

    /**
     * Crea un error de operación con su estado HTTP esperado.
     *
     * @param mensaje detalle del error
     * @param statusValue estado HTTP
     */
    public OperacionRutaException(final String mensaje, final HttpStatus statusValue) {
        super(mensaje);
        this.status = statusValue;
    }

    /**
     * Obtiene el estado HTTP asociado.
     *
     * @return estado HTTP
     */
    public HttpStatus getStatus() {
        return status;
    }
}
