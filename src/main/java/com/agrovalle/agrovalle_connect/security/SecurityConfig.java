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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    /** Ruta de consulta de precios regionales. */
    private static final String PRECIOS_PATH = "/api/v1/precios/**";

    /** Ruta antigua de productos (compatibilidad). */
    private static final String PRODUCTOS_LEGACY = "/api/productos/**";

    /** Ruta para registrar vehículos de carga. */
    private static final String VEHICULOS_PATH = "/api/v1/vehiculos";

    /** Ruta de órdenes logísticas. */
    private static final String ORDENES_PATH = "/api/v1/ordenes";

    /** Ruta para enviar solicitudes de contacto. */
    private static final String CONTACTO_PATH = "/api/v1/contacto/mensaje";

    /** Ruta para procesar pagos de pedidos. */
    private static final String PAGOS_PATH = "/api/v1/pagos";

    /** Ruta para registrar calificaciones de transporte. */
    private static final String CALIFICACIONES_VIAJE_PATH =
            "/api/v1/viajes/*/calificacion";

    /** Ruta para consultar la billetera del agricultor. */
    private static final String BILLETERA_PATH = "/api/v1/billetera";

    /** Ruta para consultar las notificaciones del agricultor. */
    private static final String NOTIFICACIONES_PATH = "/api/v1/notificaciones";

    /** Ruta para guardar calificaciones de órdenes entregadas. */
    private static final String RESENAS_PATH = "/api/v1/resenas";

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
                .requestMatchers("/api/v1/estado").permitAll()
                .requestMatchers(AUTH_PATH).permitAll()
                .requestMatchers(HttpMethod.POST, PRODUCTOS_PATH).hasRole("AGRICULTOR")
                .requestMatchers(HttpMethod.GET, PRODUCTOS_PATH).permitAll()
                .requestMatchers(HttpMethod.GET, PRECIOS_PATH).permitAll()
                .requestMatchers(HttpMethod.POST, CONTACTO_PATH).authenticated()
                .requestMatchers(HttpMethod.POST, PAGOS_PATH).authenticated()
                .requestMatchers(HttpMethod.POST, CALIFICACIONES_VIAJE_PATH).authenticated()
                .requestMatchers(HttpMethod.GET, BILLETERA_PATH + "/**")
                    .hasRole("AGRICULTOR")
                .requestMatchers(HttpMethod.GET, NOTIFICACIONES_PATH)
                    .hasRole("AGRICULTOR")
                .requestMatchers(HttpMethod.POST, RESENAS_PATH).authenticated()
                .requestMatchers(HttpMethod.POST, ORDENES_PATH + "/*/resena")
                    .authenticated()
                .requestMatchers(HttpMethod.POST, VEHICULOS_PATH)
                    .hasAnyRole("AGRICULTOR", "TRANSPORTISTA")
                .requestMatchers(HttpMethod.POST, ORDENES_PATH)
                    .hasRole("AGRICULTOR")
                .requestMatchers(HttpMethod.PATCH, ORDENES_PATH + "/*/alistado")
                    .hasRole("AGRICULTOR")
                .requestMatchers(HttpMethod.POST, ORDENES_PATH + "/*/ruta")
                    .hasAnyRole("AGRICULTOR", "TRANSPORTISTA")
                .requestMatchers(HttpMethod.GET, ORDENES_PATH, ORDENES_PATH + "/**")
                    .hasAnyRole("AGRICULTOR", "TRANSPORTISTA")
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

    /**
     * Proporciona BCrypt para almacenar contraseñas como hashes no reversibles.
     *
     * @return codificador BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
