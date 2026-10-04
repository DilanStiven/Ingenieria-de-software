package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "productos")
public class Producto {

    private static final int NOMBRE_MAX_LENGTH = 100;
    private static final int CATEGORIA_MAX_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = NOMBRE_MAX_LENGTH)
    private String nombre;

    @Column(nullable = false, length = CATEGORIA_MAX_LENGTH)
    private String categoria;

    @Column(nullable = false, length = NOMBRE_MAX_LENGTH)
    private String municipio;

    @Column(nullable = false)
    private Double cantidad;

    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;

    // Constructor vacío requerido por JPA
    public Producto() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(final String categoria) {
        this.categoria = categoria;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(final String municipio) {
        this.municipio = municipio;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(final Double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaCosecha() {
        return fechaCosecha;
    }

    public void setFechaCosecha(final LocalDate fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(final Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
