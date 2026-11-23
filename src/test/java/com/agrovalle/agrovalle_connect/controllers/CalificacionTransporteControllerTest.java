package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.models.CalificacionTransporte;
import com.agrovalle.agrovalle_connect.models.OrdenLogistica;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.security.SecurityConfig;
import com.agrovalle.agrovalle_connect.services.CalificacionTransporteService;
import com.agrovalle.agrovalle_connect.services.JwtService;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CalificacionTransporteController.class)
@Import({SecurityConfig.class, GlobalExceptionHandler.class})
class CalificacionTransporteControllerTest {

    private static final Long VIAJE_ID = 24L;
    private static final Long COMPRADOR_ID = 7L;
    private static final Long CONDUCTOR_ID = 13L;
    private static final String URL = "/api/v1/viajes/24/calificacion";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalificacionTransporteService calificacionService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void dadoCompradorAutenticado_cuandoCalifica_entoncesRegistraYRetorna201()
            throws Exception {
        when(calificacionService.registrar(
                VIAJE_ID, COMPRADOR_ID, 4, "Cajas en buen estado"))
                .thenReturn(calificacionRegistrada());

        mockMvc.perform(post(URL)
                        .with(user(COMPRADOR_ID.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "puntualidad": 4,
                                  "estado_cajas": "Cajas en buen estado"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.viajeId").value(VIAJE_ID))
                .andExpect(jsonPath("$.conductorId").value(CONDUCTOR_ID))
                .andExpect(jsonPath("$.puntualidad").value(4))
                .andExpect(jsonPath("$.estadoCajas").value("Cajas en buen estado"));

        verify(calificacionService).registrar(
                VIAJE_ID, COMPRADOR_ID, 4, "Cajas en buen estado");
    }

    @Test
    void rechazaSolicitudSinAutenticacion() throws Exception {
        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "puntualidad": 4,
                                  "estado_cajas": "Cajas en buen estado"
                                }
                                """))
                .andExpect(status().isUnauthorized());

        verifyNoInteractions(calificacionService);
    }

    @Test
    void rechazaDatosInvalidosAntesDeLlamarAlServicio() throws Exception {
        mockMvc.perform(post(URL)
                        .with(user(COMPRADOR_ID.toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "puntualidad": 6,
                                  "estado_cajas": ""
                                }
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(calificacionService);
    }

    private CalificacionTransporte calificacionRegistrada() {
        OrdenLogistica viaje = new OrdenLogistica();
        viaje.setId(VIAJE_ID);
        Transportista conductor = new Transportista();
        conductor.setId(CONDUCTOR_ID);
        CalificacionTransporte calificacion = new CalificacionTransporte();
        calificacion.setId(40L);
        calificacion.setViaje(viaje);
        calificacion.setConductor(conductor);
        calificacion.setPuntualidad((short) 4);
        calificacion.setEstadoCajas("Cajas en buen estado");
        calificacion.setFechaCalificacion(Instant.parse("2026-11-23T12:00:00Z"));
        return calificacion;
    }
}
