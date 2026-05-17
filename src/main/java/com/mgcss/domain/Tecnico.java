package com.mgcss.domain;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tecnico {

	private long id;
	private String nombre;
	private EspecialidadTecnico especialidad;
	private boolean activo;
	
	public Tecnico(long id, String nombre, EspecialidadTecnico especialidad, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.especialidad = especialidad;
		this.activo = activo;
	}

}