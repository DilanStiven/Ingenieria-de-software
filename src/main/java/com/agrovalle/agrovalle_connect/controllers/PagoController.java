package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.PagoRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.PagoResponseDTO;
import com.agrovalle.agrovalle_connect.services.PagoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API para procesar pagos de pedidos (HU-09).
 */
@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(final PagoService service) {
        this.pagoService = service;
    }

    /**
     * Procesa el pago de un pedido pendiente.
     *
     * @param solicitud datos del pago
     * @param authentication identidad autenticada
     * @return pago aprobado o rechazado
     */
    @PostMapping
    public ResponseEntity<PagoResponseDTO> pagar(
            @Valid @RequestBody final PagoRequestDTO solicitud,
            final Authentication authentication) {
        return ResponseEntity.ok(pagoService.procesar(
                solicitud.getIdPedido(),
                Long.valueOf(authentication.getName()),
                solicitud.getMedioPago()));
    }
}
