package com.samuelito.app.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "items_factura")
@Getter
@Setter
public class ItemFactura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_item_factura")
	private Long idItemFactura;

	@Column(name = "cantidad")
	private Integer cantidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_producto")
	private Producto producto;

	public Double calcularImporte() {
		return cantidad.doubleValue() * producto.getPrecio();
	}

}
