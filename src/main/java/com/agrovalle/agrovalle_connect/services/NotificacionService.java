package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.NotificacionResponseDTO;
import com.agrovalle.agrovalle_connect.events.LlegadaDestinoEvent;
import com.agrovalle.agrovalle_connect.events.SolicitudContactoCreadaEvent;
import com.agrovalle.agrovalle_connect.models.Notificacion;
import com.agrovalle.agrovalle_connect.repositories.NotificacionRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Gestiona la bandeja de notificaciones de los agricultores.
 */
@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    /**
     * Crea el servicio con el repositorio de notificaciones.
     *
     * @param notificaciones repositorio de notificaciones
     */
    public NotificacionService(final NotificacionRepository notificaciones) {
        this.notificacionRepository = notificaciones;
    }

    /**
     * Registra en la bandeja del agricultor una solicitud de contacto.
     *
     * @param evento datos de la solicitud creada
     */
    @Transactional
    public void registrarSolicitudContacto(final SolicitudContactoCreadaEvent evento) {
        Notificacion notificacion = new Notificacion();
        notificacion.setAgricultorId(evento.getAgricultorId());
        notificacion.setPedidoId(evento.getPedidoId());
        notificacion.setProductoId(evento.getProductoId());
        notificacion.setMensaje("Nueva solicitud de compra para el producto "
                + evento.getProductoId() + ", pedido " + evento.getPedidoId()
                + ": " + evento.getMensaje());
        notificacionRepository.save(notificacion);
    }

    /**
     * Guarda en la bandeja del comprador el aviso de llegada del pedido.
     *
     * @param evento datos de la llegada y del comprador
     */
    @Transactional
    public void registrarLlegadaComprador(final LlegadaDestinoEvent evento) {
        Notificacion notificacion = new Notificacion();
        notificacion.setCompradorId(evento.getCompradorId());
        notificacion.setPedidoId(evento.getPedidoId());
        notificacion.setProductoId(evento.getProductoId());
        notificacion.setMensaje("El pedido " + evento.getPedidoId()
                + " llegó a " + evento.getUbicacion()
                + " y está por ser entregado.");
        notificacionRepository.save(notificacion);
    }

    /**
     * Lista únicamente las notificaciones del agricultor autenticado.
     *
     * @param agricultorId identificador del destinatario
     * @return notificaciones desde la más reciente
     */
    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> listarPorAgricultor(final Long agricultorId) {
        return notificacionRepository
                .findByAgricultorIdOrderByFechaCreacionDesc(agricultorId)
                .stream()
                .map(NotificacionResponseDTO::new)
                .toList();
    }

    /**
     * Lista únicamente las notificaciones del comprador autenticado.
     *
     * @param compradorId identificador del destinatario
     * @return notificaciones desde la más reciente
     */
    @Transactional(readOnly = true)
    public List<NotificacionResponseDTO> listarPorComprador(final Long compradorId) {
        return notificacionRepository
                .findByCompradorIdOrderByFechaCreacionDesc(compradorId)
                .stream()
                .map(NotificacionResponseDTO::new)
                .toList();
    }
}
