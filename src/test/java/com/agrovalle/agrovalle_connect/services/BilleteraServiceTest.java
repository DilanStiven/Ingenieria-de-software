package com.agrovalle.agrovalle_connect.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agrovalle.agrovalle_connect.dtos.BilleteraResponseDTO;
import com.agrovalle.agrovalle_connect.models.Agricultor;
import com.agrovalle.agrovalle_connect.models.Billetera;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.models.Producto;
import com.agrovalle.agrovalle_connect.repositories.BilleteraRepository;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BilleteraServiceTest {

    private static final Long AGRICULTOR_ID = 5L;

    @Mock
    private BilleteraRepository billeteraRepository;

    private BilleteraService billeteraService;
    private Pedido pedido;

    @BeforeEach
    void configurar() {
        MockitoAnnotations.openMocks(this);
        billeteraService = new BilleteraService(billeteraRepository);

        Agricultor agricultor = new Agricultor();
        agricultor.setId(AGRICULTOR_ID);
        Producto producto = new Producto();
        producto.setAgricultor(agricultor);
        pedido = new Pedido();
        pedido.setProducto(producto);
    }

    @Test
    void dadoPagoAprobado_cuandoSeAcredita_entoncesSumaAlSaldo() {
        Billetera billetera = new Billetera();
        billetera.setAgricultor(pedido.getProducto().getAgricultor());
        billetera.setSaldo(new BigDecimal("40000.00"));
        when(billeteraRepository.findByAgricultorId(AGRICULTOR_ID))
                .thenReturn(Optional.of(billetera));
        when(billeteraRepository.save(any(Billetera.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        billeteraService.acreditar(pedido, new BigDecimal("25000.00"));

        assertEquals(new BigDecimal("65000.00"), billetera.getSaldo());
        verify(billeteraRepository).save(billetera);
    }

    @Test
    void dadoAgricultorSinVentas_cuandoConsulta_entoncesSaldoEsCero() {
        when(billeteraRepository.findByAgricultorId(AGRICULTOR_ID))
                .thenReturn(Optional.empty());

        BilleteraResponseDTO respuesta =
                billeteraService.consultarSaldo(AGRICULTOR_ID);

        assertEquals(AGRICULTOR_ID, respuesta.getAgricultorId());
        assertEquals(BigDecimal.ZERO, respuesta.getSaldo());
    }

    @Test
    void dadoPagoAcreditado_cuandoConsulta_entoncesDevuelveSaldoAcumulado() {
        Billetera billetera = new Billetera();
        billetera.setSaldo(new BigDecimal("65000.00"));
        when(billeteraRepository.findByAgricultorId(AGRICULTOR_ID))
                .thenReturn(Optional.of(billetera));

        BilleteraResponseDTO respuesta =
                billeteraService.consultarSaldo(AGRICULTOR_ID);

        assertEquals(new BigDecimal("65000.00"), respuesta.getSaldo());
    }
}
