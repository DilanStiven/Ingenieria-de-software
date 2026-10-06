package com.agrovalle.agrovalle_connect.services;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.agrovalle.agrovalle_connect.exceptions.MunicipioNoValidoException;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;

/**
 * Servicio para buscar productos mediante filtros.
 */
@Service
public class ProductoService {

    private static final Set<String> MUNICIPIOS_VALIDOS = Set.of("Dagua", "Palmira", "Buga");

    private final ProductoRepository productoRepository;

    /**
     * Crea el servicio con su repositorio.
     *
     * @param productoRepository repositorio de productos
     */
    public ProductoService(final ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    /**
     * Busca por municipio, categoría y estado. Los filtros nulos o vacíos se ignoran.
     *
     * @param municipio municipio exacto, opcional
     * @param categoria categoría exacta, opcional
     * @param estado estado exacto, opcional
     * @return productos coincidentes
     */
    public List<Producto> filtrar(final String municipio, final String categoria, final String estado) {
        String municipioNormalizado = normalizarFiltro(municipio);
        if (municipioNormalizado != null && !MUNICIPIOS_VALIDOS.contains(municipioNormalizado)) {
            throw new MunicipioNoValidoException(municipioNormalizado);
        }
        return productoRepository.buscarPorFiltros(
                municipioNormalizado,
                normalizarFiltro(categoria),
                normalizarFiltro(estado));
    }

    private String normalizarFiltro(final String filtro) {
        if (filtro == null || filtro.isBlank()) {
            return null;
        }
        return filtro.trim();
    }
}
