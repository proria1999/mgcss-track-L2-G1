package com.mgcss.domain;

public class Cliente {
//
	private int id;
	private String nombre;
	private String email;
	private TipoCliente tipo;
	
	
	public Cliente(int id, String nombre, String email, TipoCliente tipo){
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.tipo = tipo;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public TipoCliente getTipo() {
		return tipo;
	}


	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo;
	}
	
	
}
