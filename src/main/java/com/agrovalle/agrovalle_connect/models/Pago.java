package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Transaccion de pago asociada a un pedido.
 */
@Entity
@Table(name = "pago",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_pago_referencia",
                columnNames = "referencia_pasarela"),
        indexes = {
                @Index(name = "idx_pago_pedido", columnList = "id_pedido"),
                @Index(name = "idx_pago_estado_fecha", columnList = "estado, fecha_creacion")
        })
public class Pago {

    private static final int ESTADO_LONGITUD_MAXIMA = 20;
    private static final int MEDIO_LONGITUD_MAXIMA = 20;
    private static final int REFERENCIA_LONGITUD_MAXIMA = 100;
    private static final int PRECISION_MONTO = 12;
    private static final int ESCALA_MONTO = 2;

    /**
     * Estados posibles de una transaccion de pago.
     */
    public enum Estado {
        PENDIENTE,
        APROBADO,
        RECHAZADO
    }

    /**
     * Medios de pago admitidos por la pasarela.
     */
    public enum MedioPago {
        TARJETA,
        PSE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false, precision = PRECISION_MONTO, scale = ESCALA_MONTO)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "medio_pago", nullable = false, length = MEDIO_LONGITUD_MAXIMA)
    private MedioPago medioPago;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = ESTADO_LONGITUD_MAXIMA)
    private Estado estado = Estado.PENDIENTE;

    @Column(name = "referencia_pasarela", length = REFERENCIA_LONGITUD_MAXIMA)
    private String referenciaPasarela;

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

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(final BigDecimal montoValue) {
        this.monto = montoValue;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(final MedioPago medioPagoValue) {
        this.medioPago = medioPagoValue;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(final Estado estadoValue) {
        this.estado = estadoValue;
    }

    public String getReferenciaPasarela() {
        return referenciaPasarela;
    }

    public void setReferenciaPasarela(final String referenciaPasarelaValue) {
        this.referenciaPasarela = referenciaPasarelaValue;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(final Instant fechaCreacionValue) {
        this.fechaCreacion = fechaCreacionValue;
    }
}
