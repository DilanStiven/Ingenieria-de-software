package com.agrovalle.agrovalle_connect.controllers;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.AgricultorRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
import com.agrovalle.agrovalle_connect.services.AgricultorService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Pruebas de integración para el registro de agricultores (HU-01) en la capa Web.
 */
@WebMvcTest(AgricultorController.class)
class AgricultorControllerTest {

    private static final String URL = "/api/v1/auth/register";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AgricultorService agricultorService;

    private String json(String nombre, String ubicacion, String cedula) {
        return """
                {
                  "nombre": "%s",
                  "ubicacionValle": "%s",
                  "cedula": "%s"
                }
                """.formatted(nombre, ubicacion, cedula);
    }

    @Test
    @DisplayName("Debe registrar un agricultor con datos válidos")
    void debeRegistrarAgricultorConDatosValidos() throws Exception {
        String cedulaUnica = "1234567890";
        
        when(agricultorService.registrar(any(AgricultorRequestDTO.class)))
            .thenReturn(new AgricultorResponseDTO(1L, "Juan Perez", "Dagua", cedulaUnica, "token-jwt"));

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Juan Perez", "Dagua", cedulaUnica)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    @Test
    @DisplayName("Debe rechazar con 409 una cédula ya registrada")
    void debeRechazarCedulaDuplicada() throws Exception {
        String body = json("Ana Gomez", "Palmira", "1000000001");

        when(agricultorService.registrar(any(AgricultorRequestDTO.class)))
            .thenReturn(new AgricultorResponseDTO(1L, "Ana Gomez", "Palmira", "1000000001", "token-jwt"))
            .thenThrow(new CedulaDuplicadaException("1000000001"));

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Debe rechazar con 400 los campos obligatorios vacíos")
    void debeRechazarCamposVacios() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("", "", "")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe rechazar con 400 una cédula con formato inválido")
    void debeRechazarCedulaConFormatoInvalido() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Maria Lopez", "Buga", "abc123")))
                .andExpect(status().isBadRequest());
    }
}