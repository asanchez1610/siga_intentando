package com.udep.siga.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.Ambiente;
import com.udep.siga.bean.Unidad;
import com.udep.siga.dao.AmbienteDAO;
import com.udep.siga.util.BDConstants;


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
        
        Date fechaHoy=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		//Desplegamos la fecha
		Date tempDate = cal.getTime();
		System.out.println("Fecha actual: " + tempDate);
		
		//Le cambiamos la hora y minutos
		cal.set(Calendar.HOUR_OF_DAY,BDConstants.FECHA_HORA_LIMITE_MAXIMO );
		cal.set(Calendar.MINUTE, BDConstants.FECHA_MINUTO_CERO);
		cal.set(Calendar.SECOND, BDConstants.FECHA_MINUTO_CERO);
		tempDate = cal.getTime();
		
        String fechaMaxima=sdf.format(tempDate);
        
    	//Le cambiamos la hora y minutos
        
		cal.set(Calendar.HOUR_OF_DAY,BDConstants.FECHA_HORA_LIMITE_MINIMO );
		cal.set(Calendar.MINUTE, BDConstants.FECHA_MINUTO_CERO);
		cal.set(Calendar.SECOND, BDConstants.FECHA_MINUTO_CERO);
		tempDate = cal.getTime();
        
        String fechaMinima=sdf.format(tempDate);
        
		List<Ambiente> list=ambienteDAO.getAmbientes( sdf.format(fechaHoy),fechaMaxima,fechaMinima,unidad,  
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
