package com.agrovalle.agrovalle_connect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;

/**
 * Servicio encargado de gestionar los agricultores.
 */
@Service
public class AgricultorService {

    /**
     * Repositorio utilizado para guardar agricultores.
     */
    @Autowired
    private AgricultorRepository agricultorRepository;

    /**
     * Registra un nuevo agricultor.
     *
     * @param agricultor agricultor que será registrado
     * @return agricultor registrado
     */
    public final Agricultor registrar(final Agricultor agricultor) {
        return agricultorRepository.save(agricultor);
    }
}
