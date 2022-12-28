package com.samuelito.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.samuelito.app.domain.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

	@Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
	List<Producto> buscarPorNombre(String nombre);

	List<Producto> findByNombreContains(String nombre);
	
	List<Producto> findByNombreContainsIgnoreCase(String nombre);

	//Necesita que el parametro sea envuelto con los '%'
	List<Producto> findByNombreLike(String nombre);
}
