package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Confirma una compra creando la orden y su registro logístico.
 */
@Service
public class OrdenCompraService {

    private final OrdenCompraRepository ordenCompraRepository;
    private final OrdenLogisticaService ordenLogisticaService;

    public OrdenCompraService(final OrdenCompraRepository ordenes,
            final OrdenLogisticaService ordenesLogisticas) {
        this.ordenCompraRepository = ordenes;
        this.ordenLogisticaService = ordenesLogisticas;
    }

    /**
     * Crea la orden oficial y su orden logística para un pago aprobado.
     *
     * @param pedido pedido pagado
     * @param pago pago aprobado
     * @param monto valor total aprobado
     * @return orden de compra persistida
     */
    @Transactional
    public OrdenCompra crearDesdePedidoPagado(final Pedido pedido, final Pago pago,
            final BigDecimal monto) {
        OrdenCompra orden = new OrdenCompra();
        orden.setPedido(pedido);
        orden.setPago(pago);
        orden.setNumeroOrden("OC-" + UUID.randomUUID());
        orden.setMontoTotal(monto);
        OrdenCompra guardada = ordenCompraRepository.save(orden);
        ordenLogisticaService.crearDesdePedidoPagado(pedido);
        return guardada;
    }
}
