package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agrovalle.agrovalle_connect.dtos.VehiculoRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.VehiculoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PlacaVehiculoDuplicadaException;
import com.agrovalle.agrovalle_connect.exceptions.PropietarioNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.Transportista;
import com.agrovalle.agrovalle_connect.models.Vehiculo;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.TransportistaRepository;
import com.agrovalle.agrovalle_connect.repositories.VehiculoRepository;
import com.agrovalle.agrovalle_connect.security.RolUsuario;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private AgricultorRepository agricultorRepository;

    @Mock
    private TransportistaRepository transportistaRepository;

    @InjectMocks
    private VehiculoService vehiculoService;

    @Test
    void registraVehiculoNormalizandoPlacaYAsociandoAgricultor() {
        Agricultor agricultor = new Agricultor();
        agricultor.setId(7L);
        when(vehiculoRepository.existsByPlaca("ABC-123")).thenReturn(false);
        when(agricultorRepository.findById(7L)).thenReturn(Optional.of(agricultor));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenAnswer(invocation -> {
            Vehiculo vehiculo = invocation.getArgument(0);
            vehiculo.setId(12L);
            return vehiculo;
        });

        VehiculoResponseDTO respuesta = vehiculoService.registrar(
                solicitud("abc123", " Camión ", 1500), 7L, RolUsuario.AGRICULTOR);

        assertEquals(12L, respuesta.getId());
        assertEquals("ABC-123", respuesta.getPlaca());
        assertEquals("Camión", respuesta.getTipo());
        assertEquals(1500, respuesta.getCapacidadKg());
        assertEquals(Vehiculo.Estado.DISPONIBLE, respuesta.getEstado());
        verify(vehiculoRepository).save(any(Vehiculo.class));
    }

    @Test
    void registraVehiculoAsociandoTransportista() {
        Transportista transportista = new Transportista();
        transportista.setId(9L);
        when(vehiculoRepository.existsByPlaca("XYZ-900")).thenReturn(false);
        when(transportistaRepository.findById(9L)).thenReturn(Optional.of(transportista));
        when(vehiculoRepository.save(any(Vehiculo.class))).thenAnswer(invocation -> invocation.getArgument(0));

        vehiculoService.registrar(solicitud("XYZ-900", "Furgón", 2000),
                9L, RolUsuario.TRANSPORTISTA);

        verify(transportistaRepository).findById(9L);
        verify(vehiculoRepository).save(any(Vehiculo.class));
        verify(agricultorRepository, never()).findById(9L);
    }

    @Test
    void rechazaPlacaDuplicadaAntesDeBuscarPropietario() {
        when(vehiculoRepository.existsByPlaca("ABC-123")).thenReturn(true);

        assertThrows(PlacaVehiculoDuplicadaException.class,
                () -> vehiculoService.registrar(
                        solicitud("abc-123", "Camión", 1500), 7L, RolUsuario.AGRICULTOR));

        verify(agricultorRepository, never()).findById(7L);
        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    @Test
    void rechazaTokenAsociadoAPropietarioInexistente() {
        when(vehiculoRepository.existsByPlaca("ABC-123")).thenReturn(false);
        when(agricultorRepository.findById(7L)).thenReturn(Optional.empty());

        assertThrows(PropietarioNoEncontradoException.class,
                () -> vehiculoService.registrar(
                        solicitud("ABC-123", "Camión", 1500), 7L, RolUsuario.AGRICULTOR));

        verify(vehiculoRepository, never()).save(any(Vehiculo.class));
    }

    private VehiculoRequestDTO solicitud(final String placa, final String tipo,
            final Integer capacidadKg) {
        VehiculoRequestDTO dto = new VehiculoRequestDTO();
        dto.setPlaca(placa);
        dto.setTipo(tipo);
        dto.setCapacidadKg(capacidadKg);
        return dto;
    }
}
