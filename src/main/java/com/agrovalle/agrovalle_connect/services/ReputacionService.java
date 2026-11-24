package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.ReputacionResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.AgricultorNoEncontradoException;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.ResenaRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Calcula la reputación promedio de un agricultor a partir de sus reseñas.
 */
@Service
public class ReputacionService {

    private static final int ESCALA_PROMEDIO = 2;

    private final AgricultorRepository agricultorRepository;
    private final ResenaRepository resenaRepository;

    /**
     * Crea el servicio con los repositorios de agricultores y reseñas.
     *
     * @param agricultores repositorio de agricultores
     * @param resenas repositorio de reseñas
     */
    public ReputacionService(
            final AgricultorRepository agricultores,
            final ResenaRepository resenas) {
        this.agricultorRepository = agricultores;
        this.resenaRepository = resenas;
    }

    /**
     * Obtiene la reputación pública de un agricultor existente.
     *
     * @param agricultorId identificador del agricultor
     * @return identificador y promedio de estrellas
     */
    @Transactional(readOnly = true)
    public ReputacionResponseDTO obtenerReputacion(final Long agricultorId) {
        if (!agricultorRepository.existsById(agricultorId)) {
            throw new AgricultorNoEncontradoException(agricultorId);
        }
        return new ReputacionResponseDTO(
                agricultorId, calcularPromedioEstrellas(agricultorId));
    }

    /**
     * Calcula el promedio de estrellas de todas las reseñas de un agricultor.
     *
     * @param agricultorId identificador del agricultor
     * @return promedio con dos decimales, o cero si aún no tiene reseñas
     */
    @Transactional(readOnly = true)
    public BigDecimal calcularPromedioEstrellas(final Long agricultorId) {
        Double promedio = resenaRepository.calcularPromedioPorAgricultor(agricultorId);
        if (promedio == null) {
            return BigDecimal.ZERO.setScale(ESCALA_PROMEDIO);
        }
        return BigDecimal.valueOf(promedio)
                .setScale(ESCALA_PROMEDIO, RoundingMode.HALF_UP);
    }
}
