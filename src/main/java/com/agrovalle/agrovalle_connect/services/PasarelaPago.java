package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.models.Pago;
import java.math.BigDecimal;

/**
 * Contrato desacoplado para procesar transacciones con una pasarela de pagos.
 */
public interface PasarelaPago {

    /**
     * Procesa el pago de un monto mediante el medio seleccionado.
     *
     * @param monto monto a cobrar
     * @param medioPago medio de pago seleccionado
     * @return resultado de la transaccion
     */
    ResultadoPago procesarPago(BigDecimal monto, Pago.MedioPago medioPago);
}
