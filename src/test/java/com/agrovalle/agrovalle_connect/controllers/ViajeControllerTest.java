package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.CheckpointViajeResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.models.CheckpointViaje;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.CheckpointService;
import com.agrovalle.agrovalle_connect.services.JwtService;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ViajeController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class ViajeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CheckpointService checkpointService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void transportistaAutenticadoRegistraLlegada() throws Exception {
        CheckpointViaje checkpoint = new CheckpointViaje();
        checkpoint.setId(3L);
        OrdenLogistica orden = new OrdenLogistica();
        orden.setId(21L);
        orden.setEstado(OrdenLogistica.Estado.EN_DESTINO);
        orden.setDestino("Cali");
        checkpoint.setOrdenLogistica(orden);
        checkpoint.setEstado(OrdenLogistica.Estado.EN_DESTINO);
        checkpoint.setUbicacion("Cali");
        checkpoint.setFechaHora(Instant.parse("2026-11-23T22:26:37Z"));

        when(checkpointService.registrarLlegada(21L, 15L))
                .thenReturn(new CheckpointViajeResponseDTO(checkpoint));

        mockMvc.perform(patch("/api/v1/viajes/21/llegada")
                        .with(user("15").authorities(() -> "ROLE_TRANSPORTISTA")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.viajeId").value(21))
                .andExpect(jsonPath("$.estado").value("EN_DESTINO"))
                .andExpect(jsonPath("$.ubicacion").value("Cali"));
    }

    @Test
    void llegadaSinAutenticacionRetorna401() throws Exception {
        mockMvc.perform(patch("/api/v1/viajes/21/llegada"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void agricultorNoPuedeRegistrarLlegada() throws Exception {
        mockMvc.perform(patch("/api/v1/viajes/21/llegada")
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR")))
                .andExpect(status().isForbidden());
    }
}
