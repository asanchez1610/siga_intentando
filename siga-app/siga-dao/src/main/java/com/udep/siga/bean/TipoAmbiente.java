package com.udep.siga.bean;

import java.io.Serializable;

public class TipoAmbiente implements Serializable{
	
	private int idTipoAmbiente;
	private String nombre;
	private String descripcion;
	
	
	
	public int getIdTipoAmbiente() {
		return idTipoAmbiente;
	}
	public void setIdTipoAmbiente(int idTipoAmbiente) {
		this.idTipoAmbiente = idTipoAmbiente;
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
