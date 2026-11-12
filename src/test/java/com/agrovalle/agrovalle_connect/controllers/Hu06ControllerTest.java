package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.agrovalle.agrovalle_connect.dtos.TransportistaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.TransportistaResponseDTO;
import com.agrovalle.agrovalle_connect.dtos.VehiculoRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.VehiculoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.exceptions.PlacaVehiculoDuplicadaException;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.JwtService;
import com.agrovalle.agrovalle_connect.services.TransportistaService;
import com.agrovalle.agrovalle_connect.services.VehiculoService;
@WebMvcTest({TransportistaController.class, VehiculoController.class})
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class Hu06ControllerTest {

    private static final String URL_VEHICULOS = "/api/v1/vehiculos";
    private static final String URL_REGISTRO_TRANSPORTISTA =
            "/api/v1/auth/register-transportista";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransportistaService transportistaService;

    @MockitoBean
    private VehiculoService vehiculoService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void registraTransportistaConRespuesta201() throws Exception {
        when(transportistaService.registrar(any(TransportistaRequestDTO.class)))
                .thenReturn(new TransportistaResponseDTO(
                        3L, "Luis Perez", "12345678", "token"));

        mockMvc.perform(post(URL_REGISTRO_TRANSPORTISTA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Luis Perez\",\"cedula\":\"12345678\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(3))
                .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    void registraVehiculoDelAgricultorAutenticado() throws Exception {
        when(vehiculoService.registrar(any(VehiculoRequestDTO.class), eq(7L),
                eq(RolUsuario.AGRICULTOR)))
                .thenReturn(new VehiculoResponseDTO(1L, "ABC-123", "Camión",
                        1500, Vehiculo.Estado.DISPONIBLE));

        mockMvc.perform(post(URL_VEHICULOS)
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson("abc123", "Camión", 1500)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.placa").value("ABC-123"))
                .andExpect(jsonPath("$.estado").value("DISPONIBLE"));

        verify(vehiculoService).registrar(any(VehiculoRequestDTO.class), eq(7L),
                eq(RolUsuario.AGRICULTOR));
    }

    @Test
    void registraVehiculoDelTransportistaAutenticado() throws Exception {
        when(vehiculoService.registrar(any(VehiculoRequestDTO.class), eq(9L),
                eq(RolUsuario.TRANSPORTISTA)))
                .thenReturn(new VehiculoResponseDTO(2L, "XYZ-900", "Furgón",
                        2000, Vehiculo.Estado.DISPONIBLE));

        mockMvc.perform(post(URL_VEHICULOS)
                        .with(user("9").authorities(() -> "ROLE_TRANSPORTISTA"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson("XYZ-900", "Furgón", 2000)))
                .andExpect(status().isCreated());

        verify(vehiculoService).registrar(any(VehiculoRequestDTO.class), eq(9L),
                eq(RolUsuario.TRANSPORTISTA));
    }

    @Test
    void tokenJwtDeTransportistaAutorizaElRegistroDeVehiculos() throws Exception {
        when(jwtService.esValido("token-transportista")).thenReturn(true);
        when(jwtService.obtenerUsuarioId("token-transportista")).thenReturn(9L);
        when(jwtService.obtenerRol("token-transportista")).thenReturn(RolUsuario.TRANSPORTISTA);
        when(vehiculoService.registrar(any(VehiculoRequestDTO.class), eq(9L),
                eq(RolUsuario.TRANSPORTISTA)))
                .thenReturn(new VehiculoResponseDTO(2L, "XYZ-900", "Furgón",
                        2000, Vehiculo.Estado.DISPONIBLE));

        mockMvc.perform(post(URL_VEHICULOS)
                        .header("Authorization", "Bearer token-transportista")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson("XYZ-900", "Furgón", 2000)))
                .andExpect(status().isCreated());

        verify(vehiculoService).registrar(any(VehiculoRequestDTO.class), eq(9L),
                eq(RolUsuario.TRANSPORTISTA));
    }

    @Test
    void rechazaRegistroDeVehiculoSinAutenticacion() throws Exception {
        mockMvc.perform(post(URL_VEHICULOS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson("ABC-123", "Camión", 1500)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void rechazaRegistroConRolNoPermitido() throws Exception {
        mockMvc.perform(post(URL_VEHICULOS)
                        .with(user("5").roles("COMERCIANTE"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson("ABC-123", "Camión", 1500)))
                .andExpect(status().isForbidden());
    }

    @Test
    void rechazaCapacidadNoPositiva() throws Exception {
        mockMvc.perform(post(URL_VEHICULOS)
                        .with(user("7").roles("AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson("ABC-123", "Camión", 0)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rechazaPlacaConFormatoInvalido() throws Exception {
        mockMvc.perform(post(URL_VEHICULOS)
                        .with(user("7").roles("AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson("A-123", "Camión", 1500)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void responde409CuandoLaPlacaYaExiste() throws Exception {
        when(vehiculoService.registrar(any(VehiculoRequestDTO.class), eq(7L),
                eq(RolUsuario.AGRICULTOR)))
                .thenThrow(new PlacaVehiculoDuplicadaException("ABC-123"));

        mockMvc.perform(post(URL_VEHICULOS)
                        .with(user("7").roles("AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(vehiculoJson("ABC-123", "Camión", 1500)))
                .andExpect(status().isConflict());
    }

    @Test
    void responde409CuandoCedulaDelTransportistaYaExiste() throws Exception {
        when(transportistaService.registrar(any(TransportistaRequestDTO.class)))
                .thenThrow(new CedulaDuplicadaException("12345678"));

        mockMvc.perform(post(URL_REGISTRO_TRANSPORTISTA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Luis Perez\",\"cedula\":\"12345678\"}"))
                .andExpect(status().isConflict());
    }

    private String vehiculoJson(final String placa, final String tipo, final int capacidadKg) {
        return """
                {
                  "placa": "%s",
                  "tipo": "%s",
                  "capacidadKg": %d
                }
                """.formatted(placa, tipo, capacidadKg);
    }
}
