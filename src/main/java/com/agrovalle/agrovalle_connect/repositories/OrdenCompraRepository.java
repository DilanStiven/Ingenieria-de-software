package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para órdenes de compra oficiales.
 */
@Repository
public interface OrdenCompraRepository extends JpaRepository<OrdenCompra, Long> {

    /**
     * Busca órdenes del historial del usuario con sus datos de pedido y pago.
     *
     * @param compradorId identificador del comprador autenticado
     * @return órdenes ordenadas desde la más reciente
     */
    @Query("""
            select orden from OrdenCompra orden
            join fetch orden.pedido pedido
            join fetch pedido.producto producto
            join fetch orden.pago pago
            where pedido.compradorId = :compradorId
            order by orden.fechaCreacion desc
            """)
    List<OrdenCompra> buscarHistorialPorComprador(
            @Param("compradorId") Long compradorId);

    /**
     * Busca una orden asociada al pedido y al usuario autenticado.
     *
     * @param pedidoId identificador del pedido
     * @param compradorId identificador del comprador autenticado
     * @return orden perteneciente al usuario, si existe
     */
    @Query("""
            select orden from OrdenCompra orden
            join fetch orden.pedido pedido
            join fetch pedido.producto producto
            join fetch orden.pago pago
            where pedido.id = :pedidoId and pedido.compradorId = :compradorId
            """)
    Optional<OrdenCompra> buscarPorPedidoYComprador(
            @Param("pedidoId") Long pedidoId,
            @Param("compradorId") Long compradorId);
}
