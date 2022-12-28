package com.samuelito.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "facturas")
@Getter
@Setter
public class Factura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_factura")
	private Long idFactura;

	@NotEmpty
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "observaciones")
	private String observaciones;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	private Date createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_cliente")
	private Cliente cliente;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "id_factura")
	private List<ItemFactura> items;
	
	@PrePersist
	public void prePersist() {
		setCreatedAt(new Date());
	}
	
	public void addItemFactura(ItemFactura item) {
		items.add(item);
	}
	
	public Double getTotal() {
		return items.stream()
				.map(ItemFactura::calcularImporte)
				.reduce(0.0, (acc, curr) -> acc + curr);
				
	}
	
	public Factura() {
		items = new ArrayList<>();
	}

}
