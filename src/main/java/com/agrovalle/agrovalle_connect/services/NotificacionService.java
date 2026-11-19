package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.NotificacionResponseDTO;
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
}
