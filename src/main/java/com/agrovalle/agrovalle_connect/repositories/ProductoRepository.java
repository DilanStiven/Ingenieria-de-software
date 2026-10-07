package com.agrovalle.agrovalle_connect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agrovalle.agrovalle_connect.models.Producto;

/**
 * Repositorio para consultar productos.
 */
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Busca productos aplicando únicamente los filtros informados.
     *
     * @param municipio municipio exacto, o null para no filtrar
     * @param categoria categoría exacta, o null para no filtrar
     * @param estado estado exacto, o null para no filtrar
     * @return productos que coinciden con los filtros
     */
    @Query("""
            SELECT p FROM Producto p
            WHERE (:municipio IS NULL OR p.municipio = :municipio)
              AND (:categoria IS NULL OR p.categoria = :categoria)
              AND (:estado IS NULL OR p.estado = :estado)
            """)
    List<Producto> buscarPorFiltros(
            @Param("municipio") String municipio,
            @Param("categoria") String categoria,
            @Param("estado") String estado);
}
