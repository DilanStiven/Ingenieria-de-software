package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.dtos.AsignacionRutaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.OrdenLogisticaResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.JwtService;
import com.agrovalle.agrovalle_connect.services.OrdenLogisticaService;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(OrdenLogisticaController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class OrdenLogisticaControllerTest {

    private static final String URL = "/api/v1/ordenes";
    private static final Long ORDEN_ID = 21L;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrdenLogisticaService ordenService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void agricultorCreaOrdenLogistica() throws Exception {
        when(ordenService.crear(any(OrdenLogisticaRequestDTO.class), eq(7L)))
                .thenReturn(respuesta(OrdenLogistica.Estado.PENDIENTE_ALISTAMIENTO));

        mockMvc.perform(post(URL)
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrden()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.estado").value("PENDIENTE_ALISTAMIENTO"))
                .andExpect(jsonPath("$.destino").value("Cali"));

        verify(ordenService).crear(any(OrdenLogisticaRequestDTO.class), eq(7L));
    }

    @Test
    void transportistaNoPuedeCrearOrdenLogistica() throws Exception {
        mockMvc.perform(post(URL)
                        .with(user("8").authorities(() -> "ROLE_TRANSPORTISTA"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrden()))
                .andExpect(status().isForbidden());
    }

    @Test
    void agricultorPuedeMarcarOrdenComoAlistada() throws Exception {
        when(ordenService.marcarAlistada(ORDEN_ID, 7L))
                .thenReturn(respuesta(OrdenLogistica.Estado.ALISTADO));

        mockMvc.perform(patch(URL + "/" + ORDEN_ID + "/alistado")
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ALISTADO"));
    }

    @Test
    void transportistaPuedeAsignarRutaConFechaYVehiculo() throws Exception {
        when(ordenService.asignarRuta(eq(ORDEN_ID), any(AsignacionRutaRequestDTO.class),
                eq(8L), eq(RolUsuario.TRANSPORTISTA)))
                .thenReturn(respuesta(OrdenLogistica.Estado.EN_RUTA));

        mockMvc.perform(post(URL + "/" + ORDEN_ID + "/ruta")
                        .with(user("8").authorities(() -> "ROLE_TRANSPORTISTA"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRuta()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("EN_RUTA"));
    }

    @Test
    void rechazaAsignacionSinFechaOSinVehiculo() throws Exception {
        mockMvc.perform(post(URL + "/" + ORDEN_ID + "/ruta")
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void rechazaCreacionSinAutenticacion() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOrden()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void agricultorListaSusOrdenes() throws Exception {
        when(ordenService.listar(7L, RolUsuario.AGRICULTOR))
                .thenReturn(List.of(respuesta(OrdenLogistica.Estado.ALISTADO)));

        mockMvc.perform(get(URL)
                        .with(user("7").authorities(() -> "ROLE_AGRICULTOR")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ORDEN_ID));
    }

    private OrdenLogisticaResponseDTO respuesta(final OrdenLogistica.Estado estado) {
        OrdenLogistica orden = new OrdenLogistica();
        orden.setId(ORDEN_ID);
        orden.setProducto("Café");
        orden.setPesoKg(new BigDecimal("1200.00"));
        orden.setDestino("Cali");
        orden.setEstado(estado);
        orden.setFechaCreacion(Instant.parse("2026-11-12T12:00:00Z"));
        if (estado == OrdenLogistica.Estado.EN_RUTA) {
            orden.setFechaSalida(LocalDate.now().plusDays(1));
            orden.setGuiaDespacho("GV-TEST");
            Vehiculo vehiculo = new Vehiculo();
            vehiculo.setId(12L);
            vehiculo.setPlaca("ABC-123");
            orden.setVehiculo(vehiculo);
        }
        return new OrdenLogisticaResponseDTO(orden);
    }

    private String jsonOrden() {
        return """
                {
                  "producto": "Café",
                  "pesoKg": 1200,
                  "destino": "Cali"
                }
                """;
    }

    private String jsonRuta() {
        return """
                {
                  "vehiculoId": 12,
                  "fechaSalida": "%s"
                }
                """.formatted(LocalDate.now().plusDays(1));
    }
}
