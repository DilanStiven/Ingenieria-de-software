package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que el pedido solicitado no existe.
 */
public class PedidoNoEncontradoException extends RuntimeException {

    public PedidoNoEncontradoException(final Long id) {
        super("No se encontró el pedido con ID: " + id);
    }
}
