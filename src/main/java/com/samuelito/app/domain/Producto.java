package com.samuelito.app.domain;

import java.util.Date;

import jakarta.persistence.*;
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
