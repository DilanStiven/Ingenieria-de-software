package com.agrovalle.agrovalle_connect.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.agrovalle.agrovalle_connect.exceptions.GlobalExceptionHandler;
import com.agrovalle.agrovalle_connect.services.CategoriaService;
import com.agrovalle.agrovalle_connect.services.ProductoService;

/**
 * Pruebas de los endpoints públicos de catálogos.
 */
@WebMvcTest({CategoriaController.class, MunicipioController.class})
@Import(GlobalExceptionHandler.class)
class CatalogoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @MockitoBean
    private ProductoService productoService;

    /**
     * Verifica que el endpoint devuelva las categorías disponibles.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Lista las categorías disponibles")
    void listaCategorias() throws Exception {
        when(categoriaService.listar()).thenReturn(List.of("Frutas", "Granos"));

        mockMvc.perform(get("/api/v1/categorias").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Frutas"))
                .andExpect(jsonPath("$[1]").value("Granos"));
    }

    /**
     * Verifica que el endpoint devuelva los municipios permitidos.
     *
     * @throws Exception si falla la petición HTTP simulada
     */
    @Test
    @DisplayName("Lista los municipios disponibles")
    void listaMunicipios() throws Exception {
        when(productoService.listarMunicipios()).thenReturn(List.of("Buga", "Dagua", "Palmira"));

        mockMvc.perform(get("/api/v1/municipios").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Buga"))
                .andExpect(jsonPath("$[1]").value("Dagua"))
                .andExpect(jsonPath("$[2]").value("Palmira"));
    }
}
