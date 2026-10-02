package com.agrovalle.agrovalle_connect.controllers;

import static org.hamcrest.Matchers.notNullValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

    private static final String URL = "/api/v1/auth/register";

    /**
     * Cliente simulado para realizar peticiones HTTP.
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Construye el JSON de registro con los datos indicados.
     */
    private String json(String nombre, String ubicacion, String cedula) {
        return """
                {
                  "nombre": "%s",
                  "ubicacionValle": "%s",
                  "cedula": "%s"
                }
                """.formatted(nombre, ubicacion, cedula);
    }

    /**
     * Given un JSON válido, When se envía a /api/v1/auth/register,
     * Then responde 201 y retorna el agricultor creado con un ID.
     */
    @Test
    @DisplayName("Debe registrar un agricultor con datos válidos")
    void debeRegistrarAgricultorConDatosValidos() throws Exception {
        String cedulaUnica = String.valueOf(1_000_000_000L + (System.currentTimeMillis() % 9_000_000_000L));

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Juan Perez", "Dagua", cedulaUnica)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    /**
     * Given una cédula ya registrada, When se registra de nuevo,
     * Then responde 409 Conflict.
     */
    @Test
    @DisplayName("Debe rechazar con 409 una cédula ya registrada")
    void debeRechazarCedulaDuplicada() throws Exception {
        String body = json("Ana Gomez", "Palmira", "1000000001");

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());
    }

    /**
     * Given campos obligatorios vacíos, When se envía el registro,
     * Then responde 400 Bad Request.
     */
    @Test
    @DisplayName("Debe rechazar con 400 los campos obligatorios vacíos")
    void debeRechazarCamposVacios() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("", "", "")))
                .andExpect(status().isBadRequest());
    }

    /**
     * Given una cédula con formato inválido, When se envía el registro,
     * Then responde 400 Bad Request.
     */
    @Test
    @DisplayName("Debe rechazar con 400 una cédula con formato inválido")
    void debeRechazarCedulaConFormatoInvalido() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Maria Lopez", "Buga", "abc123")))
                .andExpect(status().isBadRequest());
    }
}