package com.agrovalle.agrovalle_connect.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.agrovalle.agrovalle_connect.models.Categoria;
import com.agrovalle.agrovalle_connect.repositories.CategoriaRepository;


@Service
public class CategoriaService implements ApplicationRunner {

    private static final List<String> CATEGORIAS_INICIALES = List.of("Frutas", "Granos");

    private final CategoriaRepository categoriaRepository;

    /**
     * Crea el servicio con su repositorio.
     *
     * @param categoriaRepository repositorio de categorías
     */
    public CategoriaService(final CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Obtiene las categorías disponibles para filtrar productos.
     *
     * @return lista de nombres de categorías
     */
    public List<String> listar() {
        return categoriaRepository.findAll(Sort.by("nombre")).stream()
                .map(Categoria::getNombre)
                .toList();
    }

    /**
     * Carga las categorías iniciales que todavía no existen en la base de datos.
     *
     * @param args argumentos de inicio de la aplicación
     */
    @Override
    public void run(final ApplicationArguments args) {
        Set<String> categoriasExistentes = categoriaRepository.findAll().stream()
                .map(Categoria::getNombre)
                .collect(Collectors.toSet());
        List<Categoria> categoriasFaltantes = CATEGORIAS_INICIALES.stream()
                .filter(nombre -> !categoriasExistentes.contains(nombre))
                .map(Categoria::new)
                .toList();
        if (!categoriasFaltantes.isEmpty()) {
            categoriaRepository.saveAll(categoriasFaltantes);
        }
    }
}
