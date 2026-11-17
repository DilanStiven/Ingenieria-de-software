package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar pedidos.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}
