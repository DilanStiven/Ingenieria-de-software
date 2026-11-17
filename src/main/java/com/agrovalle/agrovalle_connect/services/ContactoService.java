package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.ContactoMensajeResponseDTO;
import com.agrovalle.agrovalle_connect.events.SolicitudContactoCreadaEvent;
import com.agrovalle.agrovalle_connect.exceptions.OfertaNoDisponibleException;
import com.agrovalle.agrovalle_connect.models.ContactoMensaje;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.ContactoMensajeRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Registra solicitudes de contacto y crea pedidos pendientes de pago (HU-05).
 */
@Service
public class ContactoService {

    private static final String ESTADO_ACTIVO = "ACTIVO";
    private static final String MENSAJE_CONFIRMACION =
            "Solicitud guardada y notificación enviada al agricultor";

    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;
    private final ContactoMensajeRepository contactoMensajeRepository;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Crea el servicio de contacto con sus dependencias.
     *
     * @param productos repositorio de productos
     * @param pedidos repositorio de pedidos
     * @param mensajes repositorio de mensajes de contacto
     * @param publicador publicador de eventos de dominio
     */
    public ContactoService(final ProductoRepository productos,
            final PedidoRepository pedidos,
            final ContactoMensajeRepository mensajes,
            final ApplicationEventPublisher publicador) {
        this.productoRepository = productos;
        this.pedidoRepository = pedidos;
        this.contactoMensajeRepository = mensajes;
        this.eventPublisher = publicador;
    }

    /**
     * Guarda un mensaje para una oferta activa, crea el pedido pendiente de pago
     * y notifica al agricultor.
     *
     * @param productoId identificador del producto solicitado
     * @param mensaje contenido del mensaje de negociación
     * @return confirmación con los identificadores creados
     */
    @Transactional
    public ContactoMensajeResponseDTO enviarMensaje(final Long productoId, final String mensaje) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new OfertaNoDisponibleException(productoId));
        if (!ESTADO_ACTIVO.equals(producto.getEstado())) {
            throw new OfertaNoDisponibleException(productoId);
        }

        Pedido pedido = new Pedido();
        pedido.setProducto(producto);
        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        ContactoMensaje contacto = new ContactoMensaje();
        contacto.setPedido(pedidoGuardado);
        contacto.setMensaje(mensaje.trim());
        ContactoMensaje mensajeGuardado = contactoMensajeRepository.save(contacto);

        eventPublisher.publishEvent(new SolicitudContactoCreadaEvent(
                pedidoGuardado.getId(),
                producto.getId(),
                producto.getAgricultor().getId()));

        return new ContactoMensajeResponseDTO(
                mensajeGuardado.getId(),
                pedidoGuardado.getId(),
                MENSAJE_CONFIRMACION,
                true);
    }
}
