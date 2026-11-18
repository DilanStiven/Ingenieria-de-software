package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Billetera con el saldo disponible de un agricultor.
 */
@Entity
@Table(name = "billetera", uniqueConstraints = {
        @UniqueConstraint(name = "uk_billetera_agricultor", columnNames = "agricultor_id")
})
public class Billetera {

    private static final int PRECISION_SALDO = 12;
    private static final int ESCALA_SALDO = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agricultor_id", nullable = false)
    private Agricultor agricultor;

    @Column(nullable = false, precision = PRECISION_SALDO, scale = ESCALA_SALDO)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Column(name = "fecha_actualizacion", nullable = false)
    private Instant fechaActualizacion;

    /**
     * Inicializa la fecha de actualizacion antes de insertar.
     */
    @PrePersist
    protected void alCrear() {
        fechaActualizacion = Instant.now();
    }

    /**
     * Actualiza la fecha antes de modificar la billetera.
     */
    @PreUpdate
    protected void alActualizar() {
        fechaActualizacion = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long idValue) {
        this.id = idValue;
    }

    public Agricultor getAgricultor() {
        return agricultor;
    }

    public void setAgricultor(final Agricultor agricultorValue) {
        this.agricultor = agricultorValue;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(final BigDecimal saldoValue) {
        this.saldo = saldoValue;
    }

    public Instant getFechaActualizacion() {
        return fechaActualizacion;
    }
}
