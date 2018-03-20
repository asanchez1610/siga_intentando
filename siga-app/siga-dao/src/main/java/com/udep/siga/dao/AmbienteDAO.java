package com.udep.siga.dao;

import java.util.List;

import com.udep.siga.bean.Ambiente;

public interface AmbienteDAO {

	 public List<Ambiente> getAmbientes(int idBloqueoHoraio,int idDia,int idPeriodoAcademico,int idCampus);  
}
