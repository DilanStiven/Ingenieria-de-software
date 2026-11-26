package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Notificación en la bandeja de un agricultor.
 */
@Entity
@Table(name = "notificacion",
        indexes = {
                @Index(name = "idx_notificacion_agricultor_fecha",
                        columnList = "agricultor_id, fecha_creacion"),
                @Index(name = "idx_notificacion_comprador_fecha",
                        columnList = "comprador_id, fecha_creacion")
        },
        check = @CheckConstraint(
                name = "ck_notificacion_destinatario",
                constraint = "((agricultor_id IS NOT NULL AND comprador_id IS NULL)"
                        + " OR (agricultor_id IS NULL AND comprador_id IS NOT NULL))"))
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agricultor_id")
    private Long agricultorId;

    @Column(name = "comprador_id")
    private Long compradorId;

    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @PrePersist
    protected void alCrear() {
        if (fechaCreacion == null) {
            fechaCreacion = Instant.now();
        }
    }

    /**
     * Obtiene el identificador de la notificación.
     *
     * @return identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la notificación.
     *
     * @param idValue identificador
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el destinatario agricultor.
     *
     * @return identificador del agricultor
     */
    public Long getAgricultorId() {
        return agricultorId;
    }

    /**
     * Establece el destinatario agricultor.
     *
     * @param agricultorIdValue identificador del agricultor
     */
    public void setAgricultorId(final Long agricultorIdValue) {
        this.agricultorId = agricultorIdValue;
    }

    /**
     * Obtiene el identificador del comprador destinatario.
     *
     * @return identificador del comprador o null para notificaciones del agricultor
     */
    public Long getCompradorId() {
        return compradorId;
    }

    /**
     * Establece el comprador destinatario.
     *
     * @param compradorIdValue identificador del comprador
     */
    public void setCompradorId(final Long compradorIdValue) {
        this.compradorId = compradorIdValue;
    }

    /**
     * Obtiene el pedido asociado.
     *
     * @return identificador del pedido
     */
    public Long getPedidoId() {
        return pedidoId;
    }

    /**
     * Establece el pedido asociado.
     *
     * @param pedidoIdValue identificador del pedido
     */
    public void setPedidoId(final Long pedidoIdValue) {
        this.pedidoId = pedidoIdValue;
    }

    /**
     * Obtiene el producto asociado.
     *
     * @return identificador del producto
     */
    public Long getProductoId() {
        return productoId;
    }

    /**
     * Establece el producto asociado.
     *
     * @param productoIdValue identificador del producto
     */
    public void setProductoId(final Long productoIdValue) {
        this.productoId = productoIdValue;
    }

    /**
     * Obtiene el contenido de la notificación.
     *
     * @return mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el contenido de la notificación.
     *
     * @param mensajeValue mensaje
     */
    public void setMensaje(final String mensajeValue) {
        this.mensaje = mensajeValue;
    }

    /**
     * Obtiene la fecha de creación.
     *
     * @return instante de creación
     */
    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * Establece la fecha de creación.
     *
     * @param fechaCreacionValue instante de creación
     */
    public void setFechaCreacion(final Instant fechaCreacionValue) {
        this.fechaCreacion = fechaCreacionValue;
    }
}
