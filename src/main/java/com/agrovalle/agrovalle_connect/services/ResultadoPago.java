package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.models.Pago;

/**
 * Resultado devuelto por una pasarela de pagos.
 *
 * @param estado estado de la transacción
 * @param referencia referencia asignada por la pasarela, o null si fue rechazada
 * @param mensaje descripción del resultado
 */
public record ResultadoPago(
        Pago.Estado estado,
        String referencia,
        String mensaje) {
}
