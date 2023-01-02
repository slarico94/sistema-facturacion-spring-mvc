package com.samuelito.app.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.samuelito.app.domain.Cliente;

public interface IClienteService {

	List<Cliente> getAllClientes();

	Cliente saveCliente(Cliente cliente);

	Cliente updateCliente(Long idCliente, Cliente cliente);

	Cliente getClienteById(Long idCliente);

	void deleteClienteById(Long idCliente);
	
	Page<Cliente> getAllClientesPageable(Pageable pageable);
	
	Cliente findByIdClienteWithFacturaWithItemFacturaWithProducto(Long idCliente);
}
