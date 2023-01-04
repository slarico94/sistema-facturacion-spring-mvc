package com.samuelito.app.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "productos")
@Getter
@Setter
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_producto")
	private Long idProducto;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "precio")
	private Double precio;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	private Date createdAt;

	@PrePersist
	public void prePersist() {
		setCreatedAt(new Date());
	}

}
