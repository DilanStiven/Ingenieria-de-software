package com.agrovalle.agrovalle_connect.events;

import com.agrovalle.agrovalle_connect.services.NotificacionService;
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

    private final NotificacionService notificacionService;

    /**
     * Crea el listener con el servicio de bandeja.
     *
     * @param service servicio de notificaciones
     */
    public SolicitudContactoCreadaListener(final NotificacionService service) {
        this.notificacionService = service;
    }

    /**
     * Persiste en la bandeja del agricultor la solicitud confirmada.
     *
     * @param event solicitud de contacto creada
     */
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void notificarAgricultor(final SolicitudContactoCreadaEvent event) {
        notificacionService.registrarSolicitudContacto(event);
        LOGGER.info("Notificación de contacto persistida para el agricultor {} por "
                        + "el producto {} y el pedido {}",
                event.getAgricultorId(),
                event.getProductoId(),
                event.getPedidoId());
    }
}
