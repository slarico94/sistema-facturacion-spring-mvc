package com.samuelito.app.domain.service;

import java.util.List;

import com.samuelito.app.domain.Producto;

public interface IProductoService {

	List<Producto> findByNombre(String term);

	Producto findProductoById(Long idProducto);
}
