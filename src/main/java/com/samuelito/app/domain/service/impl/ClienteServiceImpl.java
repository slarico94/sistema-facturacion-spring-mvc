package com.samuelito.app.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samuelito.app.domain.Cliente;
import com.samuelito.app.domain.service.IClienteService;
import com.samuelito.app.repository.ClienteRepository;

@Service("clienteServiceImpl")
@Primary
public class ClienteServiceImpl implements IClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public List<Cliente> getAllClientes() {
		return clienteRepository.findAll();
	}

	@Override
	public Cliente saveCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente updateCliente(Long idCliente, Cliente cliente) {
		return clienteRepository.save(cliente);
	}

	@Override
	public Cliente getClienteById(Long idCliente) {
		return clienteRepository.findById(idCliente).orElse(null);
	}

	@Override
	public void deleteClienteById(Long idCliente) {
		clienteRepository.deleteById(idCliente);

	}

	@Override
	public Page<Cliente> getAllClientesPageable(Pageable pageable) {
		return clienteRepository.findAll(pageable);
	}

}
