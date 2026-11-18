package com.agrovalle.agrovalle_connect.services;

import com.agrovalle.agrovalle_connect.dtos.BilleteraResponseDTO;
import com.agrovalle.agrovalle_connect.models.Billetera;
import com.agrovalle.agrovalle_connect.models.Pedido;
import com.agrovalle.agrovalle_connect.repositories.BilleteraRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Gestiona la acreditacion y consulta de saldos de agricultores.
 */
@Service
public class BilleteraService {

    private final BilleteraRepository billeteraRepository;

    public BilleteraService(final BilleteraRepository repository) {
        this.billeteraRepository = repository;
    }

    /**
     * Acredita el monto pagado al agricultor propietario del producto.
     *
     * @param pedido pedido aprobado
     * @param monto monto acreditado
     */
    @Transactional
    public void acreditar(final Pedido pedido, final BigDecimal monto) {
        Long agricultorId = pedido.getProducto().getAgricultor().getId();
        Billetera billetera = billeteraRepository
                .findByAgricultorId(agricultorId)
                .orElseGet(() -> crearBilletera(pedido));
        BigDecimal nuevoSaldo = billetera.getSaldo().add(monto);
        billetera.setSaldo(nuevoSaldo);
        billeteraRepository.save(billetera);
    }

    /**
     * Consulta el saldo disponible del agricultor autenticado.
     *
     * @param agricultorId identificador del agricultor
     * @return saldo disponible
     */
    @Transactional(readOnly = true)
    public BilleteraResponseDTO consultarSaldo(final Long agricultorId) {
        BigDecimal saldo = billeteraRepository.findByAgricultorId(agricultorId)
                .map(Billetera::getSaldo)
                .orElse(BigDecimal.ZERO);
        return new BilleteraResponseDTO(agricultorId, saldo);
    }

    private Billetera crearBilletera(final Pedido pedido) {
        Billetera billetera = new Billetera();
        billetera.setAgricultor(pedido.getProducto().getAgricultor());
        return billetera;
    }
}
