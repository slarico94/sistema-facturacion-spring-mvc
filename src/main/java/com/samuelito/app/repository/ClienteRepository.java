package com.samuelito.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.samuelito.app.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	@Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.facturas f LEFT JOIN FETCH f.items i LEFT JOIN FETCH i.producto p WHERE c.idCliente = :idCliente")
	Optional<Cliente> findByIdClienteWithFacturaWithItemFacturaWithProducto(Long idCliente);
}
