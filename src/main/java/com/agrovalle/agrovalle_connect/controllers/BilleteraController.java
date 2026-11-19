package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.BilleteraResponseDTO;
import com.agrovalle.agrovalle_connect.services.BilleteraService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API de consulta de saldo de la billetera del agricultor.
 */
@RestController
@RequestMapping("/api/v1/billetera")
public class BilleteraController {

    private final BilleteraService billeteraService;

    public BilleteraController(final BilleteraService service) {
        this.billeteraService = service;
    }

    /**
     * Consulta el saldo disponible del agricultor autenticado.
     *
     * @param authentication autenticacion JWT
     * @return saldo disponible
     */
    @GetMapping("/saldo")
    public ResponseEntity<BilleteraResponseDTO> consultarSaldo(
            final Authentication authentication) {
        Long agricultorId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(billeteraService.consultarSaldo(agricultorId));
    }
}
