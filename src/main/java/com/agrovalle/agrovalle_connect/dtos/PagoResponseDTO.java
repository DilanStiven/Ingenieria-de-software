package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.Pago;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Resultado de procesar el pago de un pedido.
 */
public class PagoResponseDTO {

    private final Long idPago;
    private final Long idPedido;
    private final Long idOrdenCompra;
    private final String numeroOrden;
    private final BigDecimal monto;
    private final Pago.MedioPago medioPago;
    private final Pago.Estado estado;
    private final String referenciaPasarela;
    private final String mensaje;
    private final Instant fechaCreacion;
    private final Instant fechaLimiteReintento;

    public PagoResponseDTO(final Pago pago, final String numeroOrdenValue,
            final Long ordenCompraId, final String mensajeValue,
            final Instant fechaLimiteReintentoValue) {
        this.idPago = pago.getId();
        this.idPedido = pago.getPedido().getId();
        this.idOrdenCompra = ordenCompraId;
        this.numeroOrden = numeroOrdenValue;
        this.monto = pago.getMonto();
        this.medioPago = pago.getMedioPago();
        this.estado = pago.getEstado();
        this.referenciaPasarela = pago.getReferenciaPasarela();
        this.mensaje = mensajeValue;
        this.fechaCreacion = pago.getFechaCreacion();
        this.fechaLimiteReintento = fechaLimiteReintentoValue;
    }

    public Long getIdPago() {
        return idPago;
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public Long getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public Pago.MedioPago getMedioPago() {
        return medioPago;
    }

    public Pago.Estado getEstado() {
        return estado;
    }

    public String getReferenciaPasarela() {
        return referenciaPasarela;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Instant getFechaLimiteReintento() {
        return fechaLimiteReintento;
    }
}
