package com.udep.siga.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.Ambiente;
import com.udep.siga.bean.Unidad;
import com.udep.siga.dao.AmbienteDAO;


@Service("ambienteService")
public class AmbienteService {
	
	@Autowired
    private UsuarioService usuarioService;

	@Autowired
	private AmbienteDAO ambienteDAO;
	
	public List<Ambiente> getAmbientes(Integer idUnidad){
		
		Date hoy=new Date();
        Alumno alumno = usuarioService.getInfoUsuario();
		int unidad = 0;
        if(idUnidad != null) {
        	unidad = idUnidad;
        }
        
		List<Ambiente> list=ambienteDAO.getAmbientes(unidad, hoy.getDay(), 
				alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId(),
				alumno.getAlumnoEstudioList().get(0).getCampus().getId());
		
		
		return list;
	}
	
	public List<Unidad> getUnidadSede(){
		 Alumno alumno = usuarioService.getInfoUsuario();
		 List<Unidad> unidades = ambienteDAO.getUnidadSede(alumno.getAlumnoEstudioList().get(0).getCampus().getId());
		 return unidades;
	}
	

}
