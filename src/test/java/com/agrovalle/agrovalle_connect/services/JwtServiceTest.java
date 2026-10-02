package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias del servicio de tokens JWT.
 */
class JwtServiceTest {

    private static final String SECRETO = "clave-de-prueba-agrovalle-connect-minimo-32-bytes";

    /**
     * Given un token recién emitido, When se valida,
     * Then es válido y conserva el id del agricultor.
     */
    @Test
    @DisplayName("Debe generar un token válido con el id del agricultor")
    void debeGenerarTokenValido() {
        JwtService servicio = new JwtService(SECRETO, 60_000L);

        String token = servicio.generarToken(7L);

        assertTrue(servicio.esValido(token));
        assertEquals(7L, servicio.obtenerAgricultorId(token));
    }

    /**
     * Given un token ya expirado, When se valida, Then es inválido.
     */
    @Test
    @DisplayName("Debe rechazar un token expirado")
    void debeRechazarTokenExpirado() {
        JwtService servicio = new JwtService(SECRETO, -1_000L);

        String token = servicio.generarToken(7L);

        assertFalse(servicio.esValido(token));
    }

    /**
     * Given un texto que no es un JWT, When se valida, Then es inválido.
     */
    @Test
    @DisplayName("Debe rechazar un token manipulado")
    void debeRechazarTokenManipulado() {
        JwtService servicio = new JwtService(SECRETO, 60_000L);

        assertFalse(servicio.esValido("esto.no.es-un-token"));
    }
}