package com.agrovalle.agrovalle_connect.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Locale;
import java.util.UUID;
import java.util.Map;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
            "spring.datasource.url=jdbc:h2:mem:auth_integration;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
            "spring.datasource.username=sa",
            "spring.datasource.password=",
            "spring.datasource.driver-class-name=org.h2.Driver",
            "jwt.expiration-ms=86400000"
        })
class AgricultorAuthIntegrationTest {

    private static final long TOKEN_DURACION_24_HORAS_MS = 86_400_000L;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${local.server.port}")
    private int port;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Test
    void registraConHashEIniciaSesionConCorreoYContrasena() throws Exception {
        String correo = "auth-" + UUID.randomUUID() + "@example.com";
        String cedula = "76543210";
        String password = "ClaveSegura123";
        HttpResponse<String> resultadoRegistro = post(
                "/api/v1/auth/register",
                Map.of("nombre", "Agricultor de prueba", "ubicacionValle", "Dagua",
                        "cedula", cedula, "correo", correo, "password", password));
        assertEquals(HttpStatus.CREATED.value(), resultadoRegistro.statusCode());
        assertFalse(token(resultadoRegistro).isBlank());

        Map<String, String> login = Map.of(
                "correo", correo.toUpperCase(Locale.ROOT), "password", password);
        HttpResponse<String> resultadoLogin = post("/api/v1/auth/login", login);
        assertEquals(HttpStatus.OK.value(), resultadoLogin.statusCode());
        String tokenLogin = token(resultadoLogin);
        assertFalse(tokenLogin.isBlank());
        assertEquals(TOKEN_DURACION_24_HORAS_MS, obtenerDuracionToken(tokenLogin));

        HttpResponse<String> resultadoPasswordIncorrecta = post(
                "/api/v1/auth/login",
                Map.of("correo", correo, "password", "ContrasenaIncorrecta"));
        assertEquals(HttpStatus.UNAUTHORIZED.value(), resultadoPasswordIncorrecta.statusCode());
    }

    private HttpResponse<String> post(final String path, final Map<String, String> body)
            throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                .build();
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String token(final HttpResponse<String> response) throws Exception {
        return objectMapper.readTree(response.body()).path("token").asText();
    }

    private long obtenerDuracionToken(final String token) {
        Claims claims = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getExpiration().getTime() - claims.getIssuedAt().getTime();
    }
}
