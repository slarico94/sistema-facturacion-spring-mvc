package com.samuelito.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.samuelito.app.domain.service.IClienteService;
import com.samuelito.app.view.xml.ClienteList;

@RestController
@RequestMapping("api/clientes")
public class ClienteRestController {
	
	@Autowired
	@Qualifier("clienteServiceImpl")
	private IClienteService clienteService;
	
	@GetMapping("/listar")
	public ClienteList listarClientes() {
		return new ClienteList(clienteService.getAllClientes());
	}
}
