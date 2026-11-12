package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.Vehiculo;

/**
 * Datos devueltos al registrar un vehículo.
 */
public class VehiculoResponseDTO {

    private final Long id;
    private final String placa;
    private final String tipo;
    private final Integer capacidadKg;
    private final Vehiculo.Estado estado;

    /**
     * Crea la respuesta del vehículo registrado.
     *
     * @param idValue identificador del vehículo
     * @param placaValue placa normalizada
     * @param tipoValue tipo del vehículo
     * @param capacidadKgValue capacidad en kg
     * @param estadoValue estado del vehículo
     */
    public VehiculoResponseDTO(final Long idValue, final String placaValue,
            final String tipoValue, final Integer capacidadKgValue,
            final Vehiculo.Estado estadoValue) {
        this.id = idValue;
        this.placa = placaValue;
        this.tipo = tipoValue;
        this.capacidadKg = capacidadKgValue;
        this.estado = estadoValue;
    }

    /**
     * Crea una respuesta a partir de la entidad.
     *
     * @param vehiculo vehículo persistido
     * @return DTO de respuesta
     */
    public static VehiculoResponseDTO desde(final Vehiculo vehiculo) {
        return new VehiculoResponseDTO(vehiculo.getId(), vehiculo.getPlaca(),
                vehiculo.getTipo(), vehiculo.getCapacidadKg(), vehiculo.getEstado());
    }

    /**
     * Obtiene el identificador.
     *
     * @return identificador del vehículo
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene la placa.
     *
     * @return placa normalizada
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * Obtiene el tipo.
     *
     * @return tipo de vehículo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Obtiene la capacidad en kg.
     *
     * @return capacidad en kg
     */
    public Integer getCapacidadKg() {
        return capacidadKg;
    }

    /**
     * Obtiene el estado.
     *
     * @return estado del vehículo
     */
    public Vehiculo.Estado getEstado() {
        return estado;
    }
}
