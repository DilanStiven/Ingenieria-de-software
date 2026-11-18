package com.agrovalle.agrovalle_connect.dtos;

/**
 * Confirmación de una solicitud de contacto y del pedido generado.
 */
public class ContactoMensajeResponseDTO {

    private Long idMensaje;
    private Long idPedido;
    private String mensaje;
    private boolean notificacionEnviada;

    /**
     * Crea la respuesta de una solicitud de contacto.
     *
     * @param idMensaje identificador del mensaje guardado
     * @param idPedido identificador del pedido generado
     * @param mensaje confirmación para el cliente
     * @param notificacionEnviada indica si se publicó la notificación interna
     */
    public ContactoMensajeResponseDTO(final Long idMensaje, final Long idPedido,
            final String mensaje, final boolean notificacionEnviada) {
        this.idMensaje = idMensaje;
        this.idPedido = idPedido;
        this.mensaje = mensaje;
        this.notificacionEnviada = notificacionEnviada;
    }

    /**
     * Obtiene el identificador del mensaje.
     *
     * @return identificador del mensaje
     */
    public Long getIdMensaje() {
        return idMensaje;
    }

    /**
     * Obtiene el identificador del pedido.
     *
     * @return identificador del pedido
     */
    public Long getIdPedido() {
        return idPedido;
    }

    /**
     * Obtiene el mensaje de confirmación.
     *
     * @return mensaje de confirmación
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Indica si se publicó la notificación interna.
     *
     * @return true si se publicó el evento interno de notificación
     */
    public boolean isNotificacionEnviada() {
        return notificacionEnviada;
    }
}
