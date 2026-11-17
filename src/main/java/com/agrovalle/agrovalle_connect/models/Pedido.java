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
import java.time.Instant;

/**
 * Intención de compra asociada a un producto.
 */
@Entity
@Table(name = "pedido",
        indexes = @Index(name = "idx_pedido_producto_estado", columnList = "id_producto, estado"))
public class Pedido {

    private static final int ESTADO_LONGITUD_MAXIMA = 30;

    /** Estados de pago disponibles para un pedido. */
    public enum Estado {
        PENDIENTE_PAGO,
        PAGADO,
        RECHAZADO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = ESTADO_LONGITUD_MAXIMA)
    private Estado estado = Estado.PENDIENTE_PAGO;

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
     * Obtiene el identificador del pedido.
     *
     * @return identificador del pedido
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del pedido.
     *
     * @param idValue identificador del pedido
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el producto solicitado.
     *
     * @return producto asociado
     */
    public Producto getProducto() {
        return producto;
    }

    /**
     * Asocia el producto solicitado.
     *
     * @param productoValue producto asociado
     */
    public void setProducto(final Producto productoValue) {
        this.producto = productoValue;
    }

    /**
     * Obtiene el estado del pedido.
     *
     * @return estado actual
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Establece el estado del pedido.
     *
     * @param estadoValue estado actual
     */
    public void setEstado(final Estado estadoValue) {
        this.estado = estadoValue;
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
