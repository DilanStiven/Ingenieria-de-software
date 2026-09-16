package com.agrovalle.agrovalle_connect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agrovalle.agrovalle_connect.models.Agricultor;

public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {
}