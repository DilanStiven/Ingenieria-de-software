package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de acceso a datos para productos agrícolas.
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Busca productos activos por municipio.
     *
     * @param municipio municipio de origen
     * @param estado    estado del producto (ACTIVO)
     * @return lista de productos que coinciden
     */
    List<Producto> findByMunicipioAndEstado(String municipio, String estado);

    /**
     * Busca productos activos por municipio y categoría.
     *
     * @param municipio municipio de origen
     * @param categoria categoría del producto
     * @param estado    estado del producto (ACTIVO)
     * @return lista de productos que coinciden
     */
    List<Producto> findByMunicipioAndCategoriaAndEstado(
        String municipio, String categoria, String estado);

    /**
     * Busca productos aplicando únicamente los filtros informados (null = sin restricción).
     *
     * @param municipio municipio exacto, o null para no filtrar
     * @param categoria categoría exacta, o null para no filtrar
     * @param estado    estado exacto, o null para no filtrar
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
