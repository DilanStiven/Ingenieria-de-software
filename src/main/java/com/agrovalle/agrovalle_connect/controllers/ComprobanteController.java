package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.dtos.PedidoPagadoResponseDTO;
import com.agrovalle.agrovalle_connect.services.ComprobanteService;
import java.util.List;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API de historial de pedidos pagados y descarga de comprobantes (HU-10).
 */
@RestController
@RequestMapping("/api/v1/pedidos")
public class ComprobanteController {

    private final ComprobanteService comprobanteService;

    public ComprobanteController(final ComprobanteService service) {
        this.comprobanteService = service;
    }

    /**
     * Consulta el historial de compras pagadas del usuario autenticado.
     *
     * @param authentication autenticación con el identificador del comprador
     * @return historial de pedidos pagados
     */
    @GetMapping("/pagados")
    public ResponseEntity<List<PedidoPagadoResponseDTO>> obtenerHistorial(
            final Authentication authentication) {
        Long compradorId = Long.valueOf(authentication.getName());
        return ResponseEntity.ok(comprobanteService.obtenerHistorial(compradorId));
    }

    /**
     * Descarga el comprobante de una compra pagada del usuario autenticado.
     *
     * @param pedidoId identificador del pedido
     * @param authentication autenticación con el identificador del comprador
     * @return archivo PDF del comprobante
     */
    @GetMapping("/{pedidoId}/comprobante")
    public ResponseEntity<byte[]> descargarComprobante(
            @PathVariable final Long pedidoId,
            final Authentication authentication) {
        Long compradorId = Long.valueOf(authentication.getName());
        byte[] comprobante =
                comprobanteService.generarComprobante(pedidoId, compradorId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("comprobante-pedido-" + pedidoId + ".pdf")
                                .build()
                                .toString())
                .body(comprobante);
    }
}
