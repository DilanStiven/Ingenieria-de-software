package com.agrovalle.agrovalle_connect.exceptions;

import org.springframework.http.HttpStatus;

public class ResenaInvalidaException extends RuntimeException {

    private final HttpStatus status;

    public ResenaInvalidaException(final String mensaje,
            final HttpStatus statusValue) {
        super(mensaje);
        this.status = statusValue;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
