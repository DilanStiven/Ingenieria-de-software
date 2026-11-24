package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.CheckpointViajeResponseDTO;
import com.agrovalle.agrovalle_connect.events.LlegadaDestinoEvent;
import com.agrovalle.agrovalle_connect.exceptions.OperacionRutaException;
import com.agrovalle.agrovalle_connect.exceptions.OrdenNoEncontradaException;
import com.agrovalle.agrovalle_connect.models.CheckpointViaje;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.repositories.CheckpointViajeRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import java.time.Instant;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registra checkpoints de entregas logísticas.
 */
@Service
public class CheckpointService {

    private final OrdenLogisticaRepository ordenRepository;
    private final CheckpointViajeRepository checkpointRepository;
    private final ApplicationEventPublisher eventPublisher;

    public CheckpointService(final OrdenLogisticaRepository ordenes,
            final CheckpointViajeRepository checkpoints,
            final ApplicationEventPublisher publisher) {
        this.ordenRepository = ordenes;
        this.checkpointRepository = checkpoints;
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
        if (orden.getVehiculo() == null
                || orden.getVehiculo().getTransportista() == null
                || !orden.getVehiculo().getTransportista().getId()
                        .equals(transportistaId)) {
            throw new OperacionRutaException(
                    "Solo el transportista asignado puede registrar la llegada",
                    HttpStatus.FORBIDDEN);
        }

        orden.setEstado(OrdenLogistica.Estado.EN_DESTINO);

        CheckpointViaje checkpoint = new CheckpointViaje();
        checkpoint.setOrdenLogistica(orden);
        checkpoint.setEstado(OrdenLogistica.Estado.EN_DESTINO);
        checkpoint.setUbicacion(orden.getDestino());
        checkpoint.setFechaHora(Instant.now());

        CheckpointViaje guardado = checkpointRepository.save(checkpoint);
        ordenRepository.save(orden);
        eventPublisher.publishEvent(new LlegadaDestinoEvent(
                orden.getId(), orden.getDestino()));
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
}
