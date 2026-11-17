package com.agrovalle.agrovalle_connect.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Datos para solicitar contacto sobre una oferta.
 */
public class ContactoMensajeRequestDTO {

    @JsonProperty("id_producto")
    @NotNull(message = "El identificador del producto es obligatorio")
    @Positive(message = "El identificador del producto debe ser positivo")
    private Long idProducto;

    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;

    /**
     * Obtiene el identificador de la oferta solicitada.
     *
     * @return identificador del producto
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el identificador de la oferta solicitada.
     *
     * @param productoId identificador del producto
     */
    public void setIdProducto(final Long productoId) {
        this.idProducto = productoId;
    }

    /**
     * Obtiene el mensaje de negociación.
     *
     * @return mensaje enviado
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje de negociación.
     *
     * @param contenido mensaje enviado
     */
    public void setMensaje(final String contenido) {
        this.mensaje = contenido;
    }
}
