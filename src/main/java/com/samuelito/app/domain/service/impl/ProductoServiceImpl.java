package com.samuelito.app.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuelito.app.domain.Producto;
import com.samuelito.app.domain.service.IProductoService;
import com.samuelito.app.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	@Override
	public List<Producto> findByNombre(String term) {
		return productoRepository.findByNombreContainsIgnoreCase(term);
	}

	/**
	 * Find product by its ID
	 * 
	 * @param idProducto Id producto
	 * @return {@link Producto} the product
	 */
	@Override
	public Producto findProductoById(Long idProducto) {
		return productoRepository.findById(idProducto).orElse(null);
	}

}
