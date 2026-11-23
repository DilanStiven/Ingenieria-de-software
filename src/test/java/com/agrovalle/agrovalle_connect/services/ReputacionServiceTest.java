package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.ReputacionResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.AgricultorNoEncontradoException;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.ResenaRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReputacionServiceTest {

    private static final Long AGRICULTOR_ID = 7L;

    @Mock
    private ResenaRepository resenaRepository;

    @Mock
    private AgricultorRepository agricultorRepository;

    @InjectMocks
    private ReputacionService reputacionService;

    @Test
    void dadoAgricultorConResenas_cuandoCalculaPromedio_entoncesRedondeaADosDecimales() {
        when(resenaRepository.calcularPromedioPorAgricultor(AGRICULTOR_ID))
                .thenReturn(4.76D);

        BigDecimal promedio =
                reputacionService.calcularPromedioEstrellas(AGRICULTOR_ID);

        assertEquals(new BigDecimal("4.76"), promedio);
        verify(resenaRepository).calcularPromedioPorAgricultor(AGRICULTOR_ID);
    }

    @Test
    void dadoAgricultorSinResenas_cuandoCalculaPromedio_entoncesRetornaCero() {
        when(resenaRepository.calcularPromedioPorAgricultor(AGRICULTOR_ID))
                .thenReturn(null);

        BigDecimal promedio =
                reputacionService.calcularPromedioEstrellas(AGRICULTOR_ID);

        assertEquals(new BigDecimal("0.00"), promedio);
    }

    @Test
    void dadoAgricultorExistente_cuandoConsultaReputacion_entoncesDevuelvePromedio() {
        when(agricultorRepository.existsById(AGRICULTOR_ID)).thenReturn(true);
        when(resenaRepository.calcularPromedioPorAgricultor(AGRICULTOR_ID))
                .thenReturn(4.8D);

        ReputacionResponseDTO reputacion =
                reputacionService.obtenerReputacion(AGRICULTOR_ID);

        assertEquals(AGRICULTOR_ID, reputacion.getAgricultorId());
        assertEquals(new BigDecimal("4.80"), reputacion.getCalificacionPromedio());
    }

    @Test
    void dadoAgricultorInexistente_cuandoConsultaReputacion_entoncesLanzaError() {
        when(agricultorRepository.existsById(AGRICULTOR_ID)).thenReturn(false);

        assertThrows(
                AgricultorNoEncontradoException.class,
                () -> reputacionService.obtenerReputacion(AGRICULTOR_ID));
    }
}
