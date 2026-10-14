package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.exceptions.MunicipioNoValidoException;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.services.ProductoService;

/**
 * Pruebas del endpoint de consulta pública de productos.
 */
@WebMvcTest(ProductoController.class)
@Import(GlobalExceptionHandler.class)
class ProductoControllerTest {

    private static final String URL = "/api/v1/productos";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    /**
     * Verifica la respuesta exitosa y el paso de los filtros opcionales al servicio.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Escenario 1: con resultados responde 200 con los productos")
    void conResultados() throws Exception {
        Producto mango = crearProducto(1L, "Mango", "Dagua", "Frutas", "50", "2500");
        Producto papaya = crearProducto(2L, "Papaya", "Dagua", "Frutas", "20", "1800");
        when(productoService.filtrar("Dagua", "Frutas", null)).thenReturn(List.of(mango, papaya));

        mockMvc.perform(get(URL)
                        .param("municipio", "Dagua")
                        .param("categoria", "Frutas")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].municipio").value("Dagua"))
                .andExpect(jsonPath("$[0].categoria").value("Frutas"))
                .andExpect(jsonPath("$[1].nombre").value("Papaya"));

        verify(productoService).filtrar("Dagua", "Frutas", null);
    }

    /**
     * Verifica que una búsqueda sin coincidencias responda con un arreglo vacío.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Escenario 2: sin resultados responde 200 con arreglo vacío")
    void sinResultados() throws Exception {
        when(productoService.filtrar("Buga", "Granos", null)).thenReturn(List.of());

        mockMvc.perform(get(URL)
                        .param("municipio", "Buga")
                        .param("categoria", "Granos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());

        verify(productoService).filtrar("Buga", "Granos", null);
    }

    /**
     * Verifica que el municipio fuera del catálogo se traduzca a HTTP 400.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Escenario 3: municipio no válido responde 400")
    void municipioNoValido() throws Exception {
        when(productoService.filtrar("Otro", null, null))
                .thenThrow(new MunicipioNoValidoException("Otro"));

        mockMvc.perform(get(URL).param("municipio", "Otro"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Municipio no válido: Otro"));

        verify(productoService).filtrar("Otro", null, null);
    }

    private Producto crearProducto(
            final Long id,
            final String nombre,
            final String municipio,
            final String categoria,
            final String cantidad,
            final String precio) {
        Producto producto = new Producto();
        producto.setId(id);
        producto.setNombre(nombre);
        producto.setMunicipio(municipio);
        producto.setCategoria(categoria);
        producto.setCantidad(new BigDecimal(cantidad));
        producto.setPrecioUnitario(new BigDecimal(precio));
        return producto;
    }
}
