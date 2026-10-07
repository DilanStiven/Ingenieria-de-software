package com.agrovalle.agrovalle_connect.security;

import com.agrovalle.agrovalle_connect.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Filtro JWT que valida el token en cada petición y establece la autenticación.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /** Prefijo esperado en el header de autorización. */
    private static final String BEARER_PREFIX = "Bearer ";

    /** Nombre del header de autorización HTTP. */
    private static final String AUTH_HEADER = "Authorization";

    private final JwtService jwtService;

    /**
     * Crea el filtro con el servicio JWT inyectado.
     *
     * @param jwtService servicio de validación de tokens
     */
    public JwtAuthenticationFilter(final JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(BEARER_PREFIX.length());

        if (jwtService.esValido(jwt)
                && SecurityContextHolder.getContext().getAuthentication() == null) {
            Long agricultorId = jwtService.obtenerAgricultorId(jwt);
            UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                    String.valueOf(agricultorId), null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
