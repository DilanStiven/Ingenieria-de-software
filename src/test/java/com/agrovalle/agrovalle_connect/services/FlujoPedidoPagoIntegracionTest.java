package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.BilleteraResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.ContactoMensajeResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.NotificacionResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.AsignacionRutaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.PagoResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.PedidoPagadoResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.PrecioPromedioResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.VehiculoRequestDTO;
import com.agrovalle.agrovalle_connect.exceptions.PagoInvalidoException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenLogisticaRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = {
            "spring.datasource.url=jdbc:h2:mem:pedido_pago_integracion;"
                    + "MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
            "spring.datasource.username=sa",
            "spring.datasource.password=",
            "spring.datasource.driver-class-name=org.h2.Driver"
        })
class FlujoPedidoPagoIntegracionTest {

    private static final Long COMPRADOR_ID = 900L;
    private static final BigDecimal CANTIDAD = new BigDecimal("5.00");
    private static final BigDecimal PRECIO_UNITARIO = new BigDecimal("2500.00");
    private static final BigDecimal MONTO_TOTAL = new BigDecimal("12500.00");
    private static final String MENSAJE_CONTACTO =
            "Solicito información de disponibilidad para entrega.";

    @Autowired
    private AgricultorRepository agricultorRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private OrdenLogisticaRepository ordenLogisticaRepository;

    @Autowired
    private ContactoService contactoService;

    @Autowired
    private PagoService pagoService;

    @Autowired
    private ComprobanteService comprobanteService;

    @Autowired
    private BilleteraService billeteraService;

    @Autowired
    private PrecioService precioService;

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private OrdenLogisticaService ordenLogisticaService;

    @Autowired
    private VehiculoService vehiculoService;

    @MockitoBean
    private PasarelaPago pasarelaPago;

    @Test
    void conectaContactoPagoReintentoComprobanteBilleteraPrecioYNotificacion()
            throws IOException {
        Agricultor agricultor = new Agricultor();
        agricultor.setNombre("Agricultor integración");
        agricultor.setUbicacionValle("Buga");
        agricultor.setCedula("90000001");
        Agricultor agricultorGuardado = agricultorRepository.saveAndFlush(agricultor);

        Producto producto = new Producto();
        producto.setNombre("Maíz dulce");
        producto.setCategoria("Granos");
        producto.setMunicipio("Buga");
        producto.setCantidad(new BigDecimal("20.00"));
        producto.setPrecioUnitario(PRECIO_UNITARIO);
        producto.setFechaCosecha(LocalDate.now());
        producto.setEstado("ACTIVO");
        producto.setAgricultor(agricultorGuardado);
        Producto productoGuardado = productoRepository.saveAndFlush(producto);

        ContactoMensajeResponseDTO contacto = contactoService.enviarMensaje(
                productoGuardado.getId(), CANTIDAD, MENSAJE_CONTACTO, "Cali", COMPRADOR_ID);
        List<NotificacionResponseDTO> notificaciones =
                notificacionService.listarPorAgricultor(agricultorGuardado.getId());

        assertTrue(contacto.isNotificacionEnviada());
        assertEquals(1, notificaciones.size());
        assertTrue(notificaciones.get(0).getMensaje().contains(MENSAJE_CONTACTO));

        when(pasarelaPago.procesarPago(
                eq(MONTO_TOTAL), eq(Pago.MedioPago.PSE)))
                .thenReturn(new ResultadoPago(
                        Pago.Estado.RECHAZADO, null, "Fondos insuficientes"));
        when(pasarelaPago.procesarPago(
                eq(MONTO_TOTAL), eq(Pago.MedioPago.TARJETA)))
                .thenReturn(new ResultadoPago(
                        Pago.Estado.APROBADO, "SIM-INTEGRACION", "Pago aprobado"));

        PagoResponseDTO rechazo = pagoService.procesar(
                contacto.getIdPedido(), COMPRADOR_ID, Pago.MedioPago.PSE);
        Pedido pedidoPendiente = pedidoRepository.findById(contacto.getIdPedido())
                .orElseThrow();

        assertEquals(Pago.Estado.RECHAZADO, rechazo.getEstado());
        assertEquals(Pedido.Estado.PENDIENTE_PAGO, pedidoPendiente.getEstado());
        assertNotNull(rechazo.getFechaLimiteReintento());
        assertTrue(Math.abs(Duration.between(
                rechazo.getFechaLimiteReintento(),
                pedidoPendiente.getFechaLimiteReintento()).toNanos()) <= 1_000);
        assertTrue(ordenLogisticaRepository
                .findByPedidoId(contacto.getIdPedido()).isEmpty());

        PagoResponseDTO aprobado = pagoService.procesar(
                contacto.getIdPedido(), COMPRADOR_ID, Pago.MedioPago.TARJETA);
        Pedido pedidoPagado = pedidoRepository.findById(contacto.getIdPedido())
                .orElseThrow();
        List<OrdenCompra> ordenes =
                ordenCompraRepository.buscarHistorialPorComprador(COMPRADOR_ID);
        List<PedidoPagadoResponseDTO> historial =
                comprobanteService.obtenerHistorial(COMPRADOR_ID);
        BilleteraResponseDTO billetera =
                billeteraService.consultarSaldo(agricultorGuardado.getId());
        PrecioPromedioResponseDTO precio =
                precioService.obtenerPromedio("Maíz dulce");

        assertEquals(Pago.Estado.APROBADO, aprobado.getEstado());
        assertEquals(Pedido.Estado.PAGADO, pedidoPagado.getEstado());
        assertNull(pedidoPagado.getFechaLimiteReintento());
        assertEquals(1, ordenes.size());
        assertEquals(aprobado.getIdOrdenCompra(), ordenes.get(0).getId());
        assertEquals(1, historial.size());
        assertEquals(MONTO_TOTAL, historial.get(0).getMonto());
        assertEquals(MONTO_TOTAL, billetera.getSaldo());
        assertEquals(PRECIO_UNITARIO, precio.getPrecioPromedio());
        assertEquals(1L, precio.getCantidadTransacciones());

        OrdenLogistica ordenLogistica = ordenLogisticaRepository
                .findByPedidoId(contacto.getIdPedido())
                .orElseThrow();
        assertEquals("Cali", ordenLogistica.getDestino());
        assertEquals(OrdenLogistica.Estado.PENDIENTE_ALISTAMIENTO,
                ordenLogistica.getEstado());

        OrdenLogisticaResponseDTO alistada = ordenLogisticaService.marcarAlistada(
                ordenLogistica.getId(), agricultorGuardado.getId());
        assertEquals(OrdenLogistica.Estado.ALISTADO, alistada.getEstado());
        assertEquals(contacto.getIdPedido(), alistada.getPedidoId());

        VehiculoRequestDTO vehiculoSolicitud = new VehiculoRequestDTO();
        vehiculoSolicitud.setPlaca("ABC123");
        vehiculoSolicitud.setTipo("Camión");
        vehiculoSolicitud.setCapacidadKg(1000);
        Long vehiculoId = vehiculoService.registrar(
                vehiculoSolicitud,
                agricultorGuardado.getId(),
                RolUsuario.AGRICULTOR).getId();
        AsignacionRutaRequestDTO rutaSolicitud = new AsignacionRutaRequestDTO();
        rutaSolicitud.setVehiculoId(vehiculoId);
        rutaSolicitud.setFechaSalida(LocalDate.now());
        OrdenLogisticaResponseDTO enRuta = ordenLogisticaService.asignarRuta(
                ordenLogistica.getId(),
                rutaSolicitud,
                agricultorGuardado.getId(),
                RolUsuario.AGRICULTOR);
        assertEquals(OrdenLogistica.Estado.EN_RUTA, enRuta.getEstado());
        assertEquals(contacto.getIdPedido(), enRuta.getPedidoId());

        byte[] comprobante = comprobanteService.generarComprobante(
                contacto.getIdPedido(), COMPRADOR_ID);
        try (PDDocument documento = Loader.loadPDF(comprobante)) {
            String contenido = new PDFTextStripper().getText(documento);
            assertTrue(contenido.contains("Monto pagado: COP 12500.00"));
        }

        assertThrows(PagoInvalidoException.class, () -> pagoService.procesar(
                contacto.getIdPedido(), COMPRADOR_ID, Pago.MedioPago.TARJETA));
    }
}
