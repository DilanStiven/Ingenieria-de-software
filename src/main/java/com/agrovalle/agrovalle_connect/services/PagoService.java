package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.PagoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.PagoInvalidoException;
import com.agrovalle.agrovalle_connect.exceptions.PedidoNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import com.agrovalle.agrovalle_connect.repositories.PagoRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import java.math.BigDecimal;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Procesa pagos y genera órdenes oficiales cuando la pasarela aprueba.
 */
@Service
public class PagoService {

    private final PedidoRepository pedidoRepository;
    private final PagoRepository pagoRepository;
    private final OrdenCompraRepository ordenCompraRepository;
    private final PasarelaPago pasarelaPago;

    public PagoService(final PedidoRepository pedidos,
            final PagoRepository pagos,
            final OrdenCompraRepository ordenes,
            final PasarelaPago pasarela) {
        this.pedidoRepository = pedidos;
        this.pagoRepository = pagos;
        this.ordenCompraRepository = ordenes;
        this.pasarelaPago = pasarela;
    }

    /**
     * Procesa el pago de un pedido pendiente.
     *
     * @param pedidoId identificador del pedido
     * @param medioPago medio seleccionado
     * @return resultado persistido
     */
    @Transactional
    public PagoResponseDTO procesar(final Long pedidoId, final Pago.MedioPago medioPago) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
        if (pedido.getEstado() != Pedido.Estado.PENDIENTE_PAGO) {
            throw new PagoInvalidoException(pedidoId, pedido.getEstado().name());
        }

        BigDecimal monto = pedido.getProducto().getPrecioUnitario();
        ResultadoPago resultado = pasarelaPago.procesarPago(monto, medioPago);

        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(monto);
        pago.setMedioPago(medioPago);
        pago.setEstado(resultado.estado());
        pago.setReferenciaPasarela(resultado.referencia());
        Pago pagoGuardado = pagoRepository.save(pago);

        if (resultado.estado() == Pago.Estado.APROBADO) {
            pedido.setEstado(Pedido.Estado.PAGADO);
            pedidoRepository.save(pedido);

            OrdenCompra orden = new OrdenCompra();
            orden.setPedido(pedido);
            orden.setPago(pagoGuardado);
            orden.setNumeroOrden("OC-" + UUID.randomUUID());
            orden.setMontoTotal(monto);
            OrdenCompra ordenGuardada = ordenCompraRepository.save(orden);

            return new PagoResponseDTO(
                    pagoGuardado,
                    ordenGuardada.getNumeroOrden(),
                    ordenGuardada.getId(),
                    resultado.mensaje());
        }

        pedido.setEstado(Pedido.Estado.RECHAZADO);
        pedidoRepository.save(pedido);
        return new PagoResponseDTO(
                pagoGuardado,
                null,
                null,
                resultado.mensaje());
    }
}
