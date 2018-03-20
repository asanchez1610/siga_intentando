package com.udep.siga.bean;

import java.io.Serializable;

public class Campus  implements Serializable{
	
	private int idCampus;
	private Distrito idDistrito;
	private String nombre;
	
	
	public Campus() {
		// TODO Auto-generated constructor stub
	}
	
	public int getIdCampus() {
		return idCampus;
	}
	public void setIdCampus(int idCampus) {
		this.idCampus = idCampus;
	}
	public Distrito getIdDistrito() {
		return idDistrito;
	}
	public void setIdDistrito(Distrito idDistrito) {
		this.idDistrito = idDistrito;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	
	

}
