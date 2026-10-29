package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.ProductoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.FechaCosechaInvalidaException;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.services.ProductoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductoController.class)
@Import({ GlobalExceptionHandler.class, SecurityConfig.class })
@AutoConfigureMockMvc
@DisplayName("ProductoController — Pruebas Integración HU-02: Publicación de Productos")
class ProductoControllerPublicarTest {

    private static final String URL = "/api/v1/productos";
    private static final Long PRODUCTO_ID = 1L;
    private static final String BODY_VALIDO = """
            {
              "nombre": "Mango",
              "categoria": "Frutas",
              "cantidad": 50.0,
              "precioUnitario": 2500.0,
              "fechaCosecha": "2027-06-15"
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    /**
     * BDD Escenario 1 — Publicacion exitosa.
     * Given agricultor autenticado y datos validos,
     * When POST /api/v1/productos,
     * Then responde 201 Created con el ID del producto.
     */
    @Test
    @WithMockUser(username = "1")
    @DisplayName("Escenario 1: datos validos con token -> 201 Created con ID")
    void dadoDatosValidos_cuandoPublica_entonces201ConId() throws Exception {
        given(productoService.publicar(any(), eq(PRODUCTO_ID)))
                .willReturn(new ProductoResponseDTO(PRODUCTO_ID, "Producto publicado exitosamente"));

        mockMvc.perform(post(URL)
                .with(user("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(BODY_VALIDO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(PRODUCTO_ID))
                .andExpect(jsonPath("$.mensaje").value("Producto publicado exitosamente"));
    }

    /**
     * BDD Escenario 2 — Fecha de cosecha anterior a hoy.
     * Given agricultor autenticado,
     * When envia fecha_cosecha pasada,
     * Then el sistema responde 400 Bad Request.
     */
    @Test
    @WithMockUser(username = "1")
    @DisplayName("Escenario 2: fecha pasada -> 400 Bad Request")
    void dadoFechaPasada_cuandoPublica_entonces400() throws Exception {
        String bodyFechaPasada = """
                {
                  "nombre": "Mango",
                  "categoria": "Frutas",
                  "cantidad": 50.0,
                  "precioUnitario": 2500.0,
                  "fechaCosecha": "2020-01-01"
                }
                """;

        given(productoService.publicar(any(), any()))
                .willThrow(new FechaCosechaInvalidaException(
                        "La fecha de cosecha no puede ser anterior a hoy"));

        mockMvc.perform(post(URL)
                .with(user("1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyFechaPasada))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value("La fecha de cosecha no puede ser anterior a hoy"));
    }
}
