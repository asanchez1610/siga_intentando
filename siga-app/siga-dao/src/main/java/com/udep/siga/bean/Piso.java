package com.udep.siga.bean;

import java.io.Serializable;

public class Piso implements Serializable{
	
	private int idPiso;
	private String nombre;
	private String descripcion;
	
	
	public int getIdPiso() {
		return idPiso;
	}
	public void setIdPiso(int idPiso) {
		this.idPiso = idPiso;
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
