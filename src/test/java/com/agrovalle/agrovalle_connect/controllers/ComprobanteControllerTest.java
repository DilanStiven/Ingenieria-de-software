package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.PedidoPagadoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.ComprobanteService;
import com.agrovalle.agrovalle_connect.services.JwtService;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ComprobanteController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class ComprobanteControllerTest {

    private static final Long PEDIDO_ID = 11L;
    private static final Long COMPRADOR_ID = 7L;
    private static final String HISTORIAL_URL = "/api/v1/pedidos/pagados";
    private static final byte[] PDF = "%PDF-1.4 comprobante".getBytes(
            java.nio.charset.StandardCharsets.US_ASCII);

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ComprobanteService comprobanteService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void dadoCompradorAutenticadoCuandoConsultaHistorialEntoncesRecibeSusPedidosPagados()
            throws Exception {
        PedidoPagadoResponseDTO pedido = new PedidoPagadoResponseDTO(crearOrdenPagada());
        when(comprobanteService.obtenerHistorial(COMPRADOR_ID))
                .thenReturn(List.of(pedido));

        mockMvc.perform(get(HISTORIAL_URL)
                        .with(user(String.valueOf(COMPRADOR_ID))
                                .authorities(() -> "ROLE_AGRICULTOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idPedido").value(PEDIDO_ID))
                .andExpect(jsonPath("$[0].numeroOrden").value("OC-TEST"))
                .andExpect(jsonPath("$[0].monto").value(new BigDecimal("12500.00")
                        .doubleValue()))
                .andExpect(jsonPath("$[0].nombreProducto").value("Maíz dulce"));

        verify(comprobanteService).obtenerHistorial(COMPRADOR_ID);
    }

    @Test
    void dadoPedidoPagadoCuandoDescargaReciboEntoncesDevuelvePdfComoAdjunto()
            throws Exception {
        when(comprobanteService.generarComprobante(PEDIDO_ID, COMPRADOR_ID))
                .thenReturn(PDF);

        mockMvc.perform(get("/api/v1/pedidos/{pedidoId}/comprobante", PEDIDO_ID)
                        .with(user(String.valueOf(COMPRADOR_ID))
                                .authorities(() -> "ROLE_AGRICULTOR")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(header().string(
                        "Content-Disposition",
                        "attachment; filename=\"comprobante-pedido-11.pdf\""))
                .andExpect(content().bytes(PDF));

        verify(comprobanteService).generarComprobante(PEDIDO_ID, COMPRADOR_ID);
    }

    @Test
    void rechazaHistorialSinAutenticacion() throws Exception {
        mockMvc.perform(get(HISTORIAL_URL))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(comprobanteService);
    }

    private OrdenCompra crearOrdenPagada() {
        Producto producto = new Producto();
        producto.setNombre("Maíz dulce");
        producto.setCategoria("Granos");
        producto.setMunicipio("Buga");

        Pedido pedido = new Pedido();
        pedido.setId(PEDIDO_ID);
        pedido.setProducto(producto);
        pedido.setEstado(Pedido.Estado.PAGADO);

        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(new BigDecimal("12500.00"));
        pago.setMedioPago(
                Pago.MedioPago.TARJETA);
        pago.setEstado(Pago.Estado.APROBADO);
        pago.setFechaCreacion(Instant.parse("2026-11-17T17:00:00Z"));

        OrdenCompra orden = new OrdenCompra();
        orden.setPedido(pedido);
        orden.setPago(pago);
        orden.setNumeroOrden("OC-TEST");
        orden.setMontoTotal(new BigDecimal("12500.00"));
        return orden;
    }
}
