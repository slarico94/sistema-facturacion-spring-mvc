package com.samuelito.app.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuelito.app.domain.Factura;
import com.samuelito.app.domain.service.IFacturaService;
import com.samuelito.app.repository.FacturaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class FacturaServiceImpl implements IFacturaService {

	@Autowired
	private FacturaRepository facturaRepository;

	@Override
	public void saveFactura(Factura factura) {
		facturaRepository.save(factura);
	}

	@Override
	public Factura findFacturaById(Long idFactura) {
		return facturaRepository.findById(idFactura).orElse(null);
	}

	@Override
	public void deleteFacturaById(Long idFactura) {
		facturaRepository.deleteById(idFactura);
	}

}
