package com.agrovalle.agrovalle_connect.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Error de validación o estado al registrar una calificación de transporte.
 */
public class CalificacionTransporteException extends RuntimeException {

    private final HttpStatus status;

    /**
     * Crea el error con el estado HTTP correspondiente.
     *
     * @param mensaje detalle del error
     * @param statusValue estado HTTP
     */
    public CalificacionTransporteException(
            final String mensaje, final HttpStatus statusValue) {
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
