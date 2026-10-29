package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad que representa una categoría disponible en el catálogo de productos.
 */
@Entity
@Table(name = "categorias")
public class Categoria {

    private static final int NOMBRE_LONGITUD = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = NOMBRE_LONGITUD)
    private String nombre;

    /**
     * Constructor requerido por JPA.
     */
    protected Categoria() {
    }

    /**
     * Crea una categoría con su nombre.
     *
     * @param nombre nombre de la categoría
     */
    public Categoria(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador de la categoría.
     *
     * @return identificador de la categoría
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la categoría.
     *
     * @param idValue identificador de la categoría
     */
    public void setId(final Long idValue) {
        this.id = idValue;
    }

    /**
     * Obtiene el nombre de la categoría.
     *
     * @return nombre de la categoría
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la categoría.
     *
     * @param nombreValue nombre de la categoría
     */
    public void setNombre(final String nombreValue) {
        this.nombre = nombreValue;
    }
}
