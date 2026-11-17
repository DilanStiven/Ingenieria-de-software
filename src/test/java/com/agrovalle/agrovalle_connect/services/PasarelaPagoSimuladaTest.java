package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.agrovalle.agrovalle_connect.models.Pago;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class PasarelaPagoSimuladaTest {

    private final PasarelaPagoSimulada pasarela = new PasarelaPagoSimulada();

    @Test
    void apruebaPagoValido() {
        ResultadoPago resultado = pasarela.procesarPago(
                new BigDecimal("150000.00"),
                Pago.MedioPago.TARJETA);

        assertEquals(Pago.Estado.APROBADO, resultado.estado());
        assertNotNull(resultado.referencia());
        assertNotNull(resultado.mensaje());
    }

    @Test
    void rechazaPagoSinMontoValido() {
        ResultadoPago resultado = pasarela.procesarPago(
                BigDecimal.ZERO,
                Pago.MedioPago.PSE);

        assertEquals(Pago.Estado.RECHAZADO, resultado.estado());
        assertNull(resultado.referencia());
    }

    @Test
    void rechazaPagoSinMedio() {
        ResultadoPago resultado = pasarela.procesarPago(
                new BigDecimal("50000.00"),
                null);

        assertEquals(Pago.Estado.RECHAZADO, resultado.estado());
        assertNull(resultado.referencia());
    }
}
