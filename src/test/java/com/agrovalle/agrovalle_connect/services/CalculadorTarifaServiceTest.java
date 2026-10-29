package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.agrovalle.agrovalle_connect.exceptions.MunicipioNoValidoException;
import com.agrovalle.agrovalle_connect.strategies.CalculoFleteStrategy;
import com.agrovalle.agrovalle_connect.strategies.FleteBugaStrategy;
import com.agrovalle.agrovalle_connect.strategies.FleteDaguaStrategy;
import com.agrovalle.agrovalle_connect.strategies.FletePalmiraStrategy;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CalculadorTarifaService — estrategias de flete")
class CalculadorTarifaServiceTest {

    private final CalculadorTarifaService calculador = new CalculadorTarifaService(
            List.of(
                    new FleteDaguaStrategy(),
                    new FletePalmiraStrategy(),
                    new FleteBugaStrategy()));

    @Test
    @DisplayName("Despacha cada municipio mediante la estrategia intercambiable correspondiente")
    void despachaEstrategiasDeFletePorMunicipio() {
        BigDecimal pesoKg = new BigDecimal("2");

        assertEquals(new BigDecimal("35500.00"), calculador.calcularFlete(" Dagua ", pesoKg));
        assertEquals(new BigDecimal("20300.00"), calculador.calcularFlete("palmira", pesoKg));
        assertEquals(new BigDecimal("45360.00"), calculador.calcularFlete("BUGA", pesoKg));
    }

    @Test
    @DisplayName("Cada implementación puede sustituir a otra mediante el contrato de estrategia")
    void cadaEstrategiaRespetaElContratoIntercambiable() {
        List<CalculoFleteStrategy> estrategias = List.of(
                new FleteDaguaStrategy(),
                new FletePalmiraStrategy(),
                new FleteBugaStrategy());
        BigDecimal pesoKg = new BigDecimal("2");

        assertEquals(
                List.of("DAGUA", "PALMIRA", "BUGA"),
                estrategias.stream().map(CalculoFleteStrategy::getMunicipio).toList());
        assertEquals(
                List.of(
                        new BigDecimal("35500.00"),
                        new BigDecimal("20300.00"),
                        new BigDecimal("45360.00")),
                estrategias.stream()
                        .map(estrategia -> estrategia.calcularFlete(pesoKg))
                        .toList());
    }

    @Test
    @DisplayName("Rechaza municipios no configurados y valores nulos")
    void rechazaMunicipiosNoValidos() {
        BigDecimal pesoKg = new BigDecimal("2");

        assertThrows(
                MunicipioNoValidoException.class,
                () -> calculador.calcularFlete("Cali", pesoKg));
        assertThrows(
                MunicipioNoValidoException.class,
                () -> calculador.calcularFlete(null, pesoKg));
    }
}
