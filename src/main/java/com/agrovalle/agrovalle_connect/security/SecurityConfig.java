package com.agrovalle.agrovalle_connect.security;

import com.agrovalle.agrovalle_connect.services.JwtService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad stateless para AgroValle Connect.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** Ruta pública de autenticación. */
    private static final String AUTH_PATH = "/api/v1/auth/**";

    /** Ruta de productos de la API. */
    private static final String PRODUCTOS_PATH = "/api/v1/productos";

    /** Ruta antigua de productos (compatibilidad). */
    private static final String PRODUCTOS_LEGACY = "/api/productos/**";

    private final JwtService jwtService;

    /**
     * Crea la configuración de seguridad con el proveedor del servicio JWT.
     *
     * @param jwtServiceProvider proveedor del servicio de tokens JWT
     */
    public SecurityConfig(
            final ObjectProvider<JwtService> jwtServiceProvider) {
        this.jwtService = jwtServiceProvider.getIfAvailable();
    }

    /**
     * Define la cadena de filtros de seguridad.
     *
     * @param http configuración de seguridad HTTP
     * @return cadena de filtros configurada
     * @throws Exception si hay error en la configuración
     */
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(AUTH_PATH).permitAll()
                .requestMatchers(HttpMethod.GET, PRODUCTOS_PATH).permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(PRODUCTOS_LEGACY).authenticated()
                .anyRequest().authenticated())
            .exceptionHandling(e ->
                e.authenticationEntryPoint(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

        if (jwtService != null) {
            http.addFilterBefore(new JwtAuthenticationFilter(jwtService),
                UsernamePasswordAuthenticationFilter.class);
        }

        return http.build();
    }
}
