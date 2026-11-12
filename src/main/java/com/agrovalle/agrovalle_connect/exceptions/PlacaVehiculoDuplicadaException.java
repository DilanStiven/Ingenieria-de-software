package com.agrovalle.agrovalle_connect.exceptions;

/**
 * Indica que ya existe un vehículo con la placa informada.
 */
public class PlacaVehiculoDuplicadaException extends RuntimeException {

    /**
     * Crea la excepción para una placa duplicada.
     *
     * @param placa placa duplicada
     */
    public PlacaVehiculoDuplicadaException(final String placa) {
        super("Ya existe un vehículo registrado con la placa: " + placa);
    }
}
