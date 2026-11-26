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
import com.agrovalle.agrovalle_connect.repositories.PagoRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Procesa pagos y genera órdenes oficiales cuando la pasarela aprueba.
 */
@Service
public class PagoService {

    private static final int ESCALA_MONETARIA = 2;
    private static final Duration VENTANA_REINTENTO = Duration.ofMinutes(15);
    private static final String MENSAJE_PAGO_RECHAZADO =
            "Pago Rechazado. Puedes reintentar hasta la fecha límite indicada.";

    private final PedidoRepository pedidoRepository;
    private final PagoRepository pagoRepository;
    private final PasarelaPago pasarelaPago;
    private final BilleteraService billeteraService;
    private final PrecioService precioService;
    private final ProductoRepository productoRepository;
    private final OrdenCompraService ordenCompraService;

    public PagoService(final PedidoRepository pedidos,
            final PagoRepository pagos,
            final PasarelaPago pasarela,
            final BilleteraService billeteras,
            final PrecioService precios,
            final ProductoRepository productos,
            final OrdenCompraService ordenesCompra) {
        this.pedidoRepository = pedidos;
        this.pagoRepository = pagos;
        this.pasarelaPago = pasarela;
        this.billeteraService = billeteras;
        this.precioService = precios;
        this.productoRepository = productos;
        this.ordenCompraService = ordenesCompra;
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
        Pedido pedido = obtenerPedidoAutorizado(pedidoId, compradorId);
        Instant ahora = Instant.now();
        validarEstadoYVigencia(pedido, pedidoId, ahora);
        validarDestinoEntrega(pedido, pedidoId);
        Producto producto = obtenerProductoDisponible(pedido);
        pedido.setProducto(producto);
        BigDecimal monto = producto.getPrecioUnitario()
                .multiply(pedido.getCantidad())
                .setScale(ESCALA_MONETARIA, RoundingMode.HALF_UP);
        ResultadoPago resultado = pasarelaPago.procesarPago(monto, medioPago);
        Pago pagoGuardado = guardarPago(pedido, monto, medioPago, resultado);
        if (resultado.estado() != Pago.Estado.APROBADO) {
            return registrarRechazo(pedido, pagoGuardado, ahora);
        }

        return completarPagoAprobado(pedido, producto, monto, pagoGuardado, resultado);
    }

    private Pedido obtenerPedidoAutorizado(final Long pedidoId, final Long compradorId) {
        Pedido pedido = pedidoRepository.buscarPorIdConBloqueo(pedidoId)
                .orElseThrow(() -> new PedidoNoEncontradoException(pedidoId));
        if (!Objects.equals(pedido.getCompradorId(), compradorId)) {
            throw new PedidoNoEncontradoException(pedidoId);
        }
        return pedido;
    }

    private void validarDestinoEntrega(final Pedido pedido, final Long pedidoId) {
        if (pedido.getDestinoEntrega() == null || pedido.getDestinoEntrega().isBlank()) {
            throw new PagoInvalidoException(
                    pedidoId, pedido.getEstado().name(),
                    "el pedido no tiene un destino de entrega");
        }
    }

    private Pago guardarPago(final Pedido pedido, final BigDecimal monto,
            final Pago.MedioPago medioPago, final ResultadoPago resultado) {
        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(monto);
        pago.setMedioPago(medioPago);
        pago.setEstado(resultado.estado());
        pago.setReferenciaPasarela(resultado.referencia());
        return pagoRepository.save(pago);
    }

    private PagoResponseDTO completarPagoAprobado(final Pedido pedido,
            final Producto producto, final BigDecimal monto, final Pago pago,
            final ResultadoPago resultado) {
        marcarPedidoPagado(pedido);
        descontarInventario(producto, pedido.getCantidad());
        precioService.registrarTransaccion(producto.getNombre(), producto.getPrecioUnitario());
        OrdenCompra orden = ordenCompraService.crearDesdePedidoPagado(pedido, pago, monto);
        billeteraService.acreditar(pedido, monto);
        return new PagoResponseDTO(
                pago, orden.getNumeroOrden(), orden.getId(), resultado.mensaje(), null);
    }

    private void marcarPedidoPagado(final Pedido pedido) {
        pedido.setEstado(Pedido.Estado.PAGADO);
        pedido.setFechaLimiteReintento(null);
        pedidoRepository.save(pedido);
    }

    private void descontarInventario(final Producto producto, final BigDecimal cantidad) {
        BigDecimal cantidadRestante = producto.getCantidad().subtract(cantidad);
        producto.setCantidad(cantidadRestante);
        if (cantidadRestante.signum() == 0) {
            producto.setEstado("INACTIVO");
        }
        productoRepository.save(producto);
    }

    private void validarEstadoYVigencia(
            final Pedido pedido, final Long pedidoId, final Instant ahora) {
        Instant fechaLimiteReintento = pedido.getFechaLimiteReintento();
        if (fechaLimiteReintento != null && !ahora.isBefore(fechaLimiteReintento)) {
            throw new PagoInvalidoException(
                    pedidoId, pedido.getEstado().name(),
                    "venció la ventana de 15 minutos para reintentar");
        }
        boolean pendienteDePago = pedido.getEstado() == Pedido.Estado.PENDIENTE_PAGO;
        boolean reintentoPermitido = pedido.getEstado() == Pedido.Estado.RECHAZADO
                && fechaLimiteReintento != null;
        if (!pendienteDePago && !reintentoPermitido) {
            throw new PagoInvalidoException(pedidoId, pedido.getEstado().name());
        }
    }

    private Producto obtenerProductoDisponible(final Pedido pedido) {
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
        return producto;
    }

    private PagoResponseDTO registrarRechazo(
            final Pedido pedido, final Pago pago, final Instant ahora) {
        Instant fechaLimiteReintento = pedido.getFechaLimiteReintento();
        if (fechaLimiteReintento == null) {
            fechaLimiteReintento = ahora.plus(VENTANA_REINTENTO);
            pedido.setFechaLimiteReintento(fechaLimiteReintento);
        }
        pedido.setEstado(Pedido.Estado.RECHAZADO);
        pedidoRepository.save(pedido);
        return new PagoResponseDTO(
                pago, null, null, MENSAJE_PAGO_RECHAZADO, fechaLimiteReintento);
    }
}
