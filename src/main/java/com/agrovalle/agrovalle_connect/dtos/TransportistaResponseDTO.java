package com.agrovalle.agrovalle_connect.dtos;

/**
 * Datos devueltos al registrar un transportista.
 */
public class TransportistaResponseDTO {

    private final Long id;
    private final String nombre;
    private final String cedula;
    private final String token;

    /**
     * Crea la respuesta del registro de transportista.
     *
     * @param idValue identificador asignado
     * @param nombreValue nombre del transportista
     * @param cedulaValue cédula del transportista
     * @param tokenValue token JWT emitido
     */
    public TransportistaResponseDTO(final Long idValue, final String nombreValue,
            final String cedulaValue, final String tokenValue) {
        this.id = idValue;
        this.nombre = nombreValue;
        this.cedula = cedulaValue;
        this.token = tokenValue;
    }

    /**
     * Obtiene el identificador.
     *
     * @return identificador del transportista
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre.
     *
     * @return nombre del transportista
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la cédula.
     *
     * @return cédula del transportista
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Obtiene el token de acceso.
     *
     * @return token JWT
     */
    public String getToken() {
        return token;
    }
}
