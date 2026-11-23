package com.agrovalle.agrovalle_connect.dtos;

import com.agrovalle.agrovalle_connect.models.Resena;
import java.math.BigDecimal;
import java.time.Instant;

public class ResenaResponseDTO {

    private final Long id;
    private final Long idOrdenCompra;
    private final Integer estrellas;
    private final String comentario;
    private final BigDecimal calificacionPromedioAgricultor;
    private final Instant fechaCreacion;

    public ResenaResponseDTO(final Resena resena,
            final BigDecimal calificacionPromedio) {
        this.id = resena.getId();
        this.idOrdenCompra = resena.getOrdenCompra().getId();
        this.estrellas = resena.getEstrellas();
        this.comentario = resena.getComentario();
        this.calificacionPromedioAgricultor = calificacionPromedio;
        this.fechaCreacion = resena.getFechaCreacion();
    }

    public Long getId() {
        return id;
    }

    public Long getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public Integer getEstrellas() {
        return estrellas;
    }

    public String getComentario() {
        return comentario;
    }

    public BigDecimal getCalificacionPromedioAgricultor() {
        return calificacionPromedioAgricultor;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }
}
