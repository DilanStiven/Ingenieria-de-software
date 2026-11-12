package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * Precio unitario observado en una transacción completada.
 */
@Entity
@Table(name = "transaccion_precio",
        check = @CheckConstraint(
                name = "ck_transaccion_precio_no_negativo",
                constraint = "precio_unitario >= 0"))
public class Precio {

    private static final int PRODUCTO_LONGITUD_MAXIMA = 100;
    private static final int PRECISION_PRECIO = 12;
    private static final int ESCALA_PRECIO = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = PRODUCTO_LONGITUD_MAXIMA)
    private String producto;

    @Column(name = "precio_unitario", nullable = false,
            precision = PRECISION_PRECIO, scale = ESCALA_PRECIO)
    private BigDecimal precioUnitario;

    @Column(name = "fecha_transaccion", nullable = false)
    private Instant fechaTransaccion;

    /**
     * Obtiene el identificador de la transacción.
     *
     * @return identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la transacción.
     *
     * @param idValue identificador
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el nombre del producto transado.
     *
     * @return nombre del producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * Establece el nombre del producto transado.
     *
     * @param productoValue nombre del producto
     */
    public void setProducto(final String productoValue) {
        this.producto = productoValue;
    }

    /**
     * Obtiene el precio unitario en pesos colombianos por kilogramo.
     *
     * @return precio unitario
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Establece el precio unitario en pesos colombianos por kilogramo.
     *
     * @param precioUnitarioValue precio unitario
     */
    public void setPrecioUnitario(final BigDecimal precioUnitarioValue) {
        this.precioUnitario = precioUnitarioValue;
    }

    /**
     * Obtiene la fecha y hora UTC de la transacción completada.
     *
     * @return instante de la transacción
     */
    public Instant getFechaTransaccion() {
        return fechaTransaccion;
    }

    /**
     * Establece la fecha y hora UTC de la transacción.
     *
     * @param fechaTransaccionValue instante de la transacción
     */
    public void setFechaTransaccion(final Instant fechaTransaccionValue) {
        this.fechaTransaccion = fechaTransaccionValue;
    }
}
