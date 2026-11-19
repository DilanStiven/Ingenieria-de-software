package com.agrovalle.agrovalle_connect.events;

/**
 * Evento emitido al crear una solicitud de contacto y su pedido.
 */
public class SolicitudContactoCreadaEvent {

    private final Long pedidoId;
    private final Long productoId;
    private final Long agricultorId;
    private final String mensaje;

    /**
     * Crea un evento con los datos necesarios para notificar al agricultor.
     *
     * @param pedidoId identificador del pedido
     * @param productoId identificador del producto
     * @param agricultorId identificador del agricultor
     * @param mensaje mensaje enviado por el comprador
     */
    public SolicitudContactoCreadaEvent(final Long pedidoId,
            final Long productoId, final Long agricultorId, final String mensaje) {
        this.pedidoId = pedidoId;
        this.productoId = productoId;
        this.agricultorId = agricultorId;
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return identificador del pedido
     */
    public Long getPedidoId() {
        return pedidoId;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return identificador del producto
     */
    public Long getProductoId() {
        return productoId;
    }

    /**
     * Obtiene el identificador del agricultor notificado.
     *
     * @return identificador del agricultor
     */
    public Long getAgricultorId() {
        return agricultorId;
    }

    /**
     * Obtiene el mensaje enviado por el comprador.
     *
     * @return mensaje de negociación
     */
    public String getMensaje() {
        return mensaje;
    }
}
