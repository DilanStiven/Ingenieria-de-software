package com.agrovalle.agrovalle_connect.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ResenaRequestDTO {

    private static final int ESTRELLAS_MINIMAS = 1;
    private static final int ESTRELLAS_MAXIMAS = 5;
    private static final int COMENTARIO_LONGITUD_MAXIMA = 1000;

    @JsonProperty("id_orden_compra")
    @JsonAlias("idOrdenCompra")
    @Positive(message = "El identificador de la orden debe ser positivo")
    private Long idOrdenCompra;

    @NotNull(message = "La calificación es obligatoria")
    @Min(value = ESTRELLAS_MINIMAS, message = "La calificación mínima es 1")
    @Max(value = ESTRELLAS_MAXIMAS, message = "La calificación máxima es 5")
    private Integer estrellas;

    @Size(max = COMENTARIO_LONGITUD_MAXIMA,
            message = "El comentario no puede superar 1000 caracteres")
    private String comentario;

    public Long getIdOrdenCompra() {
        return idOrdenCompra;
    }

    public void setIdOrdenCompra(final Long idOrdenCompraValue) {
        this.idOrdenCompra = idOrdenCompraValue;
    }

    public Integer getEstrellas() {
        return estrellas;
    }

    public void setEstrellas(final Integer estrellasValue) {
        this.estrellas = estrellasValue;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(final String comentarioValue) {
        this.comentario = comentarioValue;
    }
}
