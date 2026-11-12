package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.AsignacionRutaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.OperacionRutaException;
import com.agrovalle.agrovalle_connect.exceptions.OrdenNoEncontradaException;
import com.agrovalle.agrovalle_connect.exceptions.VehiculoNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import com.agrovalle.agrovalle_connect.repositories.VehiculoRepository;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class OrdenLogisticaServiceTest {

    private static final Long AGRICULTOR_ID = 7L;
    private static final Long VEHICULO_ID = 12L;
    private static final Long ORDEN_ID = 21L;

    @Mock
    private OrdenLogisticaRepository ordenRepository;

    @Mock
    private AgricultorRepository agricultorRepository;

    @Mock
    private VehiculoRepository vehiculoRepository;

    @InjectMocks
    private OrdenLogisticaService ordenService;

    @Test
    void creaOrdenPendienteAsociadaAlAgricultor() {
        Agricultor agricultor = agricultor(AGRICULTOR_ID);
        when(agricultorRepository.findById(AGRICULTOR_ID))
                .thenReturn(Optional.of(agricultor));
        when(ordenRepository.save(any(OrdenLogistica.class))).thenAnswer(invocation -> {
            OrdenLogistica orden = invocation.getArgument(0);
            orden.setId(ORDEN_ID);
            return orden;
        });

        OrdenLogisticaResponseDTO response = ordenService.crear(
                solicitudOrden(), AGRICULTOR_ID);

        assertEquals(ORDEN_ID, response.getId());
        assertEquals("Café", response.getProducto());
        assertEquals(OrdenLogistica.Estado.PENDIENTE_ALISTAMIENTO, response.getEstado());
        assertNotNull(response.getFechaCreacion());
    }

    @Test
    void agricultorPuedeAlistarSuOrden() {
        OrdenLogistica orden = orden(OrdenLogistica.Estado.PENDIENTE_ALISTAMIENTO);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(ordenRepository.save(any(OrdenLogistica.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        OrdenLogisticaResponseDTO response = ordenService.marcarAlistada(
                ORDEN_ID, AGRICULTOR_ID);

        assertEquals(OrdenLogistica.Estado.ALISTADO, response.getEstado());
    }

    @Test
    void asignaOrdenAlistadaGeneraGuiaYMarcaVehiculoEnRuta() {
        OrdenLogistica orden = orden(OrdenLogistica.Estado.ALISTADO);
        Vehiculo vehiculo = vehiculo(Vehiculo.Estado.DISPONIBLE);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(vehiculoRepository.buscarPorIdParaActualizar(VEHICULO_ID))
                .thenReturn(Optional.of(vehiculo));
        when(ordenRepository.save(any(OrdenLogistica.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        OrdenLogisticaResponseDTO response = ordenService.asignarRuta(
                ORDEN_ID, solicitudRuta(LocalDate.now()), AGRICULTOR_ID,
                RolUsuario.AGRICULTOR);

        assertEquals(OrdenLogistica.Estado.EN_RUTA, response.getEstado());
        assertEquals(LocalDate.now(), response.getFechaSalida());
        assertEquals(VEHICULO_ID, response.getVehiculoId());
        assertEquals(Vehiculo.Estado.EN_RUTA, vehiculo.getEstado());
        assertNotNull(response.getGuiaDespacho());
        verify(vehiculoRepository).save(vehiculo);
    }

    @Test
    void transportistaPuedeAsignarSoloSuVehiculo() {
        Long transportistaId = 15L;
        OrdenLogistica orden = orden(OrdenLogistica.Estado.ALISTADO);
        Vehiculo vehiculo = vehiculo(Vehiculo.Estado.DISPONIBLE);
        Transportista transportista = new Transportista();
        transportista.setId(transportistaId);
        vehiculo.setTransportista(transportista);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(vehiculoRepository.buscarPorIdParaActualizar(VEHICULO_ID))
                .thenReturn(Optional.of(vehiculo));
        when(ordenRepository.save(any(OrdenLogistica.class))).thenAnswer(invocation ->
                invocation.getArgument(0));

        OrdenLogisticaResponseDTO response = ordenService.asignarRuta(
                ORDEN_ID, solicitudRuta(LocalDate.now().plusDays(1)),
                transportistaId, RolUsuario.TRANSPORTISTA);

        assertEquals(OrdenLogistica.Estado.EN_RUTA, response.getEstado());
    }

    @Test
    void rechazaTransportistaQueIntentaUsarVehiculoAjeno() {
        OrdenLogistica orden = orden(OrdenLogistica.Estado.ALISTADO);
        Vehiculo vehiculo = vehiculo(Vehiculo.Estado.DISPONIBLE);
        Transportista transportista = new Transportista();
        transportista.setId(100L);
        vehiculo.setTransportista(transportista);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(vehiculoRepository.buscarPorIdParaActualizar(VEHICULO_ID))
                .thenReturn(Optional.of(vehiculo));

        OperacionRutaException exception = assertThrows(OperacionRutaException.class,
                () -> ordenService.asignarRuta(ORDEN_ID,
                        solicitudRuta(LocalDate.now()), 101L, RolUsuario.TRANSPORTISTA));

        assertEquals(HttpStatus.FORBIDDEN, exception.getStatus());
        verify(ordenRepository, never()).save(any(OrdenLogistica.class));
    }

    @Test
    void rechazaOrdenQueSuperaCapacidadDelVehiculo() {
        OrdenLogistica orden = orden(OrdenLogistica.Estado.ALISTADO);
        orden.setPesoKg(new BigDecimal("1500.01"));
        Vehiculo vehiculo = vehiculo(Vehiculo.Estado.DISPONIBLE);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(vehiculoRepository.buscarPorIdParaActualizar(VEHICULO_ID))
                .thenReturn(Optional.of(vehiculo));

        OperacionRutaException exception = assertThrows(OperacionRutaException.class,
                () -> ordenService.asignarRuta(ORDEN_ID,
                        solicitudRuta(LocalDate.now()), AGRICULTOR_ID, RolUsuario.AGRICULTOR));

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        verify(ordenRepository, never()).save(any(OrdenLogistica.class));
    }

    @Test
    void rechazaVehiculoOcupado() {
        OrdenLogistica orden = orden(OrdenLogistica.Estado.ALISTADO);
        Vehiculo vehiculo = vehiculo(Vehiculo.Estado.EN_RUTA);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(vehiculoRepository.buscarPorIdParaActualizar(VEHICULO_ID))
                .thenReturn(Optional.of(vehiculo));

        OperacionRutaException exception = assertThrows(OperacionRutaException.class,
                () -> ordenService.asignarRuta(ORDEN_ID,
                        solicitudRuta(LocalDate.now()), AGRICULTOR_ID, RolUsuario.AGRICULTOR));

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    }

    @Test
    void rechazaFechaDeSalidaPasada() {
        OrdenLogistica orden = orden(OrdenLogistica.Estado.ALISTADO);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));

        OperacionRutaException exception = assertThrows(OperacionRutaException.class,
                () -> ordenService.asignarRuta(ORDEN_ID,
                        solicitudRuta(LocalDate.now().minusDays(1)),
                        AGRICULTOR_ID, RolUsuario.AGRICULTOR));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        verify(vehiculoRepository, never()).buscarPorIdParaActualizar(VEHICULO_ID);
    }

    @Test
    void rechazaAsignacionDeOrdenNoAlistada() {
        OrdenLogistica orden = orden(OrdenLogistica.Estado.PENDIENTE_ALISTAMIENTO);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));

        assertThrows(OperacionRutaException.class,
                () -> ordenService.asignarRuta(ORDEN_ID,
                        solicitudRuta(LocalDate.now()), AGRICULTOR_ID, RolUsuario.AGRICULTOR));

        verify(vehiculoRepository, never()).buscarPorIdParaActualizar(VEHICULO_ID);
    }

    @Test
    void retorna404CuandoLaOrdenNoExiste() {
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.empty());

        assertThrows(OrdenNoEncontradaException.class,
                () -> ordenService.marcarAlistada(ORDEN_ID, AGRICULTOR_ID));
    }

    @Test
    void retorna404CuandoElVehiculoNoExiste() {
        OrdenLogistica orden = orden(OrdenLogistica.Estado.ALISTADO);
        when(ordenRepository.buscarPorIdParaActualizar(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(vehiculoRepository.buscarPorIdParaActualizar(VEHICULO_ID))
                .thenReturn(Optional.empty());

        assertThrows(VehiculoNoEncontradoException.class,
                () -> ordenService.asignarRuta(ORDEN_ID,
                        solicitudRuta(LocalDate.now()), AGRICULTOR_ID, RolUsuario.AGRICULTOR));
    }

    @Test
    void listaOrdenesPropiasOAlistadasSegunRol() {
        when(ordenRepository.buscarPorAgricultor(AGRICULTOR_ID)).thenReturn(List.of());
        when(ordenRepository.findByEstadoOrderByFechaCreacionAsc(
                OrdenLogistica.Estado.ALISTADO)).thenReturn(List.of());

        assertEquals(List.of(), ordenService.listar(AGRICULTOR_ID, RolUsuario.AGRICULTOR));
        assertEquals(List.of(), ordenService.listar(AGRICULTOR_ID, RolUsuario.TRANSPORTISTA));

        verify(ordenRepository).buscarPorAgricultor(AGRICULTOR_ID);
        verify(ordenRepository).findByEstadoOrderByFechaCreacionAsc(
                OrdenLogistica.Estado.ALISTADO);
    }

    private Agricultor agricultor(final Long id) {
        Agricultor agricultor = new Agricultor();
        agricultor.setId(id);
        return agricultor;
    }

    private OrdenLogistica orden(final OrdenLogistica.Estado estado) {
        OrdenLogistica orden = new OrdenLogistica();
        orden.setId(ORDEN_ID);
        orden.setProducto("Café");
        orden.setPesoKg(new BigDecimal("1200.00"));
        orden.setDestino("Cali");
        orden.setEstado(estado);
        orden.setFechaCreacion(Instant.now());
        orden.setAgricultor(agricultor(AGRICULTOR_ID));
        return orden;
    }

    private Vehiculo vehiculo(final Vehiculo.Estado estado) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(VEHICULO_ID);
        vehiculo.setPlaca("ABC-123");
        vehiculo.setCapacidadKg(1500);
        vehiculo.setEstado(estado);
        return vehiculo;
    }

    private OrdenLogisticaRequestDTO solicitudOrden() {
        OrdenLogisticaRequestDTO dto = new OrdenLogisticaRequestDTO();
        dto.setProducto(" Café ");
        dto.setPesoKg(new BigDecimal("1200"));
        dto.setDestino(" Cali ");
        return dto;
    }

    private AsignacionRutaRequestDTO solicitudRuta(final LocalDate fechaSalida) {
        AsignacionRutaRequestDTO dto = new AsignacionRutaRequestDTO();
        dto.setVehiculoId(VEHICULO_ID);
        dto.setFechaSalida(fechaSalida);
        return dto;
    }
}
