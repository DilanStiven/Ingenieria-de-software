package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.ContactoMensajeResponseDTO;
import com.agrovalle.agrovalle_connect.events.SolicitudContactoCreadaEvent;
import com.agrovalle.agrovalle_connect.exceptions.OfertaNoDisponibleException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.ContactoMensaje;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.ContactoMensajeRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class ContactoServiceTest {

    private static final Long PRODUCTO_ID = 7L;
    private static final Long PEDIDO_ID = 11L;
    private static final Long MENSAJE_ID = 13L;
    private static final Long AGRICULTOR_ID = 17L;
    private static final Long COMPRADOR_ID = 19L;

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ContactoMensajeRepository contactoMensajeRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private ContactoService contactoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        Agricultor agricultor = new Agricultor();
        agricultor.setId(AGRICULTOR_ID);
        producto = new Producto();
        producto.setId(PRODUCTO_ID);
        producto.setEstado("ACTIVO");
        producto.setAgricultor(agricultor);
    }

    @Test
    void registraMensajeYPedidoYPuedeNotificarAlAgricultor() {
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(producto));
        when(pedidoRepository.save(any(Pedido.class))).thenAnswer(invocacion -> {
            Pedido pedido = invocacion.getArgument(0);
            pedido.setId(PEDIDO_ID);
            return pedido;
        });
        when(contactoMensajeRepository.save(any(ContactoMensaje.class))).thenAnswer(invocacion -> {
            ContactoMensaje contacto = invocacion.getArgument(0);
            contacto.setId(MENSAJE_ID);
            return contacto;
        });

        ContactoMensajeResponseDTO respuesta =
                contactoService.enviarMensaje(
                        PRODUCTO_ID, "  Me interesa el producto  ", COMPRADOR_ID);

        assertEquals(MENSAJE_ID, respuesta.getIdMensaje());
        assertEquals(PEDIDO_ID, respuesta.getIdPedido());
        assertTrue(respuesta.isNotificacionEnviada());

        ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
        verify(pedidoRepository).save(pedidoCaptor.capture());
        assertEquals(producto, pedidoCaptor.getValue().getProducto());
        assertEquals(COMPRADOR_ID, pedidoCaptor.getValue().getCompradorId());
        assertEquals(Pedido.Estado.PENDIENTE_PAGO, pedidoCaptor.getValue().getEstado());

        ArgumentCaptor<ContactoMensaje> mensajeCaptor = ArgumentCaptor.forClass(ContactoMensaje.class);
        verify(contactoMensajeRepository).save(mensajeCaptor.capture());
        assertEquals(pedidoCaptor.getValue(), mensajeCaptor.getValue().getPedido());
        assertEquals("Me interesa el producto", mensajeCaptor.getValue().getMensaje());
        ArgumentCaptor<SolicitudContactoCreadaEvent> eventoCaptor =
                ArgumentCaptor.forClass(SolicitudContactoCreadaEvent.class);
        verify(eventPublisher).publishEvent(eventoCaptor.capture());
        assertEquals(PEDIDO_ID, eventoCaptor.getValue().getPedidoId());
        assertEquals(PRODUCTO_ID, eventoCaptor.getValue().getProductoId());
        assertEquals(AGRICULTOR_ID, eventoCaptor.getValue().getAgricultorId());
    }

    @Test
    void rechazaOfertaInactivaSinPersistirNiNotificar() {
        producto.setEstado("INACTIVO");
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.of(producto));

        assertThrows(OfertaNoDisponibleException.class,
                () -> contactoService.enviarMensaje(
                        PRODUCTO_ID, "Me interesa el producto", COMPRADOR_ID));

        verify(pedidoRepository, never()).save(any(Pedido.class));
        verify(contactoMensajeRepository, never()).save(any(ContactoMensaje.class));
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void rechazaProductoInexistenteSinPersistirNiNotificar() {
        when(productoRepository.findById(PRODUCTO_ID)).thenReturn(Optional.empty());

        assertThrows(OfertaNoDisponibleException.class,
                () -> contactoService.enviarMensaje(
                        PRODUCTO_ID, "Me interesa el producto", COMPRADOR_ID));

        verify(pedidoRepository, never()).save(any(Pedido.class));
        verify(contactoMensajeRepository, never()).save(any(ContactoMensaje.class));
        verify(eventPublisher, never()).publishEvent(any());
    }
}
