package com.agrovalle.agrovalle_connect.events;

/**
 * Evento publicado cuando un viaje llega a su destino.
 */
public class LlegadaDestinoEvent {

    private final Long ordenLogisticaId;
    private final Long compradorId;
    private final Long pedidoId;
    private final Long productoId;
    private final String ubicacion;

    public LlegadaDestinoEvent(final Long ordenLogisticaId,
            final Long compradorId, final Long pedidoId, final Long productoId,
            final String ubicacion) {
        this.ordenLogisticaId = ordenLogisticaId;
        this.compradorId = compradorId;
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.ubicacion = ubicacion;
    }

    public LlegadaDestinoEvent(final Long ordenLogisticaId,
            final Long compradorId, final String ubicacion) {
        this(ordenLogisticaId, compradorId, null, null, ubicacion);
    }

    public LlegadaDestinoEvent(final Long ordenLogisticaId, final String ubicacion) {
        this(ordenLogisticaId, null, null, null, ubicacion);
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

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public String getUbicacion() {
        return ubicacion;
    }
}
