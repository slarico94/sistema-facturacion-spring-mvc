package com.samuelito.app.domain.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.samuelito.app.domain.Cliente;
import com.samuelito.app.domain.service.IClienteService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service("clienteServiceEmImpl")
@Transactional
public class ClienteServiceEmImpl implements IClienteService {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> getAllClientes() {
		return em.createQuery("SELECT c FROM Cliente c").getResultList();
	}

	@Override
	public Cliente saveCliente(Cliente cliente) {
		if (cliente.getIdCliente() != null) {
			em.merge(cliente);
		} else {
			em.persist(cliente);	
		}
		// em.flush();
		return em.find(Cliente.class, cliente.getIdCliente());
	}

	@Override
	public Cliente updateCliente(Long idCliente, Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente getClienteById(Long idCliente) {
		return em.find(Cliente.class, idCliente);
	}

	@Override
	public void deleteClienteById(Long idCliente) {
		em.remove(getClienteById(idCliente));

	}

	@Override
	public Page<Cliente> getAllClientesPageable(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente findByIdClienteWithFacturaWithItemFacturaWithProducto(Long idCliente) {
		// TODO Auto-generated method stub
		return null;
	}

}
