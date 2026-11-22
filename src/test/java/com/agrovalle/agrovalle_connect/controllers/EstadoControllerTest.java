package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Map;

@DisplayName("EstadoController - Verificacion de staging")
class EstadoControllerTest {

    @Test
    @DisplayName("Estado OK cuando PostgreSQL responde")
    void estadoOkCuandoPostgresqlResponde() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        Environment environment = mock(Environment.class);
        when(environment.getActiveProfiles()).thenReturn(new String[]{"staging"});
        when(jdbcTemplate.queryForObject(eq("SELECT 1"), eq(Integer.class)))
                .thenReturn(1);

        EstadoController controller =
                new EstadoController(jdbcTemplate, environment);
        ResponseEntity<Map<String, String>> respuesta =
                controller.obtenerEstado();

        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("AgroValle Connect",
                respuesta.getBody().get("aplicacion"));
        assertEquals("OK", respuesta.getBody().get("estado"));
        assertEquals("staging", respuesta.getBody().get("perfil"));
        assertEquals("PostgreSQL Connected",
                respuesta.getBody().get("baseDatos"));
    }

    @Test
    @DisplayName("Estado 503 cuando PostgreSQL no responde")
    void estado503CuandoPostgresqlNoResponde() {
        JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);
        Environment environment = mock(Environment.class);
        when(environment.getActiveProfiles()).thenReturn(new String[]{"staging"});
        when(jdbcTemplate.queryForObject(eq("SELECT 1"), eq(Integer.class)))
                .thenThrow(new DataAccessResourceFailureException("No disponible"));

        EstadoController controller =
                new EstadoController(jdbcTemplate, environment);
        ResponseEntity<Map<String, String>> respuesta =
                controller.obtenerEstado();

        assertEquals(HttpStatus.SERVICE_UNAVAILABLE,
                respuesta.getStatusCode());
        assertEquals("ERROR", respuesta.getBody().get("estado"));
        assertEquals("PostgreSQL Disconnected",
                respuesta.getBody().get("baseDatos"));
    }
}
