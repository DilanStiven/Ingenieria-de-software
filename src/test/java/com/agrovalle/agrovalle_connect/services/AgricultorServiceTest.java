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

import com.agrovalle.agrovalle_connect.dtos.AgricultorRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.AgricultorResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
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

    @InjectMocks
    private AgricultorService agricultorService;

    private AgricultorRequestDTO requestValido;

    @BeforeEach
    void setUp() {
        requestValido = new AgricultorRequestDTO();
        requestValido.setNombre("Pedro Gomez");
        requestValido.setUbicacionValle("Dagua");
        requestValido.setCedula("1112223334");
    }

    @Test
    @DisplayName("DADO datos válidos, CUANDO se registra, ENTONCES guarda agricultor y emite token JWT")
    void dadoDatosValidos_cuandoRegistra_entoncesGuardaYRetornaToken() {
        // Arrange
        when(agricultorRepository.existsByCedula("1112223334")).thenReturn(false);
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

        verify(agricultorRepository).existsByCedula("1112223334");
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
}
