package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.ProductoRequestDTO;
import com.agrovalle.agrovalle_connect.dtos.ProductoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.FechaCosechaInvalidaException;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductoService — Pruebas Unitarias HU-02: Publicación de Productos")
class ProductoServicePublicarTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private AgricultorRepository agricultorRepository;

    @InjectMocks
    private ProductoService productoService;

    private static final Long AGRICULTOR_ID = 1L;
    private static final BigDecimal CANTIDAD = new BigDecimal("50.00");
    private static final BigDecimal PRECIO = new BigDecimal("2500.00");

    private Agricultor agricultorMock;
    private ProductoRequestDTO dtoValido;

    @BeforeEach
    void setUp() {
        agricultorMock = new Agricultor();
        agricultorMock.setId(AGRICULTOR_ID);
        agricultorMock.setNombre("Juan Perez");
        agricultorMock.setUbicacionValle("Dagua");
        agricultorMock.setCedula("1234567890");

        dtoValido = new ProductoRequestDTO();
        dtoValido.setTipo("Mango");
        dtoValido.setCategoria("Frutas");
        dtoValido.setCantidad(CANTIDAD);
        dtoValido.setPrecioUnitario(PRECIO);
        dtoValido.setFechaCosecha(LocalDate.now().plusDays(1));
    }

    /**
     * BDD Escenario 1 — Publicacion exitosa.
     * Given un agricultor autenticado y fecha futura,
     * When publica un producto valido,
     * Then el sistema persiste y retorna el ID del producto.
     */
    @Test
    @DisplayName("DADO agricultor valido y fecha futura, CUANDO publica, ENTONCES retorna ID")
    void dadoFechaFutura_cuandoPublica_entoncesRetornaId() {
        Producto productoGuardado = new Producto();
        productoGuardado.setId(AGRICULTOR_ID);

        when(agricultorRepository.findById(AGRICULTOR_ID))
            .thenReturn(Optional.of(agricultorMock));
        when(productoRepository.save(any(Producto.class)))
            .thenReturn(productoGuardado);

        ProductoResponseDTO response = productoService.publicar(dtoValido, AGRICULTOR_ID);

        assertNotNull(response);
        assertEquals(AGRICULTOR_ID, response.getId());
        assertEquals("Producto publicado exitosamente", response.getMensaje());
        verify(productoRepository).save(any(Producto.class));
    }

    /**
     * BDD Escenario 2 — Fecha anterior a hoy.
     * Given un agricultor autenticado,
     * When envia fecha_cosecha anterior a hoy,
     * Then el sistema lanza FechaCosechaInvalidaException y no guarda nada.
     */
    @Test
    @DisplayName("DADO fecha pasada, CUANDO publica, ENTONCES lanza FechaCosechaInvalidaException")
    void dadoFechaPasada_cuandoPublica_entoncesLanzaExcepcion() {
        dtoValido.setFechaCosecha(LocalDate.now().minusDays(1));

        assertThrows(FechaCosechaInvalidaException.class,
            () -> productoService.publicar(dtoValido, AGRICULTOR_ID));

        verify(productoRepository, never()).save(any());
    }
}
