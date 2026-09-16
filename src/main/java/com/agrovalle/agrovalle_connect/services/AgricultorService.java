package com.agrovalle.agrovalle_connect.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;

@Service
public class AgricultorService {

    @Autowired
    private AgricultorRepository agricultorRepository;

    public Agricultor registrar(Agricultor agricultor) {
        return agricultorRepository.save(agricultor);
    }
}