package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.PedidoPagadoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PedidoNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComprobanteServiceTest {

    private static final Long PEDIDO_ID = 11L;
    private static final Long COMPRADOR_ID = 7L;
    private static final Long OTRO_COMPRADOR_ID = 99L;
    private static final BigDecimal MONTO = new BigDecimal("12500.00");

    @Mock
    private OrdenCompraRepository ordenCompraRepository;

    private ComprobanteService comprobanteService;
    private OrdenCompra ordenPagada;

    @BeforeEach
    void setUp() {
        comprobanteService = new ComprobanteService(ordenCompraRepository);
        ordenPagada = crearOrden(Pedido.Estado.PAGADO, Pago.Estado.APROBADO);
    }

    @Test
    void dadoPagoAprobadoCuandoDescargaReciboEntoncesGeneraPdfConDetalleYMonto()
            throws IOException {
        when(ordenCompraRepository.buscarPorPedidoYComprador(PEDIDO_ID, COMPRADOR_ID))
                .thenReturn(Optional.of(ordenPagada));

        byte[] pdf = comprobanteService.generarComprobante(PEDIDO_ID, COMPRADOR_ID);

        try (PDDocument documento = Loader.loadPDF(pdf)) {
            String texto = new PDFTextStripper().getText(documento);
            assertTrue(texto.contains("COMPROBANTE DE PAGO"));
            assertTrue(texto.contains("Orden: OC-TEST"));
            assertTrue(texto.contains("Producto: Maíz dulce"));
            assertTrue(texto.contains("Monto pagado: COP 12500.00"));
            assertTrue(texto.contains("Medio de pago: TARJETA"));
        }
        verify(ordenCompraRepository)
                .buscarPorPedidoYComprador(PEDIDO_ID, COMPRADOR_ID);
    }

    @Test
    void dadoHistorialConPagoRechazadoCuandoConsultaEntoncesSoloListaPagoAprobado() {
        OrdenCompra ordenRechazada = crearOrden(
                Pedido.Estado.RECHAZADO, Pago.Estado.RECHAZADO);
        when(ordenCompraRepository.buscarHistorialPorComprador(COMPRADOR_ID))
                .thenReturn(List.of(ordenPagada, ordenRechazada));

        List<PedidoPagadoResponseDTO> historial =
                comprobanteService.obtenerHistorial(COMPRADOR_ID);

        assertEquals(1, historial.size());
        assertEquals(PEDIDO_ID, historial.get(0).getIdPedido());
        assertEquals(MONTO, historial.get(0).getMonto());
        verify(ordenCompraRepository).buscarHistorialPorComprador(COMPRADOR_ID);
    }

    @Test
    void dadoPagoRechazadoCuandoSolicitaReciboEntoncesRespondeNoEncontrado() {
        OrdenCompra ordenRechazada = crearOrden(
                Pedido.Estado.RECHAZADO, Pago.Estado.RECHAZADO);
        when(ordenCompraRepository.buscarPorPedidoYComprador(PEDIDO_ID, COMPRADOR_ID))
                .thenReturn(Optional.of(ordenRechazada));

        assertThrows(PedidoNoEncontradoException.class,
                () -> comprobanteService.generarComprobante(PEDIDO_ID, COMPRADOR_ID));

        when(ordenCompraRepository.buscarPorPedidoYComprador(PEDIDO_ID, COMPRADOR_ID))
                .thenReturn(Optional.empty());
        assertThrows(PedidoNoEncontradoException.class,
                () -> comprobanteService.generarComprobante(PEDIDO_ID, COMPRADOR_ID));
    }

    @Test
    void dadoPedidoDeOtroCompradorCuandoSolicitaReciboEntoncesRespondeNoEncontrado() {
        when(ordenCompraRepository.buscarPorPedidoYComprador(
                PEDIDO_ID, OTRO_COMPRADOR_ID)).thenReturn(Optional.empty());

        assertThrows(PedidoNoEncontradoException.class,
                () -> comprobanteService.generarComprobante(
                        PEDIDO_ID, OTRO_COMPRADOR_ID));

        verify(ordenCompraRepository).buscarPorPedidoYComprador(
                PEDIDO_ID, OTRO_COMPRADOR_ID);
    }

    private OrdenCompra crearOrden(
            final Pedido.Estado estadoPedido, final Pago.Estado estadoPago) {
        Producto producto = new Producto();
        producto.setNombre("Maíz dulce");
        producto.setCategoria("Granos");
        producto.setMunicipio("Buga");

        Pedido pedido = new Pedido();
        pedido.setId(PEDIDO_ID);
        pedido.setCompradorId(COMPRADOR_ID);
        pedido.setProducto(producto);
        pedido.setEstado(estadoPedido);

        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(MONTO);
        pago.setMedioPago(Pago.MedioPago.TARJETA);
        pago.setEstado(estadoPago);
        pago.setFechaCreacion(Instant.parse("2026-11-17T17:00:00Z"));

        OrdenCompra orden = new OrdenCompra();
        orden.setPedido(pedido);
        orden.setPago(pago);
        orden.setNumeroOrden("OC-TEST");
        orden.setMontoTotal(MONTO);
        return orden;
    }
}
