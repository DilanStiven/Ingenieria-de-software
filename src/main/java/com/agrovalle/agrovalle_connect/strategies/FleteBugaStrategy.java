package com.agrovalle.agrovalle_connect.strategies;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Estrategia de flete para Buga: Tarifa base $45.000 + $180 por kg (distancia media).
 */
@Component
public class FleteBugaStrategy implements CalculoFleteStrategy {

    private static final BigDecimal TARIFA_BASE = new BigDecimal("45000.00");
    private static final BigDecimal COSTO_POR_KG = new BigDecimal("180.00");

    @Override
    public String getMunicipio() {
        return "BUGA";
    }

    @Override
    public BigDecimal calcularFlete(final BigDecimal pesoKg) {
        return TARIFA_BASE.add(COSTO_POR_KG.multiply(pesoKg));
    }
}
