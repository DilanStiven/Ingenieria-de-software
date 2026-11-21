package com.agrovalle.agrovalle_connect.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Expone el estado operativo de AgroValle Connect en staging.
 */
@RestController
@RequestMapping("/api/v1/estado")
public class EstadoController {

    private final JdbcTemplate jdbcTemplate;
    private final Environment environment;

    /**
     * Crea el controlador de estado.
     *
     * @param jdbcTemplate acceso JDBC para verificar PostgreSQL
     * @param environment entorno activo de Spring
     */
    public EstadoController(final JdbcTemplate jdbcTemplate,
                            final Environment environment) {
        this.jdbcTemplate = jdbcTemplate;
        this.environment = environment;
    }

    /**
     * Comprueba la aplicacion y la conexion configurada.
     *
     * @return estado HTTP y datos de servicio
     */
    @GetMapping
    public ResponseEntity<Map<String, String>> obtenerEstado() {
        Map<String, String> respuesta = new LinkedHashMap<>();
        respuesta.put("aplicacion", "AgroValle Connect");

        boolean stagingActivo = false;
        for (String perfil : environment.getActiveProfiles()) {
            if ("staging".equals(perfil)) {
                stagingActivo = true;
                break;
            }
        }
        respuesta.put("perfil", stagingActivo ? "staging" : "default");

        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            respuesta.put("estado", "OK");
            respuesta.put("baseDatos", "PostgreSQL Connected");
            return ResponseEntity.ok(respuesta);
        } catch (DataAccessException exception) {
            respuesta.put("estado", "ERROR");
            respuesta.put("baseDatos", "PostgreSQL Disconnected");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(respuesta);
        }
    }
}
