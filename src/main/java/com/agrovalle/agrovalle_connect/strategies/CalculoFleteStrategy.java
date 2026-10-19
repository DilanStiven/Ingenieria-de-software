package com.agrovalle.agrovalle_connect.strategies;

import java.math.BigDecimal;

/**
 * Estrategia común para el cálculo de flete según municipio (Patrón Strategy - GoF / OCP).
 */
public interface CalculoFleteStrategy {

    /**
     * Retorna el nombre del municipio al que aplica la estrategia.
     *
     * @return nombre del municipio en mayúsculas
     */
    String getMunicipio();

    /**
     * Calcula el costo total del flete según el peso en kg.
     *
     * @param pesoKg peso total de la carga en kilogramos
     * @return costo estimado del flete en pesos colombianos
     */
    BigDecimal calcularFlete(BigDecimal pesoKg);
}
