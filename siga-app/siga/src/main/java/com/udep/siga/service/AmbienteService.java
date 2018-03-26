package com.udep.siga.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.Ambiente;
import com.udep.siga.bean.FechaEvento;
import com.udep.siga.bean.Unidad;
import com.udep.siga.dao.AmbienteDAO;
import com.udep.siga.util.BDConstants;

@Service("ambienteService")
public class AmbienteService {

	public static final int HORA_INICIO = 7;
	public static final int HORA_FIN = 21;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AmbienteDAO ambienteDAO;

	public List<Ambiente> getAmbientes(Integer idUnidad) {

		Date hoy = new Date();
		Alumno alumno = usuarioService.getInfoUsuario();
		int unidad = 0;
		if (idUnidad != null) {
			unidad = idUnidad;
		}

		Date fechaHoy = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());

		// Desplegamos la fecha
		Date tempDate = cal.getTime();
		System.out.println("Fecha actual: " + tempDate);

		// Le cambiamos la hora y minutos
		cal.set(Calendar.HOUR_OF_DAY, BDConstants.FECHA_HORA_LIMITE_MAXIMO);
		cal.set(Calendar.MINUTE, BDConstants.FECHA_MINUTO_CERO);
		cal.set(Calendar.SECOND, BDConstants.FECHA_MINUTO_CERO);
		tempDate = cal.getTime();

		String fechaMaxima = sdf.format(tempDate);

		// Le cambiamos la hora y minutos

		cal.set(Calendar.HOUR_OF_DAY, BDConstants.FECHA_HORA_LIMITE_MINIMO);
		cal.set(Calendar.MINUTE, BDConstants.FECHA_MINUTO_CERO);
		cal.set(Calendar.SECOND, BDConstants.FECHA_MINUTO_CERO);
		tempDate = cal.getTime();

		String fechaMinima = sdf.format(tempDate);

		List<Ambiente> list = ambienteDAO.getAmbientes(sdf.format(fechaHoy), fechaMaxima, fechaMinima, unidad,
				alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId(),
				alumno.getAlumnoEstudioList().get(0).getCampus().getId());

		return list;
	}

	public List<Unidad> getUnidadSede() {
		Alumno alumno = usuarioService.getInfoUsuario();
		List<Unidad> unidades = ambienteDAO.getUnidadSede(alumno.getAlumnoEstudioList().get(0).getCampus().getId());
		return unidades;
	}

	public Map<String, Object> eventosPorAmbiente(int idAmbiente) {

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
		int iteracciones = HORA_FIN - hora_actual;

		boolean postMeridiano = false;
		if (hora_actual >= 12) {
			postMeridiano = true;
		} else {
			postMeridiano = false;
		}

		List<String> excluidos = new ArrayList<String>();
		SimpleDateFormat sdfDisplay = new SimpleDateFormat("hh:mm aa");
		int diferenciaEventos = 0;
		if (noDisponibles != null) {
			Date fIniTemp = null;
			for (FechaEvento fechaEvento : noDisponibles) {
				diferenciaEventos = fechaEvento.getFechaHoraFin().getHours()-fechaEvento.getFechaHoraInicio().getHours();
				if(diferenciaEventos > 1) {
					
					for(int j = 0 ; j < diferenciaEventos ; j++) {
						
						if(fechaEvento.getFechaHoraInicio().getMinutes() > 0) {
							fechaEvento.setFechaHoraInicio(DateUtils.addMinutes(fechaEvento.getFechaHoraInicio(), -fechaEvento.getFechaHoraInicio().getMinutes()));
						}
						
						excluidos.add(sdfDisplay.format(fechaEvento.getFechaHoraInicio()) + " - "+ sdfDisplay.format(DateUtils.addHours(fechaEvento.getFechaHoraInicio(), 1)));
						fIniTemp = DateUtils.addHours(fechaEvento.getFechaHoraInicio(), 1);
						fechaEvento.setFechaHoraInicio(fIniTemp);
						
					}
					
				}else {
					
					boolean ajusteMinutos = false;
					if(fechaEvento.getFechaHoraInicio().getMinutes() > 0) {
						fechaEvento.setFechaHoraInicio(DateUtils.addMinutes(fechaEvento.getFechaHoraInicio(), -fechaEvento.getFechaHoraInicio().getMinutes()));
						fechaEvento.setFechaHoraFin(DateUtils.addMinutes(fechaEvento.getFechaHoraFin(), -fechaEvento.getFechaHoraFin().getMinutes()));
						ajusteMinutos = true;
					}
					
					excluidos.add(sdfDisplay.format(fechaEvento.getFechaHoraInicio()) + " - "+ sdfDisplay.format(fechaEvento.getFechaHoraFin()));
					
					if(ajusteMinutos) {
						excluidos.add(sdfDisplay.format(DateUtils.addHours(fechaEvento.getFechaHoraInicio(), 1)) + " - "+ sdfDisplay.format(DateUtils.addHours(fechaEvento.getFechaHoraFin(), 1)));
					}
					
				}
				
			}
		}
		List<String> horariosDisponibles = new ArrayList<String>();
		cal.add(Calendar.HOUR, (postMeridiano ? 12 : 0));
		if (hora_actual >= HORA_INICIO && hora_actual < HORA_FIN) {
			String line = "";
			for (int i = 0; i < iteracciones; i++) {
				line = sdfDisplay.format(cal.getTime()) + " - ";
				cal.add(Calendar.HOUR, 1);
				line += sdfDisplay.format(cal.getTime());
				if (!excluirRangoHora(line, excluidos)) {
					horariosDisponibles.add(line);
				}
			}
		}

		result.put("listaDisponibilidad", horariosDisponibles);
		return result;
	}

	private boolean excluirRangoHora(String line, List<String> excluidos) {
		boolean excluir = false;
		if (excluidos != null) {
			for (String item : excluidos) {
				if (line.equals(item)) {
					excluir = true;
					break;
				}

			}
		}
		return excluir;
	}

}
