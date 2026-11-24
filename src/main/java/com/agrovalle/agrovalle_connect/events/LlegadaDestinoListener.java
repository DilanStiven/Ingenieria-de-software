package com.agrovalle.agrovalle_connect.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Notifica internamente al comprador la llegada del viaje al destino.
 */
@Component
public class LlegadaDestinoListener {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LlegadaDestinoListener.class);

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notificarComprador(final LlegadaDestinoEvent event) {
        LOGGER.info("Pedido del viaje {} llegó a {} y será entregado al comprador {}",
                event.getOrdenLogisticaId(), event.getUbicacion(),
                event.getCompradorId() == null ? "asignado" : event.getCompradorId());
    }
}
