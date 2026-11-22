package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Se lanza cuando las credenciales de inicio de sesión no son válidas.
 */
public class CredencialesInvalidasException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Crea un error genérico que no revela si el correo está registrado.
     */
    public CredencialesInvalidasException() {
        super("Correo o contraseña incorrectos");
    }
}
