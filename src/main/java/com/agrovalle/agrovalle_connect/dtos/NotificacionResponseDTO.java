package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.Notificacion;
import java.time.Instant;

/**
 * Notificación visible en la bandeja del agricultor autenticado.
 */
public class NotificacionResponseDTO {

    private final Long id;
    private final Long idPedido;
    private final Long idProducto;
    private final String mensaje;
    private final Instant fechaCreacion;

    /**
     * Construye la respuesta a partir de una notificación persistida.
     *
     * @param notificacion notificación
     */
    public NotificacionResponseDTO(final Notificacion notificacion) {
        this.id = notificacion.getId();
        this.idPedido = notificacion.getPedidoId();
        this.idProducto = notificacion.getProductoId();
        this.mensaje = notificacion.getMensaje();
        this.fechaCreacion = notificacion.getFechaCreacion();
    }

    /**
     * Obtiene el identificador de la notificación.
     *
     * @return identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el pedido asociado.
     *
     * @return identificador del pedido
     */
    public Long getIdPedido() {
        return idPedido;
    }

    /**
     * Obtiene el producto asociado.
     *
     * @return identificador del producto
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * Obtiene el mensaje que verá el agricultor.
     *
     * @return mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Obtiene la fecha de creación.
     *
     * @return instante de creación
     */
    public Instant getFechaCreacion() {
        return fechaCreacion;
    }
}
