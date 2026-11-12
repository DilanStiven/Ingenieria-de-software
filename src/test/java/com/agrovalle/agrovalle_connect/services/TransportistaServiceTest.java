package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agrovalle.agrovalle_connect.dtos.TransportistaRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.TransportistaResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CedulaDuplicadaException;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.TransportistaRepository;
import com.agrovalle.agrovalle_connect.security.RolUsuario;

@ExtendWith(MockitoExtension.class)
class TransportistaServiceTest {

    @Mock
    private AgricultorRepository agricultorRepository;

    @Mock
    private TransportistaRepository transportistaRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private TransportistaService transportistaService;

    @Test
    void registraTransportistaYGeneraTokenConRolCorrecto() {
        when(agricultorRepository.existsByCedula("12345678")).thenReturn(false);
        when(transportistaRepository.existsByCedula("12345678")).thenReturn(false);
        when(transportistaRepository.save(any(Transportista.class))).thenAnswer(invocation -> {
            Transportista transportista = invocation.getArgument(0);
            transportista.setId(8L);
            return transportista;
        });
        when(jwtService.generarToken(8L, RolUsuario.TRANSPORTISTA)).thenReturn("jwt-transportista");

        TransportistaResponseDTO respuesta = transportistaService.registrar(solicitud());

        assertEquals(8L, respuesta.getId());
        assertEquals("Luis Perez", respuesta.getNombre());
        assertEquals("jwt-transportista", respuesta.getToken());
        verify(jwtService).generarToken(8L, RolUsuario.TRANSPORTISTA);
    }

    @Test
    void rechazaCedulaYaUsadaPorAgricultor() {
        when(agricultorRepository.existsByCedula("12345678")).thenReturn(true);

        assertThrows(CedulaDuplicadaException.class,
                () -> transportistaService.registrar(solicitud()));

        verify(transportistaRepository, never()).save(any(Transportista.class));
    }

    private TransportistaRequestDTO solicitud() {
        TransportistaRequestDTO dto = new TransportistaRequestDTO();
        dto.setNombre(" Luis Perez ");
        dto.setCedula("12345678");
        return dto;
    }
}
