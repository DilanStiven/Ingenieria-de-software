package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.Pago;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Datos necesarios para iniciar el pago de un pedido.
 */
public class PagoRequestDTO {

    @JsonProperty("id_pedido")
    @JsonAlias("idPedido")
    @NotNull(message = "El identificador del pedido es obligatorio")
    @Positive(message = "El identificador del pedido debe ser positivo")
    private Long idPedido;

    @JsonProperty("medio_pago")
    @JsonAlias("medioPago")
    @NotNull(message = "El medio de pago es obligatorio")
    private Pago.MedioPago medioPago;

    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(final Long pedidoId) {
        this.idPedido = pedidoId;
    }

    public Pago.MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(final Pago.MedioPago medioPagoValue) {
        this.medioPago = medioPagoValue;
    }
}
