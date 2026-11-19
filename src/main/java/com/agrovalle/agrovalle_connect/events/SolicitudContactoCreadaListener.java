package com.agrovalle.agrovalle_connect.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Procesa la notificación interna de una nueva solicitud de contacto.
 */
@Component
public class SolicitudContactoCreadaListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SolicitudContactoCreadaListener.class);

    /**
     * Registra el evento de notificación interna dirigido al agricultor.
     *
     * @param event solicitud de contacto creada
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void notificarAgricultor(final SolicitudContactoCreadaEvent event) {
        LOGGER.info("Evento interno de contacto publicado para el agricultor {} por "
                        + "el producto {} y el pedido {}",
                event.getAgricultorId(),
                event.getProductoId(),
                event.getPedidoId());
    }
}
