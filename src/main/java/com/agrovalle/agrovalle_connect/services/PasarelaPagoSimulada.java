package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.models.Pago;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Component;

/**
 * Adaptador sandbox que simula respuestas de aprobacion y rechazo.
 */
@Component
public class PasarelaPagoSimulada implements PasarelaPago {

    @Override
    public ResultadoPago procesarPago(
            final BigDecimal monto,
            final Pago.MedioPago medioPago) {
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            return new ResultadoPago(
                    Pago.Estado.RECHAZADO,
                    null,
                    "El monto del pago debe ser mayor que cero");
        }
        if (medioPago == null) {
            return new ResultadoPago(
                    Pago.Estado.RECHAZADO,
                    null,
                    "Debe seleccionar un medio de pago valido");
        }

        String referencia = "SIM-" + UUID.randomUUID();
        return new ResultadoPago(
                Pago.Estado.APROBADO,
                referencia,
                "Pago aprobado por la pasarela simulada");
    }
}
