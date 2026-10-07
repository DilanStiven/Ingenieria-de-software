package com.agrovalle.agrovalle_connect.models;

/**
 * Representa una categoría disponible en el catálogo de productos.
 */
public class Categoria {

    private final String nombre;

    /**
     * Crea una categoría con su nombre.
     *
     * @param nombre nombre de la categoría
     */
    public Categoria(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return nombre de la categoría
     */
    public String getNombre() {
        return nombre;
    }
}
