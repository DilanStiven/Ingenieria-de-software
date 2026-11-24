package com.agrovalle.agrovalle_connect.events;

/**
 * Evento publicado cuando un viaje llega a su destino.
 */
public class LlegadaDestinoEvent {

    private final Long ordenLogisticaId;
    private final Long compradorId;
    private final String ubicacion;

    public LlegadaDestinoEvent(final Long ordenLogisticaId,
            final Long compradorId, final String ubicacion) {
        this.ordenLogisticaId = ordenLogisticaId;
        this.compradorId = compradorId;
        this.ubicacion = ubicacion;
    }

    public LlegadaDestinoEvent(final Long ordenLogisticaId, final String ubicacion) {
        this(ordenLogisticaId, null, ubicacion);
    }

    public Long getOrdenLogisticaId() {
        return ordenLogisticaId;
    }

    public Long getViajeId() {
        return ordenLogisticaId;
    }

    public Long getCompradorId() {
        return compradorId;
    }

    public String getUbicacion() {
        return ubicacion;
    }
}
