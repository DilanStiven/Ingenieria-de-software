package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que un pago no puede procesarse debido al estado actual del pedido.
 */
public class PagoInvalidoException extends RuntimeException {

    public PagoInvalidoException(final Long pedidoId, final String estado) {
        super("El pedido " + pedidoId + " no puede pagarse desde el estado " + estado);
    }
}
