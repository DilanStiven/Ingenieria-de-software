package com.agrovalle.agrovalle_connect.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Datos para solicitar contacto sobre una oferta.
 */
public class ContactoMensajeRequestDTO {

    private static final int CANTIDAD_ENTERA_MAXIMA = 8;
    private static final int ESCALA_CANTIDAD = 2;

    @JsonProperty("id_producto")
    @NotNull(message = "El identificador del producto es obligatorio")
    @Positive(message = "El identificador del producto debe ser positivo")
    private Long idProducto;

    @JsonProperty("cantidad")
    @NotNull(message = "La cantidad solicitada es obligatoria")
    @Positive(message = "La cantidad solicitada debe ser mayor que cero")
    @Digits(integer = CANTIDAD_ENTERA_MAXIMA, fraction = ESCALA_CANTIDAD,
            message = "La cantidad admite hasta 8 enteros y 2 decimales")
    private BigDecimal cantidad;

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
     * Obtiene la cantidad solicitada en kilogramos.
     *
     * @return cantidad solicitada
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad solicitada en kilogramos.
     *
     * @param cantidadValue cantidad solicitada
     */
    public void setCantidad(final BigDecimal cantidadValue) {
        this.cantidad = cantidadValue;
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
