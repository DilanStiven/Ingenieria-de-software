package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.exceptions.CalificacionTransporteException;
import com.agrovalle.agrovalle_connect.exceptions.OrdenNoEncontradaException;
import com.agrovalle.agrovalle_connect.models.CalificacionTransporte;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.repositories.CalificacionTransporteRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class CalificacionTransporteServiceTest {

    private static final Long VIAJE_ID = 24L;
    private static final Long COMPRADOR_ID = 7L;
    private static final Long CONDUCTOR_ID = 13L;

    @Mock
    private OrdenLogisticaRepository ordenLogisticaRepository;

    @Mock
    private CalificacionTransporteRepository calificacionRepository;

    private CalificacionTransporteService service;
    private OrdenLogistica viaje;
    private Transportista conductor;

    @BeforeEach
    void configurar() {
        MockitoAnnotations.openMocks(this);
        service = new CalificacionTransporteService(
                ordenLogisticaRepository, calificacionRepository);

        conductor = new Transportista();
        conductor.setId(CONDUCTOR_ID);
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setTransportista(conductor);
        viaje = new OrdenLogistica();
        viaje.setId(VIAJE_ID);
        viaje.setEstado(OrdenLogistica.Estado.EN_RUTA);
        viaje.setVehiculo(vehiculo);
        Pedido pedido = new Pedido();
        pedido.setCompradorId(COMPRADOR_ID);
        viaje.setPedido(pedido);

        when(ordenLogisticaRepository.buscarPorIdParaActualizar(VIAJE_ID))
                .thenReturn(Optional.of(viaje));
        when(calificacionRepository.save(any(CalificacionTransporte.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void dadoViajeEnRuta_cuandoCalificaConCuatroEstrellas_entoncesAsociaAlConductor() {
        CalificacionTransporte calificacion =
                service.registrar(VIAJE_ID, COMPRADOR_ID, 4, "Cajas en buen estado");
        ArgumentCaptor<CalificacionTransporte> captor =
                ArgumentCaptor.forClass(CalificacionTransporte.class);

        assertEquals(viaje, calificacion.getViaje());
        assertEquals(conductor, calificacion.getConductor());
        assertEquals(Short.valueOf((short) 4), calificacion.getPuntualidad());
        assertEquals("Cajas en buen estado", calificacion.getEstadoCajas());

        verify(calificacionRepository).save(captor.capture());
        assertEquals(Short.valueOf((short) 4), captor.getValue().getPuntualidad());
        assertEquals(CONDUCTOR_ID, captor.getValue().getConductor().getId());
    }

    @Test
    void dadoPuntualidadFueraDeRango_cuandoRegistra_entoncesRechazaEntrada() {
        assertThrows(CalificacionTransporteException.class,
                () -> service.registrar(
                        VIAJE_ID, COMPRADOR_ID, 6, "Cajas en buen estado"));

        verify(ordenLogisticaRepository, never()).buscarPorIdParaActualizar(VIAJE_ID);
        verify(calificacionRepository, never()).save(any(CalificacionTransporte.class));
    }

    @Test
    void dadoViajeSinTransportista_cuandoRegistra_entoncesRechazaCalificacion() {
        viaje.getVehiculo().setTransportista(null);

        assertThrows(CalificacionTransporteException.class,
                () -> service.registrar(
                        VIAJE_ID, COMPRADOR_ID, 4, "Cajas en buen estado"));

        verify(calificacionRepository, never()).save(any(CalificacionTransporte.class));
    }

    @Test
    void dadoViajeYaCalificado_cuandoRegistra_entoncesRechazaDuplicado() {
        when(calificacionRepository.existsByViajeId(VIAJE_ID)).thenReturn(true);

        assertThrows(CalificacionTransporteException.class,
                () -> service.registrar(
                        VIAJE_ID, COMPRADOR_ID, 4, "Cajas en buen estado"));

        verify(calificacionRepository, never()).save(any(CalificacionTransporte.class));
    }

    @Test
    void dadoUsuarioQueNoRecibioElPedido_cuandoCalifica_entoncesNoEncuentraElViaje() {
        assertThrows(OrdenNoEncontradaException.class,
                () -> service.registrar(VIAJE_ID, 99L, 4, "Cajas en buen estado"));

        verify(calificacionRepository, never()).save(any(CalificacionTransporte.class));
    }
}
