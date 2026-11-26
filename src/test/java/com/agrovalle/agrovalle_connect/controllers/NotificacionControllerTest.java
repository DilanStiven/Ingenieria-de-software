package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.NotificacionResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.models.Notificacion;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.JwtService;
import com.agrovalle.agrovalle_connect.services.NotificacionService;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NotificacionController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class NotificacionControllerTest {

    private static final String URL = "/api/v1/notificaciones";
    private static final String TOKEN = "token-valido";
    private static final Long AGRICULTOR_ID = 17L;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificacionService notificacionService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void agricultorConsultaSuBandejaDeNotificaciones() throws Exception {
        when(jwtService.esValido(TOKEN)).thenReturn(true);
        when(jwtService.obtenerUsuarioId(TOKEN)).thenReturn(AGRICULTOR_ID);
        when(jwtService.obtenerRol(TOKEN)).thenReturn(RolUsuario.AGRICULTOR);

        Notificacion notificacion = new Notificacion();
        notificacion.setId(3L);
        notificacion.setAgricultorId(AGRICULTOR_ID);
        notificacion.setPedidoId(11L);
        notificacion.setProductoId(7L);
        notificacion.setMensaje("Nueva solicitud de compra");
        notificacion.setFechaCreacion(Instant.parse("2026-11-18T16:00:00Z"));
        when(notificacionService.listarPorAgricultor(AGRICULTOR_ID))
                .thenReturn(List.of(new NotificacionResponseDTO(notificacion)));

        mockMvc.perform(get(URL).header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].idPedido").value(11))
                .andExpect(jsonPath("$[0].idProducto").value(7))
                .andExpect(jsonPath("$[0].mensaje").value("Nueva solicitud de compra"));

        verify(notificacionService).listarPorAgricultor(AGRICULTOR_ID);
    }

    @Test
    void compradorConsultaSuBandejaDeNotificaciones() throws Exception {
        Long compradorId = 90L;
        Notificacion notificacion = new Notificacion();
        notificacion.setId(4L);
        notificacion.setCompradorId(compradorId);
        notificacion.setPedidoId(11L);
        notificacion.setProductoId(7L);
        notificacion.setMensaje("El pedido llegó a Cali y está por ser entregado.");
        notificacion.setFechaCreacion(Instant.parse("2026-11-25T18:00:00Z"));
        when(notificacionService.listarPorComprador(compradorId))
                .thenReturn(List.of(new NotificacionResponseDTO(notificacion)));

        mockMvc.perform(get(URL + "/comprador").with(user(compradorId.toString())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[0].idPedido").value(11))
                .andExpect(jsonPath("$[0].idProducto").value(7))
                .andExpect(jsonPath("$[0].mensaje")
                        .value("El pedido llegó a Cali y está por ser entregado."));

        verify(notificacionService).listarPorComprador(compradorId);
    }

    @Test
    void rechazaConsultaSinAutenticacion() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(notificacionService);
    }

    @Test
    void rechazaConsultaDeUsuarioQueNoEsAgricultor() throws Exception {
        when(jwtService.esValido(TOKEN)).thenReturn(true);
        when(jwtService.obtenerUsuarioId(TOKEN)).thenReturn(9L);
        when(jwtService.obtenerRol(TOKEN)).thenReturn(RolUsuario.TRANSPORTISTA);

        mockMvc.perform(get(URL).header("Authorization", "Bearer " + TOKEN))
                .andExpect(status().isForbidden());

        verifyNoInteractions(notificacionService);
    }
}
