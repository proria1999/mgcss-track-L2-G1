package com.mgcss.domain;

public class Tecnico {

	private int id;
	private String nombre;
	private EspecialidadTecnico especialidad;
	private boolean activo;
	
	public Tecnico(int id, String nombre, EspecialidadTecnico especialidad, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.especialidad = especialidad;
		this.activo = activo;
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

	public EspecialidadTecnico getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(EspecialidadTecnico especialidad) {
		this.especialidad = especialidad;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	

}