package com.samuelito.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuelito.app.domain.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

}
