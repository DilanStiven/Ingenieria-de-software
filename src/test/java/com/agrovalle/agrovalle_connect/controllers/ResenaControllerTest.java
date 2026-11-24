package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.ResenaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.ResenaResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.models.Resena;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.JwtService;
import com.agrovalle.agrovalle_connect.services.ResenaService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ResenaController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class ResenaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ResenaService resenaService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void compradorAutenticadoPuedeCrearResenaPorOrden() throws Exception {
        Resena resena = new Resena();
        resena.setId(55L);
        OrdenCompra orden = new OrdenCompra();
        orden.setId(21L);
        resena.setOrdenCompra(orden);
        ResenaResponseDTO response = new ResenaResponseDTO(
                resena, new BigDecimal("5.00"));
        when(resenaService.guardarPorOrden(
                eq(21L), any(ResenaRequestDTO.class), eq(90L)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/ordenes/21/resena")
                        .with(user("90").authorities(() -> "ROLE_COMERCIANTE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "estrellas": 5,
                                  "comentario": "Excelente tomate"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void rechazaResenaSinAutenticacion() throws Exception {
        mockMvc.perform(post("/api/v1/ordenes/21/resena")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "estrellas": 5
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rechazaEstrellasFueraDelRangoBDD() throws Exception {
        mockMvc.perform(post("/api/v1/ordenes/21/resena")
                        .with(user("90").authorities(() -> "ROLE_COMERCIANTE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "estrellas": 6,
                                  "comentario": "Calificación inválida"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rechazaComentarioExcesivamenteLargo() throws Exception {
        String comentario = "x".repeat(1001);

        mockMvc.perform(post("/api/v1/ordenes/21/resena")
                        .with(user("90").authorities(() -> "ROLE_COMERCIANTE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "estrellas": 5,
                                  "comentario": "%s"
                                }
                                """.formatted(comentario)))
                .andExpect(status().isBadRequest());
    }
}
