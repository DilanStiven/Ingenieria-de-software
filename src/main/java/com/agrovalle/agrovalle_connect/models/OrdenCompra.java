package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Orden oficial generada despues de confirmar un pago.
 */
@Entity
@Table(name = "orden_compra",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_orden_compra_numero",
                        columnNames = "numero_orden"),
                @UniqueConstraint(
                        name = "uk_orden_compra_pago",
                        columnNames = "id_pago")
        },
        indexes = @Index(name = "idx_orden_compra_pedido", columnList = "id_pedido"))
public class OrdenCompra {

    private static final int NUMERO_LONGITUD_MAXIMA = 40;
    private static final int PRECISION_MONTO = 12;
    private static final int ESCALA_MONTO = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pago", nullable = false)
    private Pago pago;

    @Column(name = "numero_orden", nullable = false, length = NUMERO_LONGITUD_MAXIMA)
    private String numeroOrden;

    @Column(name = "monto_total", nullable = false, precision = PRECISION_MONTO,
            scale = ESCALA_MONTO)
    private BigDecimal montoTotal;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    /**
     * Asigna la fecha de creacion si aun no fue establecida.
     */
    @PrePersist
    protected void alCrear() {
        if (fechaCreacion == null) {
            fechaCreacion = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long idValue) {
        this.id = idValue;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(final Pedido pedidoValue) {
        this.pedido = pedidoValue;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(final Pago pagoValue) {
        this.pago = pagoValue;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(final String numeroOrdenValue) {
        this.numeroOrden = numeroOrdenValue;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(final BigDecimal montoTotalValue) {
        this.montoTotal = montoTotalValue;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(final Instant fechaCreacionValue) {
        this.fechaCreacion = fechaCreacionValue;
    }
}
