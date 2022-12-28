package com.samuelito.app.util.paginator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageItem {

	private int numero;
	
	private boolean actual;

	public PageItem(int numero, boolean actual) {
		this.numero = numero;
		this.actual = actual;
	}
	
	
}
