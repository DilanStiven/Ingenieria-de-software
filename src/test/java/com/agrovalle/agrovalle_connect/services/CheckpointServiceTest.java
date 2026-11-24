package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.CheckpointViajeResponseDTO;
import com.agrovalle.agrovalle_connect.events.LlegadaDestinoEvent;
import com.agrovalle.agrovalle_connect.exceptions.OperacionRutaException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.CheckpointViaje;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.repositories.CheckpointViajeRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class CheckpointServiceTest {

    @Mock
    private OrdenLogisticaRepository ordenRepository;

    @Mock
    private CheckpointViajeRepository checkpointRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private CheckpointService checkpointService;

    @Test
    void registraLlegadaDelConductorAsignado() {
        OrdenLogistica orden = ordenEnRuta();
        when(ordenRepository.buscarPorIdParaActualizar(21L)).thenReturn(Optional.of(orden));
        when(checkpointRepository.save(any(CheckpointViaje.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        CheckpointViajeResponseDTO response =
                checkpointService.registrarLlegada(21L, 15L);

        assertEquals(OrdenLogistica.Estado.EN_DESTINO, response.getEstado());
        assertEquals("Cali", response.getUbicacion());
        assertEquals(21L, response.getViajeId());
        verify(checkpointRepository).save(any(CheckpointViaje.class));
        verify(eventPublisher).publishEvent(isA(LlegadaDestinoEvent.class));
    }

    @Test
    void rechazaLlegadaDeConductorNoAsignado() {
        OrdenLogistica orden = ordenEnRuta();
        when(ordenRepository.buscarPorIdParaActualizar(21L)).thenReturn(Optional.of(orden));

        OperacionRutaException exception = assertThrows(OperacionRutaException.class,
                () -> checkpointService.registrarLlegada(21L, 99L));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
    }

    @Test
    void rechazaLlegadaCuandoElViajeNoEstaEnRuta() {
        OrdenLogistica orden = ordenEnRuta();
        orden.setEstado(OrdenLogistica.Estado.ALISTADO);
        when(ordenRepository.buscarPorIdParaActualizar(21L)).thenReturn(Optional.of(orden));

        OperacionRutaException exception = assertThrows(OperacionRutaException.class,
                () -> checkpointService.registrarLlegada(21L, 15L));

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    private OrdenLogistica ordenEnRuta() {
        Agricultor agricultor = new Agricultor();
        agricultor.setId(7L);

        Transportista transportista = new Transportista();
        transportista.setId(15L);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(12L);
        vehiculo.setPlaca("ABC123");
        vehiculo.setCapacidadKg(1500);
        vehiculo.setTransportista(transportista);

        OrdenLogistica orden = new OrdenLogistica();
        orden.setId(21L);
        orden.setProducto("Café");
        orden.setPesoKg(new BigDecimal("1200"));
        orden.setDestino("Cali");
        orden.setEstado(OrdenLogistica.Estado.EN_RUTA);
        orden.setFechaCreacion(Instant.now());
        orden.setAgricultor(agricultor);
        orden.setVehiculo(vehiculo);
        return orden;
    }
}
