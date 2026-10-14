package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agrovalle.agrovalle_connect.exceptions.MunicipioNoValidoException;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;

/**
 * Pruebas unitarias del filtrado de productos.
 */
@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    /**
     * Verifica la combinación de filtros informados y la normalización de espacios.
     */
    @Test
    @DisplayName("Debe filtrar por municipio, categoría y estado")
    void debeFiltrarPorMunicipioCategoriaYEstado() {
        Producto producto = new Producto();
        List<Producto> esperados = List.of(producto);
        when(productoRepository.buscarPorFiltros("Palmira", "Tomate", "activo")).thenReturn(esperados);

        List<Producto> resultado = productoService.filtrar(" Palmira ", "Tomate", "activo");

        assertSame(esperados, resultado);
        verify(productoRepository).buscarPorFiltros("Palmira", "Tomate", "activo");
    }

    /**
     * Verifica que los filtros vacíos no restrinjan la búsqueda.
     */
    @Test
    @DisplayName("Debe ignorar filtros vacíos")
    void debeIgnorarFiltrosVacios() {
        List<Producto> esperados = List.of();
        when(productoRepository.buscarPorFiltros(null, null, "activo")).thenReturn(esperados);

        List<Producto> resultado = productoService.filtrar(" ", null, "activo");

        assertSame(esperados, resultado);
        verify(productoRepository).buscarPorFiltros(null, null, "activo");
    }

    /**
     * Verifica que municipios fuera del catálogo sean rechazados antes de consultar el repositorio.
     */
    @Test
    @DisplayName("Debe rechazar municipios no válidos")
    void debeRechazarMunicipioNoValido() {
        MunicipioNoValidoException excepcion = assertThrows(
                MunicipioNoValidoException.class,
                () -> productoService.filtrar("Otro", null, null));

        assertEquals("Municipio no válido: Otro", excepcion.getMessage());
    }
}
