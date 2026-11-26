package com.agrovalle.agrovalle_connect.events;

import com.agrovalle.agrovalle_connect.services.NotificacionService;
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

    private final NotificacionService notificacionService;

    public LlegadaDestinoListener(final NotificacionService service) {
        this.notificacionService = service;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notificarComprador(final LlegadaDestinoEvent event) {
        if (event.getCompradorId() == null || event.getPedidoId() == null
                || event.getProductoId() == null) {
            LOGGER.warn("No se pudo crear notificación de llegada para el viaje {}: "
                    + "faltan datos del comprador o del pedido",
                    event.getOrdenLogisticaId());
            return;
        }
        notificacionService.registrarLlegadaComprador(event);
        LOGGER.info("Se guardó una notificación de llegada para el comprador {} "
                        + "del pedido {}",
                event.getCompradorId(), event.getPedidoId());
    }
}
