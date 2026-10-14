package com.agrovalle.agrovalle_connect.strategies;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Estrategia de flete para Palmira: Tarifa base $20.000 + $150 por kg (vía plana/autopista).
 */
@Component
public class FletePalmiraStrategy implements CalculoFleteStrategy {

    private static final BigDecimal TARIFA_BASE = new BigDecimal("20000.00");
    private static final BigDecimal COSTO_POR_KG = new BigDecimal("150.00");

    @Override
    public String getMunicipio() {
        return "PALMIRA";
    }

    @Override
    public BigDecimal calcularFlete(final BigDecimal pesoKg) {
        return TARIFA_BASE.add(COSTO_POR_KG.multiply(pesoKg));
    }
}
