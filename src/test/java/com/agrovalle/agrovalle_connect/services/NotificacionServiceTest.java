package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.NotificacionResponseDTO;
import com.agrovalle.agrovalle_connect.events.SolicitudContactoCreadaEvent;
import com.agrovalle.agrovalle_connect.models.Notificacion;
import com.agrovalle.agrovalle_connect.repositories.NotificacionRepository;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class NotificacionServiceTest {

    private static final Long AGRICULTOR_ID = 17L;
    private static final Long PEDIDO_ID = 11L;
    private static final Long PRODUCTO_ID = 7L;

    @Mock
    private NotificacionRepository notificacionRepository;

    @InjectMocks
    private NotificacionService notificacionService;

    @Test
    void persisteNotificacionDeSolicitudParaElAgricultorDestinatario() {
        SolicitudContactoCreadaEvent evento =
                new SolicitudContactoCreadaEvent(
                        PEDIDO_ID, PRODUCTO_ID, AGRICULTOR_ID, "Quisiera negociar la entrega.");
        when(notificacionRepository.save(any(Notificacion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        notificacionService.registrarSolicitudContacto(evento);

        ArgumentCaptor<Notificacion> notificacionCaptor =
                ArgumentCaptor.forClass(Notificacion.class);
        verify(notificacionRepository).save(notificacionCaptor.capture());
        Notificacion guardada = notificacionCaptor.getValue();
        assertEquals(AGRICULTOR_ID, guardada.getAgricultorId());
        assertEquals(PEDIDO_ID, guardada.getPedidoId());
        assertEquals(PRODUCTO_ID, guardada.getProductoId());
        assertEquals("Nueva solicitud de compra para el producto 7, pedido 11: "
                        + "Quisiera negociar la entrega.",
                guardada.getMensaje());
    }

    @Test
    void listaSoloLasNotificacionesDelAgricultorSolicitado() {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(3L);
        notificacion.setAgricultorId(AGRICULTOR_ID);
        notificacion.setPedidoId(PEDIDO_ID);
        notificacion.setProductoId(PRODUCTO_ID);
        notificacion.setMensaje("Nueva solicitud de compra");
        notificacion.setFechaCreacion(Instant.parse("2026-11-18T16:00:00Z"));
        when(notificacionRepository.findByAgricultorIdOrderByFechaCreacionDesc(AGRICULTOR_ID))
                .thenReturn(List.of(notificacion));

        List<NotificacionResponseDTO> respuesta =
                notificacionService.listarPorAgricultor(AGRICULTOR_ID);

        assertEquals(1, respuesta.size());
        assertEquals(3L, respuesta.get(0).getId());
        assertEquals(PEDIDO_ID, respuesta.get(0).getIdPedido());
        assertEquals(PRODUCTO_ID, respuesta.get(0).getIdProducto());
        assertEquals("Nueva solicitud de compra", respuesta.get(0).getMensaje());
        assertNotNull(respuesta.get(0).getFechaCreacion());
        verify(notificacionRepository)
                .findByAgricultorIdOrderByFechaCreacionDesc(AGRICULTOR_ID);
    }
}
