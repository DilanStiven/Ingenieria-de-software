package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.CheckpointViajeResponseDTO;
import com.agrovalle.agrovalle_connect.events.LlegadaDestinoEvent;
import com.agrovalle.agrovalle_connect.exceptions.OperacionRutaException;
import com.agrovalle.agrovalle_connect.exceptions.OrdenNoEncontradaException;
import com.agrovalle.agrovalle_connect.models.CheckpointViaje;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.repositories.CheckpointViajeRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import com.agrovalle.agrovalle_connect.repositories.VehiculoRepository;
import java.time.Instant;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registra checkpoints y completa entregas logísticas.
 */
@Service
public class CheckpointService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckpointService.class);

    private final OrdenLogisticaRepository ordenRepository;
    private final CheckpointViajeRepository checkpointRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CheckpointService(final OrdenLogisticaRepository ordenes,
            final CheckpointViajeRepository checkpoints,
            final VehiculoRepository vehiculos,
            final ApplicationEventPublisher publisher) {
        this.ordenRepository = ordenes;
        this.checkpointRepository = checkpoints;
        this.vehiculoRepository = vehiculos;
        this.eventPublisher = publisher;
    }

    /**
     * Registra la llegada del conductor asignado al destino.
     *
     * @param ordenId identificador del viaje
     * @param transportistaId conductor autenticado
     * @return checkpoint creado
     */
    @Transactional
    public CheckpointViajeResponseDTO registrarLlegada(final Long ordenId,
            final Long transportistaId) {
        OrdenLogistica orden = ordenRepository.buscarPorIdParaActualizar(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));

        if (orden.getEstado() != OrdenLogistica.Estado.EN_RUTA) {
            throw new OperacionRutaException(
                    "Solo se puede registrar llegada de un viaje EN_RUTA",
                    HttpStatus.CONFLICT);
        }
        Vehiculo vehiculo = obtenerVehiculoAsignado(orden, transportistaId);

        orden.setEstado(OrdenLogistica.Estado.EN_DESTINO);
        CheckpointViaje guardado = guardarCheckpoint(
                orden, OrdenLogistica.Estado.EN_DESTINO);
        ordenRepository.save(orden);
        publicarNotificacionLlegada(orden);
        return new CheckpointViajeResponseDTO(guardado);
    }

    /**
     * Confirma la entrega del pedido por el transportista asignado.
     *
     * @param ordenId identificador del viaje
     * @param transportistaId conductor autenticado
     * @return checkpoint creado para la entrega
     */
    @Transactional
    public CheckpointViajeResponseDTO registrarEntrega(final Long ordenId,
            final Long transportistaId) {
        OrdenLogistica orden = ordenRepository.buscarPorIdParaActualizar(ordenId)
                .orElseThrow(() -> new OrdenNoEncontradaException(ordenId));

        if (orden.getEstado() != OrdenLogistica.Estado.EN_DESTINO) {
            throw new OperacionRutaException(
                    "Solo se puede confirmar la entrega de un viaje EN_DESTINO",
                    HttpStatus.CONFLICT);
        }

        Vehiculo vehiculo = obtenerVehiculoAsignado(orden, transportistaId);
        orden.setEstado(OrdenLogistica.Estado.ENTREGADA);
        vehiculo.setEstado(Vehiculo.Estado.DISPONIBLE);

        CheckpointViaje guardado = guardarCheckpoint(
                orden, OrdenLogistica.Estado.ENTREGADA);
        vehiculoRepository.save(vehiculo);
        ordenRepository.save(orden);
        return new CheckpointViajeResponseDTO(guardado);
    }

    /**
     * Alias semántico para el escenario de llegada al destino.
     *
     * @param ordenId identificador del viaje
     * @param transportistaId conductor autenticado
     * @return checkpoint creado
     */
    @Transactional
    public CheckpointViajeResponseDTO registrarLlegadaDestino(final Long ordenId,
            final Long transportistaId) {
        return registrarLlegada(ordenId, transportistaId);
    }

    private Vehiculo obtenerVehiculoAsignado(final OrdenLogistica orden,
            final Long transportistaId) {
        if (orden.getVehiculo() == null) {
            throw new OperacionRutaException(
                    "El viaje no tiene un vehículo asignado",
                    HttpStatus.CONFLICT);
        }
        Long vehiculoId = orden.getVehiculo().getId();
        Vehiculo vehiculo = vehiculoRepository.buscarPorIdParaActualizar(vehiculoId)
                .orElseThrow(() -> new OperacionRutaException(
                        "El vehículo asignado al viaje ya no existe",
                        HttpStatus.CONFLICT));
        if (vehiculo.getTransportista() == null
                || !Objects.equals(
                        vehiculo.getTransportista().getId(), transportistaId)) {
            throw new OperacionRutaException(
                    "Solo el transportista asignado puede actualizar el viaje",
                    HttpStatus.FORBIDDEN);
        }
        if (vehiculo.getEstado() != Vehiculo.Estado.EN_RUTA) {
            throw new OperacionRutaException(
                    "El vehículo asignado no está en ruta",
                    HttpStatus.CONFLICT);
        }
        return vehiculo;
    }

    private CheckpointViaje guardarCheckpoint(final OrdenLogistica orden,
            final OrdenLogistica.Estado estado) {
        CheckpointViaje checkpoint = new CheckpointViaje();
        checkpoint.setOrdenLogistica(orden);
        checkpoint.setEstado(estado);
        checkpoint.setUbicacion(orden.getDestino());
        checkpoint.setFechaHora(Instant.now());
        return checkpointRepository.save(checkpoint);
    }

    private void publicarNotificacionLlegada(final OrdenLogistica orden) {
        Pedido pedido = orden.getPedido();
        if (pedido == null || pedido.getId() == null || pedido.getCompradorId() == null
                || pedido.getProducto() == null || pedido.getProducto().getId() == null) {
            LOGGER.warn("No se publicó notificación de llegada para el viaje {}: "
                    + "no tiene comprador y pedido vinculados",
                    orden.getId());
            return;
        }
        eventPublisher.publishEvent(new LlegadaDestinoEvent(
                orden.getId(), pedido.getCompradorId(), pedido.getId(),
                pedido.getProducto().getId(), orden.getDestino()));
    }
}
