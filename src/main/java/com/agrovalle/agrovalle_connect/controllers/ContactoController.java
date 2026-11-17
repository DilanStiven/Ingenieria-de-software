package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.ContactoMensajeRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.ContactoMensajeResponseDTO;
import com.agrovalle.agrovalle_connect.services.ContactoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API para enviar solicitudes de contacto sobre ofertas (HU-05).
 */
@RestController
@RequestMapping("/api/v1/contacto")
public class ContactoController {

    private final ContactoService contactoService;

    /**
     * Crea el controlador de contacto.
     *
     * @param service servicio que registra la solicitud
     */
    public ContactoController(final ContactoService service) {
        this.contactoService = service;
    }

    /**
     * Envía un mensaje de contacto para una oferta.
     *
     * @param solicitud datos de la solicitud
     * @return confirmación de contacto con HTTP 200
     */
    @PostMapping("/mensaje")
    public ResponseEntity<ContactoMensajeResponseDTO> enviarMensaje(
            @Valid @RequestBody final ContactoMensajeRequestDTO solicitud) {
        return ResponseEntity.ok(contactoService.enviarMensaje(
                solicitud.getIdProducto(), solicitud.getMensaje()));
    }
}
