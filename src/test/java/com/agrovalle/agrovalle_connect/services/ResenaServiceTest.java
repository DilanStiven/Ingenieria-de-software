package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.ResenaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.ResenaResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.ResenaInvalidaException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.models.Resena;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import com.agrovalle.agrovalle_connect.repositories.ResenaRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ResenaServiceTest {

    private static final Long COMPRADOR_ID = 90L;
    private static final Long ORDEN_ID = 12L;
    private static final Long PEDIDO_ID = 34L;
    private static final Long AGRICULTOR_ID = 7L;

    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private OrdenCompraRepository ordenCompraRepository;

    @Mock
    private OrdenLogisticaRepository ordenLogisticaRepository;

    @Mock
    private AgricultorRepository agricultorRepository;

    @InjectMocks
    private ResenaService resenaService;

    @Test
    void guardaResenaDeOrdenEntregadaYActualizaPromedio() {
        OrdenCompra orden = ordenEntregada();
        when(ordenCompraRepository.findById(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(resenaRepository.findByOrdenCompraId(ORDEN_ID))
                .thenReturn(Optional.empty());
        when(ordenLogisticaRepository.findByPedidoId(PEDIDO_ID))
                .thenReturn(Optional.of(ordenLogistica(
                        OrdenLogistica.Estado.ENTREGADA)));
        when(resenaRepository.save(any(Resena.class))).thenAnswer(invocation -> {
            Resena resena = invocation.getArgument(0);
            resena.setId(55L);
            return resena;
        });
        when(resenaRepository.calcularPromedioPorAgricultor(AGRICULTOR_ID))
                .thenReturn(4.5D);

        ResenaRequestDTO request = new ResenaRequestDTO();
        request.setIdOrdenCompra(ORDEN_ID);
        request.setEstrellas(5);
        request.setComentario("Excelente tomate");

        ResenaResponseDTO response = resenaService.guardar(request, COMPRADOR_ID);

        assertEquals(55L, response.getId());
        assertEquals(5, response.getEstrellas());
        assertEquals(new BigDecimal("4.50"),
                response.getCalificacionPromedioAgricultor());
        verify(agricultorRepository).save(any(Agricultor.class));
    }

    @Test
    void rechazaResenaCuandoLaOrdenNoFueEntregada() {
        OrdenCompra orden = ordenEntregada();
        when(ordenCompraRepository.findById(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(resenaRepository.findByOrdenCompraId(ORDEN_ID))
                .thenReturn(Optional.empty());
        when(ordenLogisticaRepository.findByPedidoId(PEDIDO_ID))
                .thenReturn(Optional.of(ordenLogistica(
                        OrdenLogistica.Estado.EN_RUTA)));

        ResenaRequestDTO request = new ResenaRequestDTO();
        request.setIdOrdenCompra(ORDEN_ID);
        request.setEstrellas(4);

        assertThrows(ResenaInvalidaException.class,
                () -> resenaService.guardar(request, COMPRADOR_ID));
        verify(resenaRepository, never()).save(any(Resena.class));
    }

    @Test
    void rechazaResenaDuplicadaParaLaMismaOrden() {
        OrdenCompra orden = ordenEntregada();
        Resena resenaExistente = new Resena();
        when(ordenCompraRepository.findById(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(resenaRepository.findByOrdenCompraId(ORDEN_ID))
                .thenReturn(Optional.of(resenaExistente));

        ResenaRequestDTO request = solicitud(5, "Excelente tomate");

        assertThrows(ResenaInvalidaException.class,
                () -> resenaService.guardar(request, COMPRADOR_ID));
        verify(resenaRepository, never()).save(any(Resena.class));
    }

    @Test
    void rechazaOrdenDeOtroComprador() {
        OrdenCompra orden = ordenEntregada();
        orden.getPedido().setCompradorId(999L);
        when(ordenCompraRepository.findById(ORDEN_ID))
                .thenReturn(Optional.of(orden));

        ResenaRequestDTO request = solicitud(5, "Excelente tomate");

        assertThrows(ResenaInvalidaException.class,
                () -> resenaService.guardar(request, COMPRADOR_ID));
        verify(resenaRepository, never()).save(any(Resena.class));
    }

    @Test
    void aceptaCalificacionMinimaConComentarioOpcional() {
        OrdenCompra orden = ordenEntregada();
        when(ordenCompraRepository.findById(ORDEN_ID))
                .thenReturn(Optional.of(orden));
        when(resenaRepository.findByOrdenCompraId(ORDEN_ID))
                .thenReturn(Optional.empty());
        when(ordenLogisticaRepository.findByPedidoId(PEDIDO_ID))
                .thenReturn(Optional.of(ordenLogistica(
                        OrdenLogistica.Estado.ENTREGADA)));
        when(resenaRepository.save(any(Resena.class))).thenAnswer(invocation -> {
            Resena resena = invocation.getArgument(0);
            resena.setId(56L);
            return resena;
        });
        when(resenaRepository.calcularPromedioPorAgricultor(AGRICULTOR_ID))
                .thenReturn(1.0D);

        ResenaResponseDTO response = resenaService.guardar(
                solicitud(1, null), COMPRADOR_ID);

        assertEquals(1, response.getEstrellas());
        assertEquals(new BigDecimal("1.00"),
                response.getCalificacionPromedioAgricultor());
    }

    private ResenaRequestDTO solicitud(final int estrellas,
            final String comentario) {
        ResenaRequestDTO request = new ResenaRequestDTO();
        request.setIdOrdenCompra(ORDEN_ID);
        request.setEstrellas(estrellas);
        request.setComentario(comentario);
        return request;
    }

    private OrdenCompra ordenEntregada() {
        Agricultor agricultor = new Agricultor();
        agricultor.setId(AGRICULTOR_ID);
        Producto producto = new Producto();
        producto.setAgricultor(agricultor);
        Pedido pedido = new Pedido();
        pedido.setId(PEDIDO_ID);
        pedido.setCompradorId(COMPRADOR_ID);
        pedido.setProducto(producto);
        OrdenCompra orden = new OrdenCompra();
        orden.setId(ORDEN_ID);
        orden.setPedido(pedido);
        return orden;
    }

    private OrdenLogistica ordenLogistica(final OrdenLogistica.Estado estado) {
        OrdenLogistica orden = new OrdenLogistica();
        orden.setEstado(estado);
        return orden;
    }
}
