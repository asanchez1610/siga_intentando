package com.udep.siga.bean;

import java.io.Serializable;

public class Unidad implements Serializable{
 
	private int idUnidad;
	private String nombre;
	private String descripcion;
	private TipoUnidad tipoUnidad; //fk
	private SedeInfraestructura sedeInfraestructura; //fk
	
	
	public int getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
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
	public TipoUnidad getTipoUnidad() {
		return tipoUnidad;
	}
	public void setTipoUnidad(TipoUnidad tipoUnidad) {
		this.tipoUnidad = tipoUnidad;
	}
	public SedeInfraestructura getSedeInfraestructura() {
		return sedeInfraestructura;
	}
	public void setSedeInfraestructura(SedeInfraestructura sedeInfraestructura) {
		this.sedeInfraestructura = sedeInfraestructura;
	}
	
	
	
	
	
	
}
