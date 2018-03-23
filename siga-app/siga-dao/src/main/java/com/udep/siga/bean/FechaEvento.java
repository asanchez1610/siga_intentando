package com.udep.siga.bean;

import java.io.Serializable;
import java.util.Date;

public class FechaEvento implements Serializable {

	private static final long serialVersionUID = -6938377400040547928L;

	private Integer id;

	private Integer idEvento;

	private Date fechaHoraInicio;

	private Date fechaHoraFin;

	private Integer idAmbiente;

	private Integer horaInicio;

	private Integer horaFin;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Integer idEvento) {
		this.idEvento = idEvento;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public Integer getIdAmbiente() {
		return idAmbiente;
	}

	public void setIdAmbiente(Integer idAmbiente) {
		this.idAmbiente = idAmbiente;
	}

	public Integer getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Integer horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Integer getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Integer horaFin) {
		this.horaFin = horaFin;
	}

}
