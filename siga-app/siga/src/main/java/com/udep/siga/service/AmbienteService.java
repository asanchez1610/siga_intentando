package com.udep.siga.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.Ambiente;
import com.udep.siga.dao.AmbienteDAO;


@Service("ambienteService")
public class AmbienteService {
	
	@Autowired
    private UsuarioService usuarioService;

	@Autowired
	private AmbienteDAO ambienteDAO;
	
	public List<Ambiente> getAmbientes(){
		
		Date hoy=new Date();
        Alumno alumno = usuarioService.getInfoUsuario();
		
		List<Ambiente> list=ambienteDAO.getAmbientes(5, hoy.getDay(), 
				alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId(),
				alumno.getAlumnoEstudioList().get(0).getCampus().getId());
		
		
		return list;
	}
	

}
