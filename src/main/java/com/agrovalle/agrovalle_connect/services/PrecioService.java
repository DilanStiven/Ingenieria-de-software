package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.PrecioPromedioResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PrecioNoDisponibleException;
import com.agrovalle.agrovalle_connect.repositories.PrecioRepository;
import com.agrovalle.agrovalle_connect.repositories.PrecioRepository.PromedioPrecio;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.Locale;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Lógica de negocio para el promedio de precios de transacciones (HU-03).
 */
@Service
public class PrecioService {

    private static final Duration VENTANA_PROMEDIO = Duration.ofHours(24);
    private static final int ESCALA_MONETARIA = 2;
    private static final String MONEDA = "COP";
    private static final String UNIDAD_PRECIO = "kg";

    private final PrecioRepository precioRepository;

    /**
     * Crea el servicio con el repositorio de precios.
     *
     * @param repository repositorio de transacciones de precio
     */
    public PrecioService(final PrecioRepository repository) {
        this.precioRepository = repository;
    }

    /**
     * Calcula el promedio aritmético por kilogramo para las últimas 24 horas.
     *
     * @param nombreProducto producto a consultar
     * @return promedio y cantidad de transacciones utilizadas
     */
    @Transactional(readOnly = true)
    public PrecioPromedioResponseDTO obtenerPromedio(final String nombreProducto) {
        String producto = nombreProducto.trim();
        Instant hasta = Instant.now();
        Instant desde = hasta.minus(VENTANA_PROMEDIO);
        PromedioPrecio resultado = precioRepository.calcularPromedio(
                producto.toLowerCase(Locale.ROOT), desde, hasta);

        if (resultado.getCantidad() == null || resultado.getCantidad() == 0L
                || resultado.getPromedio() == null) {
            throw new PrecioNoDisponibleException(producto);
        }

        return new PrecioPromedioResponseDTO(producto, resultado.getPromedio()
                .setScale(ESCALA_MONETARIA, RoundingMode.HALF_UP), MONEDA,
                UNIDAD_PRECIO, resultado.getCantidad());
    }
}
