package com.samuelito.app.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clientes")
@Getter
@Setter
public class Cliente {

	@Id
	@Column(name = "id_cliente")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idCliente;

	@NotBlank
	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@NotEmpty
	@Email
	@Column(name = "email")
	private String email;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Column(name = "created_at", nullable = false)
	private Date createdAt;

	@Column(name = "foto")
	private String foto;

	@PrePersist
	public void prePersist() {
		setCreatedAt(new Date());
	}

	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Factura> facturas;

	public void addFactura(Factura factura) {
		facturas.add(factura);
	}

	public Cliente() {
		facturas = new HashSet<>();
	}

}
