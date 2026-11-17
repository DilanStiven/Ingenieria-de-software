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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Mensaje de negociación asociado a un pedido.
 */
@Entity
@Table(name = "contacto_mensaje",
        indexes = @Index(
                name = "idx_contacto_mensaje_pedido_fecha",
                columnList = "id_pedido, fecha_creacion"))
public class ContactoMensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private Instant fechaCreacion;

    /**
     * Asigna la fecha de creación si aún no fue establecida.
     */
    @PrePersist
    protected void alCrear() {
        if (fechaCreacion == null) {
            fechaCreacion = Instant.now();
        }
    }

    /**
     * Obtiene el identificador del mensaje.
     *
     * @return identificador del mensaje
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del mensaje.
     *
     * @param idValue identificador del mensaje
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el pedido asociado.
     *
     * @return pedido asociado
     */
    public Pedido getPedido() {
        return pedido;
    }

    /**
     * Asocia el mensaje a un pedido.
     *
     * @param pedidoValue pedido asociado
     */
    public void setPedido(final Pedido pedidoValue) {
        this.pedido = pedidoValue;
    }

    /**
     * Obtiene el contenido del mensaje.
     *
     * @return contenido del mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el contenido del mensaje.
     *
     * @param mensajeValue contenido del mensaje
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
