package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para persistir transacciones de pago.
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
}
