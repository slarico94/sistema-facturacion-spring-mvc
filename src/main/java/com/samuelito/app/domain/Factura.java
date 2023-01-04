package com.samuelito.app.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "facturas")
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

	@JsonBackReference
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

	public Long getIdFactura() {
		return idFactura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@XmlTransient
	public Cliente getCliente() {
		return cliente;
	}

	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

}
