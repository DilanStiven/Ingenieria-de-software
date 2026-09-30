package com.agrovalle.agrovalle_connect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.services.AgricultorService;

/**
 * Controlador REST para el registro de agricultores.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AgricultorController {

    /**
     * Servicio utilizado para registrar agricultores.
     */
    @Autowired
    private AgricultorService agricultorService;

    /**
     * Registra un nuevo agricultor.
     *
     * @param agricultor datos del agricultor
     * @return respuesta con el agricultor registrado
     */
    @PostMapping("/register")
    public final ResponseEntity<Agricultor> registrar(
            final @RequestBody Agricultor agricultor) {
        Agricultor nuevo = agricultorService.registrar(agricultor);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
}
