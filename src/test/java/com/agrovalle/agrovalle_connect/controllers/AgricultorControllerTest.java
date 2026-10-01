package com.agrovalle.agrovalle_connect.controllers;

import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Transactional;

/**
 * Pruebas de integración para el registro de agricultores (HU-01).
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AgricultorControllerTest {

    /**
     * Cliente simulado para realizar peticiones HTTP.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Given un JSON válido, When se envía a /api/v1/auth/register,
     * Then responde 201 y retorna el agricultor creado con un ID.
     */
    @Test
    @DisplayName("Debe registrar un agricultor con datos válidos")
    void debeRegistrarAgricultorConDatosValidos() throws Exception {
        String cedulaUnica = String.valueOf(1_000_000_000L + (System.currentTimeMillis() % 9_000_000_000L));
        String json = """
                {
                  "nombre": "Juan Perez",
                  "ubicacionValle": "Dagua",
                  "cedula": "%s"
                }
                """.formatted(cedulaUnica);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }
}