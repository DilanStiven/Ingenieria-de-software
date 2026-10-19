package com.agrovalle.agrovalle_connect.strategies;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Estrategia de flete para Dagua: Tarifa base $35.000 + $250 por kg (zona montañosa).
 */
@Component
public class FleteDaguaStrategy implements CalculoFleteStrategy {

    private static final BigDecimal TARIFA_BASE = new BigDecimal("35000.00");
    private static final BigDecimal COSTO_POR_KG = new BigDecimal("250.00");

    @Override
    public String getMunicipio() {
        return "DAGUA";
    }

    @Override
    public BigDecimal calcularFlete(final BigDecimal pesoKg) {
        return TARIFA_BASE.add(COSTO_POR_KG.multiply(pesoKg));
    }
}
