package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para órdenes de compra oficiales.
 */
@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {
}
