package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.PagoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.exceptions.PagoInvalidoException;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.JwtService;
import com.agrovalle.agrovalle_connect.services.PagoService;
import java.math.BigDecimal;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PagoController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class PagoControllerTest {

    private static final String URL = "/api/v1/pagos";
    private static final Long PEDIDO_ID = 11L;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PagoService pagoService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void dadoPedidoPendienteCuandoPagoEsAprobadoEntoncesRetornaPagoPagadoYOrden() throws Exception {
        when(pagoService.procesar(PEDIDO_ID, 7L, Pago.MedioPago.TARJETA))
                .thenReturn(respuesta(Pago.Estado.APROBADO,
                        "SIM-APROBADO", "Pago aprobado", 31L, "OC-TEST", null));

        mockMvc.perform(post(URL)
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id_pedido": 11,
                                  "medio_pago": "TARJETA"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPedido").value(PEDIDO_ID))
                .andExpect(jsonPath("$.estado").value("APROBADO"))
                .andExpect(jsonPath("$.idOrdenCompra").value(31))
                .andExpect(jsonPath("$.numeroOrden").value("OC-TEST"))
                .andExpect(jsonPath("$.mensaje").value("Pago aprobado"));

        verify(pagoService).procesar(PEDIDO_ID, 7L, Pago.MedioPago.TARJETA);
    }

    @Test
    void dadoPedidoPendienteCuandoPagoEsRechazadoEntoncesNoRetornaOrden() throws Exception {
        when(pagoService.procesar(PEDIDO_ID, 7L, Pago.MedioPago.PSE))
                .thenReturn(respuesta(Pago.Estado.RECHAZADO,
                        null, "Pago Rechazado. Puedes reintentar hasta la fecha límite indicada.",
                        null, null, Instant.parse("2026-11-18T21:00:00Z")));

        mockMvc.perform(post(URL)
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id_pedido": 11,
                                  "medio_pago": "PSE"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("RECHAZADO"))
                .andExpect(jsonPath("$.fechaLimiteReintento")
                        .value("2026-11-18T21:00:00Z"))
                .andExpect(jsonPath("$.idOrdenCompra").doesNotExist())
                .andExpect(jsonPath("$.numeroOrden").doesNotExist())
                .andExpect(jsonPath("$.mensaje")
                        .value("Pago Rechazado. Puedes reintentar hasta la fecha límite indicada."));

        verify(pagoService).procesar(PEDIDO_ID, 7L, Pago.MedioPago.PSE);
    }

    @Test
    void rechazaPagoSinAutenticacion() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id_pedido": 11,
                                  "medio_pago": "TARJETA"
                                }
                                """))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(pagoService);
    }

    @Test
    void rechazaSolicitudConDatosInvalidos() throws Exception {
        mockMvc.perform(post(URL)
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id_pedido": 0,
                                  "medio_pago": null
                                }
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(pagoService);
    }

    @Test
    void responde409CuandoElPedidoNoPermiteOtroPago() throws Exception {
        when(pagoService.procesar(PEDIDO_ID, 7L, Pago.MedioPago.TARJETA))
                .thenThrow(new PagoInvalidoException(
                        PEDIDO_ID,
                        Pedido.Estado.PAGADO.name()));

        mockMvc.perform(post(URL)
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "id_pedido": 11,
                                  "medio_pago": "TARJETA"
                                }
                                """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").exists());
    }

    private PagoResponseDTO respuesta(final Pago.Estado estado,
            final String referencia,
            final String mensaje,
            final Long ordenCompraId,
            final String numeroOrden,
            final Instant fechaLimiteReintento) {
        Pedido pedido = new Pedido();
        pedido.setId(PEDIDO_ID);

        Pago pago = new Pago();
        pago.setId(21L);
        pago.setPedido(pedido);
        pago.setMonto(new BigDecimal("25000.00"));
        pago.setMedioPago(Pago.MedioPago.TARJETA);
        pago.setEstado(estado);
        pago.setReferenciaPasarela(referencia);
        pago.setFechaCreacion(Instant.parse("2026-11-17T12:00:00Z"));

        return new PagoResponseDTO(
                pago, numeroOrden, ordenCompraId, mensaje, fechaLimiteReintento);
    }
}
