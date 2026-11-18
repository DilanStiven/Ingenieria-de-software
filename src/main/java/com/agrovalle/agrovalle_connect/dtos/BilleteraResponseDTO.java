package com.agrovalle.agrovalle_connect.dtos;

import java.math.BigDecimal;

/**
 * Respuesta con el saldo disponible del agricultor.
 */
public class BilleteraResponseDTO {

    private final Long agricultorId;
    private final BigDecimal saldo;

    public BilleteraResponseDTO(final Long agricultorIdValue,
            final BigDecimal saldoValue) {
        this.agricultorId = agricultorIdValue;
        this.saldo = saldoValue;
    }

    public Long getAgricultorId() {
        return agricultorId;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }
}
