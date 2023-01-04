package com.samuelito.app.view.xml;

import java.util.List;

import com.samuelito.app.domain.Cliente;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "clientes")
public class ClienteList {

	@XmlElement(name = "cliente")
	private List<Cliente> clientes;

	public ClienteList() {

	}

	public ClienteList(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

}
