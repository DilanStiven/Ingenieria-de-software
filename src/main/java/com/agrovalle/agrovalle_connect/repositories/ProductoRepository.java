package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
