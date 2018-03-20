package com.udep.siga.bean;

import java.io.Serializable;

public class TipoUnidad implements Serializable {
	
	private int idTipoUnidad;
	private String nombre;
	private String descripcion;
	
	
	public TipoUnidad() {
		// TODO Auto-generated constructor stub
	}
	
	public int getIdTipoUnidad() {
		return idTipoUnidad;
	}
	public void setIdTipoUnidad(int idTipoUnidad) {
		this.idTipoUnidad = idTipoUnidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	

}
