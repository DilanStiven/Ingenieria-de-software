package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.agrovalle.agrovalle_connect.dtos.LoginRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
import com.agrovalle.agrovalle_connect.exceptions.CorreoDuplicadoException;
import com.agrovalle.agrovalle_connect.exceptions.CredencialesInvalidasException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;

/**
 * Pruebas unitarias aisladas para la lógica de negocio de AgricultorService (HU-01 / Criterio 3).
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AgricultorService — Pruebas Unitarias de Negocio")
class AgricultorServiceTest {

    @Mock
    private AgricultorRepository agricultorRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AgricultorService agricultorService;

    private AgricultorRequestDTO requestValido;

    @BeforeEach
    void setUp() {
        requestValido = new AgricultorRequestDTO();
        requestValido.setNombre("Pedro Gomez");
        requestValido.setUbicacionValle("Dagua");
        requestValido.setCedula("1112223334");
        requestValido.setCorreo("Pedro@Example.com");
        requestValido.setPassword("ClaveSegura123");
    }

    @Test
    @DisplayName("DADO datos válidos, CUANDO se registra, ENTONCES guarda agricultor y emite token JWT")
    void dadoDatosValidos_cuandoRegistra_entoncesGuardaYRetornaToken() {
        // Arrange
        when(agricultorRepository.existsByCedula("1112223334")).thenReturn(false);
        when(agricultorRepository.existsByCorreoIgnoreCase("pedro@example.com")).thenReturn(false);
        when(passwordEncoder.encode("ClaveSegura123")).thenReturn("hash-bcrypt");
        when(jwtService.generarToken(1L)).thenReturn("token-simulado-jwt");

        when(agricultorRepository.save(any(Agricultor.class))).thenAnswer(invocation -> {
            Agricultor arg = invocation.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        // Act
        AgricultorResponseDTO respuesta = agricultorService.registrar(requestValido);

        // Assert
        assertNotNull(respuesta);
        assertEquals(1L, respuesta.getId());
        assertEquals("Pedro Gomez", respuesta.getNombre());
        assertEquals("Dagua", respuesta.getUbicacionValle());
        assertEquals("1112223334", respuesta.getCedula());
        assertEquals("token-simulado-jwt", respuesta.getToken());

        verify(passwordEncoder).encode("ClaveSegura123");
        verify(agricultorRepository).existsByCedula("1112223334");
        verify(agricultorRepository).existsByCorreoIgnoreCase("pedro@example.com");
        verify(agricultorRepository).save(any(Agricultor.class));
        verify(jwtService).generarToken(1L);
    }

    @Test
    @DisplayName("DADO cédula duplicada, CUANDO se registra, ENTONCES lanza CedulaDuplicadaException y no persiste")
    void dadoCedulaExistente_cuandoRegistra_entoncesLanzaExcepcion() {
        // Arrange
        when(agricultorRepository.existsByCedula("1112223334")).thenReturn(true);

        // Act & Assert
        assertThrows(CedulaDuplicadaException.class, () -> agricultorService.registrar(requestValido));

        verify(agricultorRepository).existsByCedula("1112223334");
        verify(agricultorRepository, never()).save(any(Agricultor.class));
        verify(jwtService, never()).generarToken(any());
    }

    @Test
    @DisplayName("DADO un correo registrado, CUANDO se registra, ENTONCES no persiste otra cuenta")
    void dadoCorreoExistente_cuandoRegistra_entoncesLanzaExcepcion() {
        when(agricultorRepository.existsByCorreoIgnoreCase("pedro@example.com")).thenReturn(true);

        assertThrows(CorreoDuplicadoException.class,
                () -> agricultorService.registrar(requestValido));

        verify(agricultorRepository, never()).save(any(Agricultor.class));
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    @DisplayName("DADO credenciales válidas, CUANDO inicia sesión, ENTONCES emite un JWT")
    void dadoCredencialesValidas_cuandoIniciaSesion_entoncesRetornaToken() {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setCorreo(" PEDRO@example.com ");
        login.setPassword("ClaveSegura123");

        Agricultor agricultor = new Agricultor();
        agricultor.setId(1L);
        agricultor.setNombre("Pedro Gomez");
        agricultor.setUbicacionValle("Dagua");
        agricultor.setCedula("1112223334");
        agricultor.setCorreo("pedro@example.com");
        agricultor.setPasswordHash("hash-bcrypt");

        when(agricultorRepository.findByCorreoIgnoreCase("pedro@example.com"))
                .thenReturn(java.util.Optional.of(agricultor));
        when(passwordEncoder.matches("ClaveSegura123", "hash-bcrypt")).thenReturn(true);
        when(jwtService.generarToken(1L)).thenReturn("token-simulado-jwt");

        AgricultorResponseDTO respuesta = agricultorService.autenticar(login);

        assertEquals(1L, respuesta.getId());
        assertEquals("token-simulado-jwt", respuesta.getToken());
        verify(passwordEncoder).matches("ClaveSegura123", "hash-bcrypt");
        verify(jwtService).generarToken(1L);
    }

    @Test
    @DisplayName("DADO contraseña incorrecta, CUANDO inicia sesión, ENTONCES rechaza credenciales")
    void dadoPasswordIncorrecta_cuandoIniciaSesion_entoncesLanzaExcepcion() {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setCorreo("pedro@example.com");
        login.setPassword("Incorrecta123");

        Agricultor agricultor = new Agricultor();
        agricultor.setPasswordHash("hash-bcrypt");
        when(agricultorRepository.findByCorreoIgnoreCase("pedro@example.com"))
                .thenReturn(java.util.Optional.of(agricultor));
        when(passwordEncoder.matches("Incorrecta123", "hash-bcrypt")).thenReturn(false);

        assertThrows(CredencialesInvalidasException.class,
                () -> agricultorService.autenticar(login));
        verify(jwtService, never()).generarToken(any());
    }

    @Test
    @DisplayName("DADO cuenta antigua sin contraseña, CUANDO inicia sesión, ENTONCES rechaza credenciales")
    void dadoUsuarioSinCredenciales_cuandoIniciaSesion_entoncesLanzaExcepcion() {
        LoginRequestDTO login = new LoginRequestDTO();
        login.setCorreo("pedro@example.com");
        login.setPassword("ClaveSegura123");

        Agricultor agricultorAntiguo = new Agricultor();
        when(agricultorRepository.findByCorreoIgnoreCase("pedro@example.com"))
                .thenReturn(java.util.Optional.of(agricultorAntiguo));

        assertThrows(CredencialesInvalidasException.class,
                () -> agricultorService.autenticar(login));
        verify(passwordEncoder, never()).matches(any(), any());
        verify(jwtService, never()).generarToken(any());
    }
}
