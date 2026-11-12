package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.PrecioPromedioResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.exceptions.PrecioNoDisponibleException;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.JwtService;
import com.agrovalle.agrovalle_connect.services.PrecioService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PrecioController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class PrecioControllerTest {

    private static final String URL = "/api/v1/precios/promedio";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PrecioService precioService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void consultaPromedioPublicoYDevuelveLasTransaccionesConsideradas() throws Exception {
        when(precioService.obtenerPromedio("Café"))
                .thenReturn(new PrecioPromedioResponseDTO("Café",
                        new BigDecimal("1234.50"), "COP", "kg", 50L));

        mockMvc.perform(get(URL).param("producto", "Café"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.producto").value("Café"))
                .andExpect(jsonPath("$.precioPromedio").value(1234.50))
                .andExpect(jsonPath("$.moneda").value("COP"))
                .andExpect(jsonPath("$.unidadPrecio").value("kg"))
                .andExpect(jsonPath("$.cantidadTransacciones").value(50));
    }

    @Test
    void responde404CuandoNoHayTransaccionesRecientes() throws Exception {
        when(precioService.obtenerPromedio("Café"))
                .thenThrow(new PrecioNoDisponibleException("Café"));

        mockMvc.perform(get(URL).param("producto", "Café"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void rechazaNombreDeProductoEnBlanco() throws Exception {
        mockMvc.perform(get(URL).param("producto", " "))
                .andExpect(status().isBadRequest());
    }
}
