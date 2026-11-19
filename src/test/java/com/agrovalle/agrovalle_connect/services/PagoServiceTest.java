package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.PagoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PagoInvalidoException;
import com.agrovalle.agrovalle_connect.exceptions.CantidadNoDisponibleException;
import com.agrovalle.agrovalle_connect.exceptions.PedidoNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import com.agrovalle.agrovalle_connect.repositories.PagoRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PagoServiceTest {

    private static final Long PEDIDO_ID = 11L;
    private static final Long COMPRADOR_ID = 9L;
    private static final Long PRODUCTO_ID = 7L;
    private static final BigDecimal PRECIO_UNITARIO = new BigDecimal("25000.00");
    private static final BigDecimal CANTIDAD = new BigDecimal("5.00");
    private static final BigDecimal MONTO_TOTAL = new BigDecimal("125000.00");

    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private OrdenCompraRepository ordenCompraRepository;
    @Mock
    private PasarelaPago pasarelaPago;
    @Mock
    private BilleteraService billeteraService;
    @Mock
    private PrecioService precioService;
    @Mock
    private ProductoRepository productoRepository;

    private PagoService pagoService;
    private Pedido pedido;
    private Producto producto;

    @BeforeEach
    void configurar() {
        MockitoAnnotations.openMocks(this);
        pagoService = new PagoService(
                pedidoRepository,
                pagoRepository,
                ordenCompraRepository,
                pasarelaPago,
                billeteraService,
                precioService,
                productoRepository);

        producto = new Producto();
        producto.setId(PRODUCTO_ID);
        producto.setNombre("Maíz dulce");
        producto.setPrecioUnitario(PRECIO_UNITARIO);
        producto.setCantidad(new BigDecimal("50.00"));
        producto.setEstado("ACTIVO");
        Agricultor agricultor = new Agricultor();
        agricultor.setId(5L);
        producto.setAgricultor(agricultor);

        pedido = new Pedido();
        pedido.setId(PEDIDO_ID);
        pedido.setProducto(producto);
        pedido.setCantidad(CANTIDAD);
        pedido.setCompradorId(COMPRADOR_ID);
        pedido.setEstado(Pedido.Estado.PENDIENTE_PAGO);
    }

    @Test
    void apruebaPagoYGeneraOrdenDeCompra() {
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));
        when(productoRepository.buscarPorIdConBloqueo(PRODUCTO_ID))
                .thenReturn(Optional.of(producto));
        when(pasarelaPago.procesarPago(
                MONTO_TOTAL, Pago.MedioPago.TARJETA))
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
                COMPRADOR_ID,
                Pago.MedioPago.TARJETA);

        assertEquals(Pago.Estado.APROBADO, respuesta.getEstado());
        assertEquals(Pedido.Estado.PAGADO, pedido.getEstado());
        assertEquals("SIM-123", respuesta.getReferenciaPasarela());
        assertNotNull(respuesta.getNumeroOrden());
        assertNotNull(respuesta.getIdOrdenCompra());
        verify(billeteraService).acreditar(pedido, MONTO_TOTAL);
        verify(precioService).registrarTransaccion("Maíz dulce", PRECIO_UNITARIO);
        verify(productoRepository).save(producto);
        assertEquals(new BigDecimal("45.00"), producto.getCantidad());
    }

    @Test
    void rechazaPagoSinGenerarOrdenDeCompra() {
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));
        when(productoRepository.buscarPorIdConBloqueo(PRODUCTO_ID))
                .thenReturn(Optional.of(producto));
        when(pasarelaPago.procesarPago(
                MONTO_TOTAL, Pago.MedioPago.PSE))
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
                COMPRADOR_ID,
                Pago.MedioPago.PSE);

        assertEquals(Pago.Estado.RECHAZADO, respuesta.getEstado());
        assertEquals(Pedido.Estado.RECHAZADO, pedido.getEstado());
        assertNull(respuesta.getIdOrdenCompra());
        assertNull(respuesta.getNumeroOrden());
        verifyNoInteractions(billeteraService);
        verifyNoInteractions(precioService);
        assertEquals(new BigDecimal("50.00"), producto.getCantidad());
    }

    @Test
    void rechazaProcesarPedidoQueYaFuePagado() {
        pedido.setEstado(Pedido.Estado.PAGADO);
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));

        assertThrows(PagoInvalidoException.class, () ->
                pagoService.procesar(
                        PEDIDO_ID, COMPRADOR_ID, Pago.MedioPago.TARJETA));
    }

    @Test
    void rechazaPagarPedidoDeOtroCompradorSinContactarLaPasarela() {
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));

        assertThrows(PedidoNoEncontradoException.class, () ->
                pagoService.procesar(PEDIDO_ID, 99L, Pago.MedioPago.TARJETA));

        verifyNoInteractions(
                pasarelaPago, pagoRepository, ordenCompraRepository,
                billeteraService, precioService, productoRepository);
    }

    @Test
    void rechazaPagoCuandoYaNoHayExistenciasSuficientes() {
        producto.setCantidad(new BigDecimal("3.00"));
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));
        when(productoRepository.buscarPorIdConBloqueo(PRODUCTO_ID))
                .thenReturn(Optional.of(producto));

        assertThrows(CantidadNoDisponibleException.class, () ->
                pagoService.procesar(
                        PEDIDO_ID, COMPRADOR_ID, Pago.MedioPago.TARJETA));

        verifyNoInteractions(pasarelaPago, pagoRepository, ordenCompraRepository);
        assertEquals(new BigDecimal("3.00"), producto.getCantidad());
    }

    @Test
    void desactivaOfertaCuandoLaCompraAgotaLasExistencias() {
        producto.setCantidad(CANTIDAD);
        when(pedidoRepository.findById(PEDIDO_ID)).thenReturn(Optional.of(pedido));
        when(productoRepository.buscarPorIdConBloqueo(PRODUCTO_ID))
                .thenReturn(Optional.of(producto));
        when(pasarelaPago.procesarPago(
                MONTO_TOTAL, Pago.MedioPago.TARJETA))
                .thenReturn(new ResultadoPago(
                        Pago.Estado.APROBADO, "SIM-123", "Pago aprobado"));
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

        pagoService.procesar(PEDIDO_ID, COMPRADOR_ID, Pago.MedioPago.TARJETA);

        assertEquals(new BigDecimal("0.00"), producto.getCantidad());
        assertEquals("INACTIVO", producto.getEstado());
    }
}
