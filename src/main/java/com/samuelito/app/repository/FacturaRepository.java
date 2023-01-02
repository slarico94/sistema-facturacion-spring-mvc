package com.samuelito.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.samuelito.app.domain.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

	@Query("SELECT f FROM Factura f JOIN FETCH f.cliente c JOIN FETCH f.items i JOIN FETCH i.producto WHERE f.idFactura = :idFactura")
	Optional<Factura> fetchByIdFacturaWithClienteWithItemFacturaWithProducto(Long idFactura);
}
