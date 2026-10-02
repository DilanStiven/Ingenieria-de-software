package com.agrovalle.agrovalle_connect.controllers;

import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    void listarProductosReturnsProductsWithOkStatus() throws Exception {
        Producto producto = createProducto();
        when(productoService.obtenerTodosLosProductos()).thenReturn(Collections.singletonList(producto));

        mockMvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Maiz"));

        verify(productoService).obtenerTodosLosProductos();
    }

    @Test
    void crearProductoSavesRequestAndReturnsCreatedStatus() throws Exception {
        Producto productoGuardado = createProducto();
        when(productoService.guardarProducto(org.mockito.ArgumentMatchers.any(Producto.class)))
                .thenReturn(productoGuardado);

        mockMvc.perform(post("/api/productos")
                        .contentType("application/json")
                        .content("""
                                {"nombre":"Maiz","categoria":"Grano","municipio":"Valle","cantidad":5.0,"fechaCosecha":"2026-01-10","precioUnitario":12.5}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Maiz"));

        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(productoService).guardarProducto(captor.capture());
        assertEquals("Maiz", captor.getValue().getNombre());
        assertEquals(LocalDate.of(2026, 1, 10), captor.getValue().getFechaCosecha());
    }

    private Producto createProducto() {
        Producto producto = new Producto();
        producto.setNombre("Maiz");
        producto.setCategoria("Grano");
        producto.setMunicipio("Valle");
        producto.setCantidad(5.0);
        producto.setFechaCosecha(LocalDate.of(2026, 1, 10));
        producto.setPrecioUnitario(12.5);
        return producto;
    }
}
