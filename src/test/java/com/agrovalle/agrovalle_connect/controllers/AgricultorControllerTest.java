package com.agrovalle.agrovalle_connect.controllers;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.agrovalle.agrovalle_connect.dtos.AgricultorRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
import com.agrovalle.agrovalle_connect.exceptions.CredencialesInvalidasException;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.services.AgricultorService;

/**
 * Pruebas de integración web para el registro de agricultores (HU-01).
 */
@WebMvcTest(AgricultorController.class)
@Import(GlobalExceptionHandler.class)
class AgricultorControllerTest {

    private static final String URL = "/api/v1/auth/register";
    private static final String LOGIN_URL = "/api/v1/auth/login";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AgricultorService agricultorService;

    /**
     * Construye el JSON de registro con los datos indicados.
     *
     * @param nombre nombre del agricultor
     * @param ubicacion ubicación en el Valle
     * @param cedula número de cédula
     * @param correo correo electrónico
     * @param password contraseña
     * @return cuerpo JSON para el endpoint
     */
    private String json(final String nombre, final String ubicacion, final String cedula,
            final String correo, final String password) {
        return """
                {
                  "nombre": "%s",
                  "ubicacionValle": "%s",
                  "cedula": "%s",
                  "correo": "%s",
                  "password": "%s"
                }
                """.formatted(nombre, ubicacion, cedula, correo, password);
    }

    /**
     * Given un JSON válido, When se envía a /api/v1/auth/register,
     * Then responde 201 y retorna el agricultor creado con un ID.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Debe registrar un agricultor con datos válidos")
    void debeRegistrarAgricultorConDatosValidos() throws Exception {
        String cedula = "1234567890";
        when(agricultorService.registrar(any(AgricultorRequestDTO.class)))
                .thenReturn(new AgricultorResponseDTO(1L, "Juan Perez", "Dagua", cedula, "token-jwt"));

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Juan Perez", "Dagua", cedula, "juan@example.com",
                                "ClaveSegura123")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    /**
     * Given una cédula ya registrada, When se registra de nuevo,
     * Then responde 409 Conflict.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Debe rechazar con 409 una cédula duplicada")
    void debeRechazarCedulaDuplicada() throws Exception {
        String cedula = "1000000001";
        String body = json("Ana Gomez", "Palmira", cedula, "ana@example.com", "ClaveSegura123");
        when(agricultorService.registrar(any(AgricultorRequestDTO.class)))
                .thenReturn(new AgricultorResponseDTO(1L, "Ana Gomez", "Palmira", cedula, "token-jwt"))
                .thenThrow(new CedulaDuplicadaException(cedula));

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isCreated());

        mockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isConflict());
    }

    /**
     * Given campos obligatorios vacíos, When se envía el registro,
     * Then responde 400 Bad Request.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Debe rechazar con 400 los campos obligatorios vacíos")
    void debeRechazarCamposVacios() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("", "", "", "", "")))
                .andExpect(status().isBadRequest());
    }

    /**
     * Given una cédula con formato inválido, When se envía el registro,
     * Then responde 400 Bad Request.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Debe rechazar con 400 una cédula con formato inválido")
    void debeRechazarCedulaConFormatoInvalido() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Maria Lopez", "Buga", "abc123", "maria@example.com",
                                "ClaveSegura123")))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe iniciar sesión y devolver un JWT")
    void debeIniciarSesionConCredencialesValidas() throws Exception {
        when(agricultorService.autenticar(any()))
                .thenReturn(new AgricultorResponseDTO(
                        1L, "Juan Perez", "Dagua", "1234567890", "token-jwt"));

        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "correo": "juan@example.com",
                                  "password": "ClaveSegura123"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.token").value("token-jwt"));
    }

    @Test
    @DisplayName("Debe responder 401 cuando las credenciales de inicio no son válidas")
    void debeRechazarCredencialesInvalidas() throws Exception {
        when(agricultorService.autenticar(any()))
                .thenThrow(new CredencialesInvalidasException());

        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "correo": "juan@example.com",
                                  "password": "Incorrecta123"
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Debe rechazar registro sin correo y contraseña")
    void debeRechazarRegistroSinCredenciales() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nombre": "Juan Perez",
                                  "ubicacionValle": "Dagua",
                                  "cedula": "1234567890"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debe rechazar contraseñas que exceden el límite de bytes de BCrypt")
    void debeRechazarPasswordQueExcedeLimiteBcrypt() throws Exception {
        String password = "á".repeat(40);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json("Juan Perez", "Dagua", "1234567890",
                                "juan@example.com", password)))
                .andExpect(status().isBadRequest());

        mockMvc.perform(post(LOGIN_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "correo": "juan@example.com",
                                  "password": "%s"
                                }
                                """.formatted(password)))
                .andExpect(status().isBadRequest());
    }
}
