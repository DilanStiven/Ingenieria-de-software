package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.models.Pago;

/**
 * Resultado devuelto por una pasarela de pagos.
 */
public record ResultadoPago(
        Pago.Estado estado,
        String referencia,
        String mensaje) {
}
