package com.agrovalle.agrovalle_connect.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Agricultor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String ubicacionValle;
    private String cedula;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUbicacionValle() { return ubicacionValle; }
    public void setUbicacionValle(String ubicacionValle) { this.ubicacionValle = ubicacionValle; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
}