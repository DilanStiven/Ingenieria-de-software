package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

/**
 * Respuesta de consulta y operación de una orden logística.
 */
public class OrdenLogisticaResponseDTO {

    private final Long id;
    private final String producto;
    private final BigDecimal pesoKg;
    private final String destino;
    private final OrdenLogistica.Estado estado;
    private final Instant fechaCreacion;
    private final LocalDate fechaSalida;
    private final String guiaDespacho;
    private final Long vehiculoId;
    private final String placaVehiculo;

    /**
     * Crea el DTO a partir de una orden persistida.
     *
     * @param orden orden logística
     */
    public OrdenLogisticaResponseDTO(final OrdenLogistica orden) {
        this.id = orden.getId();
        this.producto = orden.getProducto();
        this.pesoKg = orden.getPesoKg();
        this.destino = orden.getDestino();
        this.estado = orden.getEstado();
        this.fechaCreacion = orden.getFechaCreacion();
        this.fechaSalida = orden.getFechaSalida();
        this.guiaDespacho = orden.getGuiaDespacho();
        this.vehiculoId = orden.getVehiculo() == null
                ? null : orden.getVehiculo().getId();
        this.placaVehiculo = orden.getVehiculo() == null
                ? null : orden.getVehiculo().getPlaca();
    }

    /**
     * Obtiene el identificador de la orden.
     *
     * @return identificador
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el producto.
     *
     * @return producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * Obtiene el peso.
     *
     * @return peso en kg
     */
    public BigDecimal getPesoKg() {
        return pesoKg;
    }

    /**
     * Obtiene el destino.
     *
     * @return destino
     */
    public String getDestino() {
        return destino;
    }

    /**
     * Obtiene el estado.
     *
     * @return estado de la orden
     */
    public OrdenLogistica.Estado getEstado() {
        return estado;
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
     * Obtiene la fecha de salida programada.
     *
     * @return fecha de salida o null
     */
    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    /**
     * Obtiene la guía generada.
     *
     * @return guía o null
     */
    public String getGuiaDespacho() {
        return guiaDespacho;
    }

    /**
     * Obtiene el identificador del vehículo asignado.
     *
     * @return vehículo o null
     */
    public Long getVehiculoId() {
        return vehiculoId;
    }

    /**
     * Obtiene la placa del vehículo asignado.
     *
     * @return placa o null
     */
    public String getPlacaVehiculo() {
        return placaVehiculo;
    }
}
