package com.agrovalle.agrovalle_connect.exceptions;

import java.math.BigDecimal;

/**
 * Indica que la cantidad solicitada supera las existencias disponibles.
 */
public class CantidadNoDisponibleException extends RuntimeException {

    public CantidadNoDisponibleException(
            final Long productoId, final BigDecimal solicitada, final BigDecimal disponible) {
        super("Cantidad no disponible para el producto " + productoId
                + ": solicitada " + solicitada + ", disponible " + disponible);
    }
}
