package com.udep.siga.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Ambiente implements Serializable{

	private  int idAmbiente;	
	private String nombre;	
	private TipoAmbiente tipoAmbiente; 	
	private String  descripcion; 	
	private BigDecimal areaM2;	
	private int aforo;	
	private int capacidadReal;	
	private char reservable;	
	private Piso piso;
	private SedeInfraestructura sedeInfraestructura;
	private Unidad unidad;
	private int cantidadHorario;
	
	public int getIdAmbiente() {
		return idAmbiente;
	}
	public void setIdAmbiente(int idAmbiente) {
		this.idAmbiente = idAmbiente;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public TipoAmbiente getTipoAmbiente() {
		return tipoAmbiente;
	}
	public void setTipoAmbiente(TipoAmbiente tipoAmbiente) {
		this.tipoAmbiente = tipoAmbiente;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getAreaM2() {
		return areaM2;
	}
	public void setAreaM2(BigDecimal areaM2) {
		this.areaM2 = areaM2;
	}
	public int getAforo() {
		return aforo;
	}
	public void setAforo(int aforo) {
		this.aforo = aforo;
	}
	public int getCapacidadReal() {
		return capacidadReal;
	}
	public void setCapacidadReal(int capacidadReal) {
		this.capacidadReal = capacidadReal;
	}
	public char getReservable() {
		return reservable;
	}
	public void setReservable(char reservable) {
		this.reservable = reservable;
	}
	public Piso getPiso() {
		return piso;
	}
	public void setPiso(Piso piso) {
		this.piso = piso;
	}
	public SedeInfraestructura getSedeInfraestructura() {
		return sedeInfraestructura;
	}
	public void setSedeInfraestructura(SedeInfraestructura sedeInfraestructura) {
		this.sedeInfraestructura = sedeInfraestructura;
	}
	public Unidad getUnidad() {
		return unidad;
	}
	public void setUnidad(Unidad unidad) {
		this.unidad = unidad;
	}
	public int getCantidadHorario() {
		return cantidadHorario;
	}
	public void setCantidadHorario(int cantidadHorario) {
		this.cantidadHorario = cantidadHorario;
	} 
	
	
	
	
}
