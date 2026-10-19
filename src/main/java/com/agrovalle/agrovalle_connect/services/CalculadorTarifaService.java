package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.exceptions.MunicipioNoValidoException;
import com.agrovalle.agrovalle_connect.strategies.CalculoFleteStrategy;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * Servicio contextual que despacha el cálculo de tarifa según el municipio (GoF Strategy).
 */
@Service
public class CalculadorTarifaService {

    private final Map<String, CalculoFleteStrategy> estrategias;

    /**
     * Inyección por constructor de todas las implementaciones de CalculoFleteStrategy (DIP).
     *
     * @param listaEstrategias lista de beans que implementan la interfaz
     */
    public CalculadorTarifaService(final List<CalculoFleteStrategy> listaEstrategias) {
        this.estrategias = listaEstrategias.stream()
                .collect(Collectors.toMap(
                        e -> e.getMunicipio().toUpperCase(),
                        Function.identity()
                ));
    }

    /**
     * Calcula el flete seleccionando la estrategia correspondiente en tiempo de ejecución.
     *
     * @param municipio municipio de origen
     * @param pesoKg    peso de la carga
     * @return costo calculado del flete
     */
    public BigDecimal calcularFlete(final String municipio, final BigDecimal pesoKg) {
        if (municipio == null || !estrategias.containsKey(municipio.trim().toUpperCase())) {
            throw new MunicipioNoValidoException(municipio);
        }
        CalculoFleteStrategy estrategia = estrategias.get(municipio.trim().toUpperCase());
        return estrategia.calcularFlete(pesoKg);
    }
}
