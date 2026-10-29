package com.agrovalle.agrovalle_connect.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.agrovalle.agrovalle_connect.controllers.ProductoController;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.services.JwtService;
import com.agrovalle.agrovalle_connect.services.ProductoService;

@WebMvcTest(ProductoController.class)
@Import({ SecurityConfig.class, GlobalExceptionHandler.class })
@AutoConfigureMockMvc
@DisplayName("SecurityConfig — Pruebas de seguridad stateless (HU-01 y HU-02)")
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @MockitoBean
    private JwtService jwtService;

    /**
     * BDD Escenario 3 de HU-02 — Sin token JWT.
     * Given un usuario sin token,
     * When intenta publicar en POST /api/v1/productos,
     * Then el sistema responde 401 Unauthorized.
     */
    @Test
    @DisplayName("Escenario HU-02 E3: sin token -> 401 Unauthorized al publicar producto")
    void dadoSinToken_cuandoPublicaProducto_entonces401() throws Exception {
        mockMvc.perform(post("/api/v1/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Mango\"}"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * GET /api/v1/productos es publico segun SecurityConfig.
     * Given cualquier usuario (sin autenticacion),
     * When hace GET al catalogo,
     * Then el sistema no bloquea con 401.
     */
    @Test
    @DisplayName("GET /api/v1/productos es publico — no requiere autenticacion")
    void getCatalogosEsPublico() throws Exception {
        given(productoService.filtrarProductos(any(), any())).willReturn(List.of());

        mockMvc.perform(get("/api/v1/productos").param("municipio", "Dagua"))
                .andExpect(status().isOk());
    }

    /**
     * La ruta de registro es publica.
     * When se accede a /api/v1/auth/register sin token,
     * Then no se bloquea con 401 (responde 404 al no estar mapeada en
     * ProductoController).
     */
    @Test
    @DisplayName("Registro es publico — no requiere autenticacion")
    void registroEsPublico() throws Exception {
        mockMvc.perform(get("/api/v1/auth/register"))
                .andExpect(status().isNotFound());
    }
}
