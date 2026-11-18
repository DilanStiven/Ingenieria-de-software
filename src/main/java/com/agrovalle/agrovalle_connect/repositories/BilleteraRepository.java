package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Billetera;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de billeteras de agricultores.
 */
@Repository
public interface BilleteraRepository extends JpaRepository<Billetera, Long> {

    /**
     * Busca la billetera asociada al agricultor.
     *
     * @param agricultorId identificador del agricultor
     * @return billetera encontrada
     */
    Optional<Billetera> findByAgricultorId(Long agricultorId);
}
