package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.agrovalle.agrovalle_connect.dtos.ReputacionResponseDTO;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.OrdenCompra;
import com.agrovalle.agrovalle_connect.models.Pago;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.models.Resena;
import com.agrovalle.agrovalle_connect.repositories.AgricultorRepository;
import com.agrovalle.agrovalle_connect.repositories.OrdenCompraRepository;
import com.agrovalle.agrovalle_connect.repositories.PagoRepository;
import com.agrovalle.agrovalle_connect.repositories.PedidoRepository;
import com.agrovalle.agrovalle_connect.repositories.ProductoRepository;
import com.agrovalle.agrovalle_connect.repositories.ResenaRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * Pruebas de integración del promedio calculado desde reseñas persistidas.
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReputacionServiceIntegrationTest {

    private static final int CUATRO_ESTRELLAS = 4;
    private static final int CINCO_ESTRELLAS = 5;
    private static final int[] ESTRELLAS_RESENAS =
            {CINCO_ESTRELLAS, CINCO_ESTRELLAS, CINCO_ESTRELLAS, CINCO_ESTRELLAS,
                    CINCO_ESTRELLAS, CINCO_ESTRELLAS, CINCO_ESTRELLAS, CINCO_ESTRELLAS,
                    CUATRO_ESTRELLAS, CUATRO_ESTRELLAS};
    private static final Long COMPRADOR_ID_BASE = 100L;
    private static final int CANTIDAD_RESENAS = 10;

    @Autowired
    private AgricultorRepository agricultorRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private OrdenCompraRepository ordenCompraRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private ReputacionService reputacionService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void dadoAgricultorConDiezVentasCalificadas_cuandoConsultaReputacion_entoncesCalculaPromedio() {
        Agricultor agricultor = crearAgricultor();
        Producto producto = crearProducto(agricultor);
        guardarDiezResenasCalificadas(agricultor, producto);

        ReputacionResponseDTO reputacion =
                reputacionService.obtenerReputacion(agricultor.getId());

        assertEquals(agricultor.getId(), reputacion.getAgricultorId());
        assertEquals(new BigDecimal("4.80"), reputacion.getCalificacionPromedio());
        Integer resenasGuardadas = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM resena WHERE id_agricultor = ?",
                Integer.class,
                agricultor.getId());
        assertEquals(CANTIDAD_RESENAS, resenasGuardadas);
    }

    private Agricultor crearAgricultor() {
        Agricultor agricultor = new Agricultor();
        agricultor.setNombre("Agricultor de prueba");
        agricultor.setUbicacionValle("Dagua");
        agricultor.setCedula("REP-TEST-001");
        return agricultorRepository.saveAndFlush(agricultor);
    }

    private Producto crearProducto(final Agricultor agricultor) {
        Producto producto = new Producto();
        producto.setNombre("Tomate de prueba");
        producto.setCategoria("Verduras");
        producto.setMunicipio("Dagua");
        producto.setCantidad(new BigDecimal("100.00"));
        producto.setPrecioUnitario(new BigDecimal("10.00"));
        producto.setFechaCosecha(LocalDate.now());
        producto.setEstado("ACTIVO");
        producto.setAgricultor(agricultor);
        return productoRepository.saveAndFlush(producto);
    }

    private void guardarDiezResenasCalificadas(
            final Agricultor agricultor, final Producto producto) {
        for (int indice = 0; indice < ESTRELLAS_RESENAS.length; indice++) {
            Long compradorId = COMPRADOR_ID_BASE + indice;
            Pedido pedido = crearPedido(producto, compradorId);
            Pago pago = crearPago(pedido);
            OrdenCompra orden = crearOrdenCompra(pedido, pago, indice);

            Resena resena = new Resena();
            resena.setOrdenCompra(orden);
            resena.setCompradorId(compradorId);
            resena.setAgricultor(agricultor);
            resena.setEstrellas(ESTRELLAS_RESENAS[indice]);
            resenaRepository.saveAndFlush(resena);
        }
    }

    private Pedido crearPedido(final Producto producto, final Long compradorId) {
        Pedido pedido = new Pedido();
        pedido.setProducto(producto);
        pedido.setCantidad(new BigDecimal("1.00"));
        pedido.setCompradorId(compradorId);
        pedido.setEstado(Pedido.Estado.PAGADO);
        return pedidoRepository.saveAndFlush(pedido);
    }

    private Pago crearPago(final Pedido pedido) {
        Pago pago = new Pago();
        pago.setPedido(pedido);
        pago.setMonto(new BigDecimal("10.00"));
        pago.setMedioPago(Pago.MedioPago.TARJETA);
        pago.setEstado(Pago.Estado.APROBADO);
        return pagoRepository.saveAndFlush(pago);
    }

    private OrdenCompra crearOrdenCompra(
            final Pedido pedido, final Pago pago, final int indice) {
        OrdenCompra orden = new OrdenCompra();
        orden.setPedido(pedido);
        orden.setPago(pago);
        orden.setNumeroOrden("OC-REPUTACION-" + indice);
        orden.setMontoTotal(new BigDecimal("10.00"));
        return ordenCompraRepository.saveAndFlush(orden);
    }
}
