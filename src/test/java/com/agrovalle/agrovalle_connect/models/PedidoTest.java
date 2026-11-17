package com.agrovalle.agrovalle_connect.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PedidoTest {

    @Test
    void iniciaPendienteDePago() {
        Pedido pedido = new Pedido();

        assertEquals(Pedido.Estado.PENDIENTE_PAGO, pedido.getEstado());
    }
}
