package com.agrovalle.agrovalle_connect.dtos;

/**
 * Respuesta tras publicar un producto: contiene el ID asignado.
 */
public class ProductoResponseDTO {

    private Long id;
    private String mensaje;

    /**
     * Crea la respuesta con el ID del producto creado.
     *
     * @param id     identificador del producto
     * @param mensaje mensaje de confirmación
     */
    public ProductoResponseDTO(final Long id, final String mensaje) {
        this.id = id;
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return id del producto
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtiene el mensaje de confirmación.
     *
     * @return mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
}
