package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.Instant;

@Entity
@Table(name = "resena", uniqueConstraints =
        @UniqueConstraint(name = "uk_resena_orden", columnNames = "id_orden_compra"))
public class Resena {

    private static final int COMENTARIO_LONGITUD_MAXIMA = 1000;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_orden_compra", nullable = false)
    private OrdenCompra ordenCompra;

    @Column(name = "id_comprador", nullable = false)
    private Long compradorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_agricultor", nullable = false)
    private Agricultor agricultor;

    @Column(nullable = false)
    private Integer estrellas;

    @Column(length = COMENTARIO_LONGITUD_MAXIMA)
    private String comentario;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    @PrePersist
    protected void alCrear() {
        if (fechaCreacion == null) {
            fechaCreacion = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public OrdenCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(final OrdenCompra ordenCompraValue) {
        this.ordenCompra = ordenCompraValue;
    }

    public Long getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(final Long compradorIdValue) {
        this.compradorId = compradorIdValue;
    }

    public Agricultor getAgricultor() {
        return agricultor;
    }

    public void setAgricultor(final Agricultor agricultorValue) {
        this.agricultor = agricultorValue;
    }

    public Integer getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(final Integer estrellasValue) {
        this.estrellas = estrellasValue;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(final String comentarioValue) {
        this.comentario = comentarioValue;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(final Instant fechaCreacionValue) {
        this.fechaCreacion = fechaCreacionValue;
    }
}
