package com.agrovalle.agrovalle_connect.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import com.agrovalle.agrovalle_connect.security.RolUsuario;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio que emite y valida tokens JWT de acceso para los usuarios.
 */
@Service
public class JwtService {

    private final SecretKey clave;
    private final long expiracionMs;

    /**
     * Crea el servicio con la clave secreta y el tiempo de vida del token.
     *
     * @param secreto clave secreta (mínimo 32 caracteres)
     * @param expiracionMs duración del token en milisegundos
     */
    public JwtService(@Value("${jwt.secret}") final String secreto,
            @Value("${jwt.expiration-ms}") final long expiracionMs) {
        this.clave = Keys.hmacShaKeyFor(secreto.getBytes(StandardCharsets.UTF_8));
        this.expiracionMs = expiracionMs;
    }

    /**
     * Genera un token firmado para el agricultor indicado.
     *
     * @param agricultorId identificador del agricultor
     * @return token JWT
     */
    public String generarToken(final Long agricultorId) {
        return generarToken(agricultorId, RolUsuario.AGRICULTOR);
    }

    /**
     * Genera un token firmado para el usuario y rol indicados.
     *
     * @param usuarioId identificador del usuario
     * @param rol rol del usuario
     * @return token JWT
     */
    public String generarToken(final Long usuarioId, final RolUsuario rol) {
        Date ahora = new Date();
        return Jwts.builder()
                .subject(String.valueOf(usuarioId))
                .claim("rol", rol.name())
                .issuedAt(ahora)
                .expiration(new Date(ahora.getTime() + expiracionMs))
                .signWith(clave)
                .compact();
    }

    /**
     * Indica si el token es auténtico y no ha expirado.
     *
     * @param token token JWT
     * @return true si es válido
     */
    public boolean esValido(final String token) {
        try {
            Claims claims = leerClaims(token);
            String subject = claims.getSubject();
            String rol = claims.get("rol", String.class);
            if (subject == null || rol == null) {
                return false;
            }
            Long.parseLong(subject);
            RolUsuario.valueOf(rol);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Obtiene el identificador del agricultor guardado en el token.
     *
     * @param token token JWT válido
     * @return id del agricultor
     */
    public Long obtenerAgricultorId(final String token) {
        return obtenerUsuarioId(token);
    }

    /**
     * Obtiene el identificador guardado en un token válido.
     *
     * @param token token JWT válido
     * @return id del usuario
     */
    public Long obtenerUsuarioId(final String token) {
        return Long.valueOf(leerClaims(token).getSubject());
    }

    /**
     * Obtiene el rol guardado en un token válido.
     *
     * @param token token JWT válido
     * @return rol del usuario
     */
    public RolUsuario obtenerRol(final String token) {
        return RolUsuario.valueOf(leerClaims(token).get("rol", String.class));
    }

    private Claims leerClaims(final String token) {
        return Jwts.parser().verifyWith(clave).build()
                .parseSignedClaims(token).getPayload();
    }
}
