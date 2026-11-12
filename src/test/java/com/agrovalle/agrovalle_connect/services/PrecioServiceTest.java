package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.PrecioPromedioResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PrecioNoDisponibleException;
import com.agrovalle.agrovalle_connect.repositories.PrecioRepository;
import com.agrovalle.agrovalle_connect.repositories.PrecioRepository.PromedioPrecio;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PrecioServiceTest {

    @Mock
    private PrecioRepository precioRepository;

    @Mock
    private PromedioPrecio promedioPrecio;

    @InjectMocks
    private PrecioService precioService;

    @Test
    void calculaPromedioDeLasUltimas24HorasEnPesosPorKilogramo() {
        when(precioRepository.calcularPromedio(
                eq("café"), any(Instant.class), any(Instant.class)))
                .thenReturn(promedioPrecio);
        when(promedioPrecio.getPromedio()).thenReturn(new BigDecimal("1234.505"));
        when(promedioPrecio.getCantidad()).thenReturn(50L);

        PrecioPromedioResponseDTO respuesta = precioService.obtenerPromedio(" Café ");

        assertEquals("Café", respuesta.getProducto());
        assertEquals(new BigDecimal("1234.51"), respuesta.getPrecioPromedio());
        assertEquals("COP", respuesta.getMoneda());
        assertEquals("kg", respuesta.getUnidadPrecio());
        assertEquals(50L, respuesta.getCantidadTransacciones());

        ArgumentCaptor<Instant> instantes = ArgumentCaptor.forClass(Instant.class);
        verify(precioRepository).calcularPromedio(
                eq("café"), instantes.capture(), instantes.capture());
        assertEquals(Duration.ofHours(24),
                Duration.between(instantes.getAllValues().get(0),
                        instantes.getAllValues().get(1)));
    }

    @Test
    void informaCuandoNoHayTransaccionesEnLaVentana() {
        when(precioRepository.calcularPromedio(
                eq("café"), any(Instant.class), any(Instant.class)))
                .thenReturn(promedioPrecio);
        when(promedioPrecio.getCantidad()).thenReturn(0L);

        assertThrows(PrecioNoDisponibleException.class,
                () -> precioService.obtenerPromedio("Café"));
    }
}
