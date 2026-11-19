package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.PagoResponseDTO;
import com.agrovalle.agrovalle_connect.exceptions.CantidadNoDisponibleException;
import com.agrovalle.agrovalle_connect.exceptions.OfertaNoDisponibleException;
import com.agrovalle.agrovalle_connect.exceptions.PagoInvalidoException;
import com.agrovalle.agrovalle_connect.exceptions.PedidoNoEncontradoException;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import com.agrovalle.agrovalle_connect.repositories.PagoRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Procesa pagos y genera órdenes oficiales cuando la pasarela aprueba.
 */
@Service
public class PagoService {

    private static final int ESCALA_MONETARIA = 2;

    private final PedidoRepository pedidoRepository;
    private final PagoRepository pagoRepository;
    private final OrdenCompraRepository ordenCompraRepository;
    private final PasarelaPago pasarelaPago;
    private final BilleteraService billeteraService;
    private final PrecioService precioService;
    private final ProductoRepository productoRepository;

    public PagoService(final PedidoRepository pedidos,
            final PagoRepository pagos,
            final OrdenCompraRepository ordenes,
            final PasarelaPago pasarela,
            final BilleteraService billeteras,
            final PrecioService precios,
            final ProductoRepository productos) {
        this.pedidoRepository = pedidos;
        this.pagoRepository = pagos;
        this.ordenCompraRepository = ordenes;
        this.pasarelaPago = pasarela;
        this.billeteraService = billeteras;
        this.precioService = precios;
        this.productoRepository = productos;
    }

    /**
     * Procesa el pago de un pedido pendiente.
     *
     * @param pedidoId identificador del pedido
     * @param compradorId usuario autenticado que inicia el pago
     * @param medioPago medio seleccionado
     * @return resultado persistido
     */
    @Transactional
    public PagoResponseDTO procesar(
            final Long pedidoId, final Long compradorId, final Pago.MedioPago medioPago) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
        if (!Objects.equals(pedido.getCompradorId(), compradorId)) {
            throw new PedidoNoEncontradoException(pedidoId);
        }
        if (pedido.getEstado() != Pedido.Estado.PENDIENTE_PAGO) {
            throw new PagoInvalidoException(pedidoId, pedido.getEstado().name());
        }

        Long productoId = pedido.getProducto().getId();
        Producto producto = productoRepository.buscarPorIdConBloqueo(productoId)
                .orElseThrow(() -> new OfertaNoDisponibleException(productoId));
        if (!"ACTIVO".equals(producto.getEstado())) {
            throw new OfertaNoDisponibleException(productoId);
        }
        if (pedido.getCantidad().compareTo(producto.getCantidad()) > 0) {
            throw new CantidadNoDisponibleException(
                    productoId, pedido.getCantidad(), producto.getCantidad());
        }
        pedido.setProducto(producto);
        BigDecimal monto = producto.getPrecioUnitario()
                .multiply(pedido.getCantidad())
                .setScale(ESCALA_MONETARIA, RoundingMode.HALF_UP);
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

            BigDecimal cantidadRestante = producto.getCantidad().subtract(pedido.getCantidad());
            producto.setCantidad(cantidadRestante);
            if (cantidadRestante.signum() == 0) {
                producto.setEstado("INACTIVO");
            }
            productoRepository.save(producto);
            precioService.registrarTransaccion(
                    producto.getNombre(), producto.getPrecioUnitario());

            OrdenCompra orden = new OrdenCompra();
            orden.setPedido(pedido);
            orden.setPago(pagoGuardado);
            orden.setNumeroOrden("OC-" + UUID.randomUUID());
            orden.setMontoTotal(monto);
            OrdenCompra ordenGuardada = ordenCompraRepository.save(orden);
            billeteraService.acreditar(pedido, monto);

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
