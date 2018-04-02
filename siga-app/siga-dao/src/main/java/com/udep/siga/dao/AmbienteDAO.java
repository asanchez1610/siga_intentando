package com.udep.siga.dao;

import java.util.List;

import com.udep.siga.bean.Ambiente;
import com.udep.siga.bean.FechaEvento;
import com.udep.siga.bean.Unidad;

public interface AmbienteDAO {

	 public List<Ambiente> getAmbientes(int idUnidad,int idPeriodoAcademico,int idCampus);
	 public List<Unidad> getUnidadSede(int idCampus);
	 public List<FechaEvento> eventosPorAmbiente(int idAmbiente , String fecha);
}
