package com.samuelito.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuelito.app.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
