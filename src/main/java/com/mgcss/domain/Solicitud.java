package com.mgcss.domain;

import java.time.LocalDate;

public class Solicitud {

	private int id;
	private Cliente cliente;
	private String descripcion;
	private LocalDate fechaCreacion;
	private EstadoSolicitud estado;
	private Tecnico tecnicoAsignado; // opcional
	private LocalDate fechaCierre; // nullable
	
    public Solicitud(int id, Cliente cliente, String descripcion) {
        this.id = id;
        this.cliente = cliente;
        this.descripcion = descripcion;
        this.fechaCreacion = LocalDate.now();
        this.estado = EstadoSolicitud.ABIERTA;
        this.tecnicoAsignado = null;
        this.fechaCierre = null;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public EstadoSolicitud getEstado() {
		return estado;
	}

	public void setEstado(EstadoSolicitud estado) {
		this.estado = estado;
	}

	public Tecnico getTecnicoAsignado() {
		return tecnicoAsignado;
	}

	public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
		this.tecnicoAsignado = tecnicoAsignado;
	}

	public LocalDate getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
}