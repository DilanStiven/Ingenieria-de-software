package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void obtenerTodosLosProductosReturnsRepositoryResults() {
        List<Producto> productos = Arrays.asList(new Producto(), new Producto());
        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.obtenerTodosLosProductos();

        assertSame(productos, resultado);
        assertEquals(2, resultado.size());
        verify(productoRepository).findAll();
    }

    @Test
    void guardarProductoReturnsSavedEntity() {
        Producto producto = new Producto();
        Producto productoGuardado = new Producto();
        when(productoRepository.save(producto)).thenReturn(productoGuardado);

        Producto resultado = productoService.guardarProducto(producto);

        assertSame(productoGuardado, resultado);
        verify(productoRepository).save(producto);
    }
}
