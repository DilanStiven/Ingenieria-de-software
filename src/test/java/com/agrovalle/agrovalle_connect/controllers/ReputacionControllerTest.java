package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.ReputacionResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.AgricultorNoEncontradoException;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.JwtService;
import com.agrovalle.agrovalle_connect.services.ReputacionService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ReputacionController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class ReputacionControllerTest {

    private static final Long AGRICULTOR_ID = 7L;
    private static final String URL = "/api/v1/agricultores/7/reputacion";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReputacionService reputacionService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void consultaReputacionPublicaYDevuelvePromedio() throws Exception {
        when(reputacionService.obtenerReputacion(AGRICULTOR_ID))
                .thenReturn(new ReputacionResponseDTO(
                        AGRICULTOR_ID, new BigDecimal("4.80")));

        mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.agricultorId").value(AGRICULTOR_ID))
                .andExpect(jsonPath("$.calificacionPromedio").value(4.80));

        verify(reputacionService).obtenerReputacion(AGRICULTOR_ID);
    }

    @Test
    void responde404CuandoElAgricultorNoExiste() throws Exception {
        when(reputacionService.obtenerReputacion(AGRICULTOR_ID))
                .thenThrow(new AgricultorNoEncontradoException(AGRICULTOR_ID));

        mockMvc.perform(get(URL))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void rechazaIdentificadorNoPositivo() throws Exception {
        mockMvc.perform(get("/api/v1/agricultores/0/reputacion"))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(reputacionService);
    }
}
