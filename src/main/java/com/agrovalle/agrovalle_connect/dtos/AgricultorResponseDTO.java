package com.agrovalle.agrovalle_connect.dtos;

/**
 * Datos de salida tras registrar o consultar un agricultor.
 */
public class AgricultorResponseDTO {

    private Long id;
    private String nombre;
    private String ubicacionValle;
    private String cedula;
    private String token;

      /**
     * Crea la respuesta con todos sus campos.
     *
     * @param id identificador
     * @param nombre nombre del agricultor
     * @param ubicacionValle ubicación en el Valle
     * @param cedula cédula del agricultor
     * @param token token de acceso JWT
     */
    public AgricultorResponseDTO(Long id, String nombre, String ubicacionValle,
            String cedula, String token) {
        this.id = id;
        this.nombre = nombre;
        this.ubicacionValle = ubicacionValle;
        this.cedula = cedula;
        this.token = token;
    }

    /**
     * Obtiene el identificador.
     *
     * @return id del agricultor
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el nombre.
     *
     * @return nombre del agricultor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la ubicación.
     *
     * @return ubicación en el Valle
     */
    public String getUbicacionValle() {
        return ubicacionValle;
    }

    /**
     * Obtiene la cédula.
     *
     * @return cédula del agricultor
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
