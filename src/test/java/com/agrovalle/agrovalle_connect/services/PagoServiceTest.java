package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.PagoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PagoInvalidoException;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import com.agrovalle.agrovalle_connect.repositories.PagoRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PagoServiceTest {

    private static final Long PEDIDO_ID = 11L;

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private OrdenCompraRepository ordenCompraRepository;
    @Mock
    private PasarelaPago pasarelaPago;

    private PagoService pagoService;
    private Pedido pedido;

    @BeforeEach
    void configurar() {
        MockitoAnnotations.openMocks(this);
        pagoService = new PagoService(
                pedidoRepository,
                pagoRepository,
                ordenCompraRepository,
                pasarelaPago);

        Producto producto = new Producto();
        producto.setId(7L);
        producto.setPrecioUnitario(new BigDecimal("25000.00"));

        pedido = new Pedido();
        pedido.setId(PEDIDO_ID);
        pedido.setProducto(producto);
        pedido.setEstado(Pedido.Estado.PENDIENTE_PAGO);
    }

    @Test
    void apruebaPagoYGeneraOrdenDeCompra() {
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));
        when(pasarelaPago.procesarPago(
                new BigDecimal("25000.00"), Pago.MedioPago.TARJETA))
                .thenReturn(new ResultadoPago(
                        Pago.Estado.APROBADO,
                        "SIM-123",
                        "Pago aprobado"));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> {
            Pago pago = invocation.getArgument(0);
            pago.setId(21L);
            return pago;
        });
        when(ordenCompraRepository.save(any(OrdenCompra.class))).thenAnswer(invocation -> {
            OrdenCompra orden = invocation.getArgument(0);
            orden.setId(31L);
            return orden;
        });

        PagoResponseDTO respuesta = pagoService.procesar(
                PEDIDO_ID,
                Pago.MedioPago.TARJETA);

        assertEquals(Pago.Estado.APROBADO, respuesta.getEstado());
        assertEquals(Pedido.Estado.PAGADO, pedido.getEstado());
        assertEquals("SIM-123", respuesta.getReferenciaPasarela());
        assertNotNull(respuesta.getNumeroOrden());
        assertNotNull(respuesta.getIdOrdenCompra());
    }

    @Test
    void rechazaPagoSinGenerarOrdenDeCompra() {
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));
        when(pasarelaPago.procesarPago(
                new BigDecimal("25000.00"), Pago.MedioPago.PSE))
                .thenReturn(new ResultadoPago(
                        Pago.Estado.RECHAZADO,
                        null,
                        "Pago rechazado"));
        when(pagoRepository.save(any(Pago.class))).thenAnswer(invocation -> {
            Pago pago = invocation.getArgument(0);
            pago.setId(22L);
            return pago;
        });

        PagoResponseDTO respuesta = pagoService.procesar(
                PEDIDO_ID,
                Pago.MedioPago.PSE);

        assertEquals(Pago.Estado.RECHAZADO, respuesta.getEstado());
        assertEquals(Pedido.Estado.RECHAZADO, pedido.getEstado());
        assertNull(respuesta.getIdOrdenCompra());
        assertNull(respuesta.getNumeroOrden());
    }

    @Test
    void rechazaProcesarPedidoQueYaFuePagado() {
        pedido.setEstado(Pedido.Estado.PAGADO);
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));

        assertThrows(PagoInvalidoException.class, () ->
                pagoService.procesar(PEDIDO_ID, Pago.MedioPago.TARJETA));
    }
}
