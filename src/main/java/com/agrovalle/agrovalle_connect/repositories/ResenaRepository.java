package com.agrovalle.agrovalle_connect.repositories;

import com.agrovalle.agrovalle_connect.models.Resena;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResenaRepository extends JpaRepository<Resena, Long> {

    Optional<Resena> findByOrdenCompraId(Long ordenCompraId);

    @Query("SELECT AVG(r.estrellas) FROM Resena r "
            + "WHERE r.agricultor.id = :agricultorId")
    Double calcularPromedioPorAgricultor(@Param("agricultorId") Long agricultorId);
}
