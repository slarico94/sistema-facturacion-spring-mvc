package com.samuelito.app.domain.service;

import com.samuelito.app.domain.Factura;

public interface IFacturaService {

	void saveFactura(Factura factura);
	
	Factura findFacturaById(Long idFactura);
	
	void deleteFacturaById(Long idFactura);
}
