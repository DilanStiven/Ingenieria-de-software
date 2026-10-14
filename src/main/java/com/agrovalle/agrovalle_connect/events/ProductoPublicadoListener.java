package com.agrovalle.agrovalle_connect.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Observador que reacciona de forma desacoplada al evento ProductoPublicadoEvent (Observer - GoF).
 */
@Component
public class ProductoPublicadoListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductoPublicadoListener.class);

    /**
     * Procesa la notificación cuando se publica un nuevo producto.
     *
     * @param event evento de producto publicado
     */
    @EventListener
    public void onProductoPublicado(final ProductoPublicadoEvent event) {
        LOGGER.info("[OBSERVER EVENT] Notificación: Nuevo producto disponible - ID: {}, Nombre: '{}', Municipio: {}, Cantidad: {} kg",
                event.getProductoId(), event.getNombre(), event.getMunicipio(), event.getCantidad());
    }
}
