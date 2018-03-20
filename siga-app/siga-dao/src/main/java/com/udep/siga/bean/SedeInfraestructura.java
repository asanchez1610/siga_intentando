package com.udep.siga.bean;

import java.io.Serializable;

public class SedeInfraestructura implements Serializable{
	
	
	private int idSedeInfraestructura;
	private Distrito distrito;//fk
	private String nombre;
	private String sigla;
	private Campus campus; //fk
	
	
	public SedeInfraestructura() {
		// TODO Auto-generated constructor stub
	}
	
	public int getIdSedeInfraestructura() {
		return idSedeInfraestructura;
	}
	public void setIdSedeInfraestructura(int idSedeInfraestructura) {
		this.idSedeInfraestructura = idSedeInfraestructura;
	}
	public Distrito getDistrito() {
		return distrito;
	}
	public void setDistrito(Distrito distrito) {
		this.distrito = distrito;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Campus getCampus() {
		return campus;
	}
	public void setCampus(Campus campus) {
		this.campus = campus;
	}
	
	
	

}
