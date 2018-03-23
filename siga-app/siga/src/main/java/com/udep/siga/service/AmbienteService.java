package com.udep.siga.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.Ambiente;
import com.udep.siga.bean.FechaEvento;
import com.udep.siga.bean.Unidad;
import com.udep.siga.dao.AmbienteDAO;


@Service("ambienteService")
public class AmbienteService {
	
	public static final int HORA_INICIO = 8;
	public static final int HORA_FIN = 22;
	
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
	
	public Map<String, Object> eventosPorAmbiente(int idAmbiente){
		 
		Map<String, Object> result = new HashMap<String, Object>();
		
		 Date now = new Date();
		 SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		 SimpleDateFormat sdfHoraActual = new SimpleDateFormat("hh:mm a");
		 
		 result.put("horaActual", sdfHoraActual.format(now));
		 
		 String fecha = sdf.format(now);
		 List<FechaEvento> noDisponibles = ambienteDAO.eventosPorAmbiente(idAmbiente, fecha);

		  Calendar cal = Calendar.getInstance(); 
		  cal.setTime(new Date()); 
		  cal.set(Calendar.MINUTE, 0);
		  cal.set(Calendar.SECOND, 0);
		  
		  int hora_actual = cal.get(Calendar.HOUR_OF_DAY);
		  		  
		  cal.set(Calendar.HOUR, hora_actual);
		  
		  int iteracciones = HORA_FIN-hora_actual;

		  List<String> horariosDisponibles = new ArrayList<String>();
		  
		  if(hora_actual >= HORA_INICIO && hora_actual <= HORA_FIN) {
			  SimpleDateFormat sdformat = new SimpleDateFormat("hh:mm a");
			  String line = "";
			  int horaTemp = 0;
			  for(int i = 0 ; i < iteracciones ; i++) {
				  
				  horaTemp = cal.get(Calendar.HOUR);
				  if(!this.ocupado(horaTemp,noDisponibles)) {
					  line = sdformat.format(cal.getTime())+" - ";
					  cal.add(Calendar.HOUR, 1);
					  line+=sdformat.format(cal.getTime());
					  System.out.println(line);
					  horariosDisponibles.add(line);
				  }
				
			  }
		  }
		  
		 result.put("listaDisponibilidad",horariosDisponibles);
		 return result;
	}

	private boolean ocupado(int horaTemp, List<FechaEvento> noDisponibles) {
		boolean ocupado = false;
		if(noDisponibles != null) {
			for (FechaEvento fechaEvento : noDisponibles) {
				if(horaTemp >= fechaEvento.getHoraInicio() && horaTemp <= fechaEvento.getHoraFin()) {
					ocupado = true;
					break;
				}
			}
		}
		return ocupado;
	}
	

}
