package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Resumen de un pedido pagado para el historial del comprador.
 */
public class PedidoPagadoResponseDTO {

    private final Long idPedido;
    private final String numeroOrden;
    private final String nombreProducto;
    private final String categoria;
    private final String municipio;
    private final BigDecimal cantidad;
    private final BigDecimal monto;
    private final Pago.MedioPago medioPago;
    private final Instant fechaPago;

    public PedidoPagadoResponseDTO(final OrdenCompra orden) {
        this.idPedido = orden.getPedido().getId();
        this.numeroOrden = orden.getNumeroOrden();
        this.nombreProducto = orden.getPedido().getProducto().getNombre();
        this.categoria = orden.getPedido().getProducto().getCategoria();
        this.municipio = orden.getPedido().getProducto().getMunicipio();
        this.cantidad = orden.getPedido().getCantidad();
        this.monto = orden.getPago().getMonto();
        this.medioPago = orden.getPago().getMedioPago();
        this.fechaPago = orden.getPago().getFechaCreacion();
    }

    public Long getIdPedido() {
        return idPedido;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getMunicipio() {
        return municipio;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public Pago.MedioPago getMedioPago() {
        return medioPago;
    }

    public Instant getFechaPago() {
        return fechaPago;
    }
}
