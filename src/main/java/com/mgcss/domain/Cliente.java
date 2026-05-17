package com.mgcss.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {
	private long id;
	private String nombre;
	private String email;
	private TipoCliente tipo;
	
	
	public Cliente(long id, String nombre, String email, TipoCliente tipo){
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.tipo = tipo;
	}

	
}
