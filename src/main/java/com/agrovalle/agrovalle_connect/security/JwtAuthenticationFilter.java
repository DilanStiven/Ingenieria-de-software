package com.agrovalle.agrovalle_connect.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Obtener el encabezado que contiene el token
        final String authHeader = request.getHeader("Authorization");
        
        // 2. Si no hay token o no empieza con "Bearer ", continuamos sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        // Aquí extraemos el token quitando la palabra "Bearer "
        final String jwt = authHeader.substring(7);
        
        // (La lógica de validación y extracción de usuario va aquí)
        
        // Continuar con la petición
        filterChain.doFilter(request, response);
    }
}