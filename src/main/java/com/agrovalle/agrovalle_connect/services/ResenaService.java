package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.ResenaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.ResenaResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.ResenaInvalidaException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Resena;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import com.agrovalle.agrovalle_connect.repositories.ResenaRepository;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResenaService {

    private final ResenaRepository resenaRepository;
    private final OrdenCompraRepository ordenCompraRepository;
    private final OrdenLogisticaRepository ordenLogisticaRepository;
    private final AgricultorRepository agricultorRepository;
    private final ReputacionService reputacionService;

    public ResenaService(final ResenaRepository resenas,
            final OrdenCompraRepository ordenes,
            final OrdenLogisticaRepository ordenesLogisticas,
            final AgricultorRepository agricultores,
            final ReputacionService reputacion) {
        this.resenaRepository = resenas;
        this.ordenCompraRepository = ordenes;
        this.ordenLogisticaRepository = ordenesLogisticas;
        this.agricultorRepository = agricultores;
        this.reputacionService = reputacion;
    }

    @Transactional
    public ResenaResponseDTO guardar(final ResenaRequestDTO dto,
            final Long compradorId) {
        OrdenCompra orden = ordenCompraRepository.findById(dto.getIdOrdenCompra())
                .orElseThrow(() -> new ResenaInvalidaException(
                        "La orden de compra no existe", HttpStatus.NOT_FOUND));

        if (!orden.getPedido().getCompradorId().equals(compradorId)) {
            throw new ResenaInvalidaException(
                    "La orden no pertenece al comprador autenticado",
                    HttpStatus.FORBIDDEN);
        }

        if (resenaRepository.findByOrdenCompraId(orden.getId()).isPresent()) {
            throw new ResenaInvalidaException(
                    "La orden ya tiene una reseña registrada",
                    HttpStatus.CONFLICT);
        }

        OrdenLogistica entrega = ordenLogisticaRepository
                .findByPedidoId(orden.getPedido().getId())
                .orElseThrow(() -> new ResenaInvalidaException(
                        "La orden todavía no tiene información de entrega",
                        HttpStatus.CONFLICT));
        if (entrega.getEstado() != OrdenLogistica.Estado.ENTREGADA) {
            throw new ResenaInvalidaException(
                    "Solo se puede calificar una orden entregada",
                    HttpStatus.CONFLICT);
        }

        Agricultor agricultor = orden.getPedido().getProducto().getAgricultor();
        Resena resena = new Resena();
        resena.setOrdenCompra(orden);
        resena.setCompradorId(compradorId);
        resena.setAgricultor(agricultor);
        resena.setEstrellas(dto.getEstrellas());
        resena.setComentario(dto.getComentario() == null
                ? null : dto.getComentario().trim());
        Resena guardada = resenaRepository.save(resena);

        BigDecimal promedioDecimal =
                reputacionService.calcularPromedioEstrellas(agricultor.getId());
        agricultor.setCalificacionPromedio(promedioDecimal);
        agricultorRepository.save(agricultor);

        return new ResenaResponseDTO(guardada, promedioDecimal);
    }

    /**
     * Guarda una reseña usando el identificador de orden incluido en la ruta.
     *
     * @param ordenId identificador de la orden de compra
     * @param dto calificación y comentario
     * @param compradorId comprador autenticado
     * @return reseña persistida
     */
    @Transactional
    public ResenaResponseDTO guardarPorOrden(final Long ordenId,
            final ResenaRequestDTO dto, final Long compradorId) {
        dto.setIdOrdenCompra(ordenId);
        return guardar(dto, compradorId);
    }
}
