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
import com.udep.siga.bean.Piso;
import com.udep.siga.bean.SedeInfraestructura;
import com.udep.siga.bean.TipoAmbiente;
import com.udep.siga.bean.Unidad;
import com.udep.siga.dao.AmbienteDAO;
import com.udep.siga.util.BDConstants;

@Service("ambienteService")
public class AmbienteService {

	public static final int HORA_INICIO = 7;
	public static final int HORA_FIN = 22;
	private static final int MAXIMO_TERACCIONES = 3;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private AmbienteDAO ambienteDAO;

	public List<Ambiente> getAmbientes(Integer idUnidad) {

		Alumno alumno = usuarioService.getInfoUsuario();
		int unidad = 0;
		if (idUnidad != null) {
			unidad = idUnidad;
		}

		Date fechaHoy = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Calendar cal = null;


		List<Ambiente> list = ambienteDAO.getAmbientes(   unidad,
				alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId(),
				alumno.getAlumnoEstudioList().get(0).getCampus().getId());
		
		List<Ambiente> listFinal=new ArrayList<Ambiente>() ;
		Ambiente ambiente=null;
		for (int i = 0; i < list.size(); i++) {
			
			String fecha = sdf.format(fechaHoy);

			cal = Calendar.getInstance();
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
			
			
			if (hora_actual >= HORA_INICIO && hora_actual < HORA_FIN) {
			
			SimpleDateFormat sdfDisplay = new SimpleDateFormat("hh:mm aa");
			
			List<String> excluidos=listHorarioExcluidos(list.get(i).getIdAmbiente(), fecha,sdfDisplay);
			boolean existHorario=false;
			int contadorHorarios=0;
			cal.add(Calendar.HOUR, (postMeridiano ? 12 : 0));
			
				String line = "";
				for (int j = 0; j < iteracciones; j++) {
					line = sdfDisplay.format(cal.getTime()) + " - ";
					cal.add(Calendar.HOUR, 1);
					line += sdfDisplay.format(cal.getTime());
					if (!excluirRangoHora(line, excluidos) && (cal.getTime().getHours() - hora_actual) <3) {
						existHorario=true;
						contadorHorarios++;
					}
					if(MAXIMO_TERACCIONES == contadorHorarios) {
						break;
					}
				}
				
				if(existHorario) {

				    ambiente = new Ambiente();
		            ambiente.setAforo(list.get(i).getAforo());
		            ambiente.setAreaM2(list.get(i).getAreaM2());
		            ambiente.setCapacidadReal(list.get(i).getCapacidadReal());
		            ambiente.setDescripcion(list.get(i).getDescripcion());
		            ambiente.setIdAmbiente(list.get(i).getIdAmbiente());
		            Piso piso=new Piso();
		            piso.setIdPiso(list.get(i).getPiso().getIdPiso());
		            ambiente.setPiso(piso);
		            SedeInfraestructura sedeInfraestructura=new SedeInfraestructura();
		            sedeInfraestructura.setIdSedeInfraestructura(list.get(i).getSedeInfraestructura().getIdSedeInfraestructura());
		            ambiente.setSedeInfraestructura(sedeInfraestructura);
		            TipoAmbiente tipoAmbiente=new TipoAmbiente();
		            tipoAmbiente.setIdTipoAmbiente(list.get(i).getTipoAmbiente().getIdTipoAmbiente());
		            ambiente.setTipoAmbiente(tipoAmbiente);
		            Unidad unida=new Unidad();
		            unida.setIdUnidad(list.get(i).getUnidad().getIdUnidad());
		            unida.setNombre(list.get(i).getUnidad().getNombre());
		            ambiente.setUnidad(unida);
		            ambiente.setNombre(list.get(i).getNombre());
		            ambiente.setReservable(list.get(i).getReservable());
		            listFinal.add(ambiente);
					
				}
				
			}
			
		}

		return listFinal;
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
		SimpleDateFormat sdfDisplay = new SimpleDateFormat("hh:mm aa");
		
		List<String> excluidos=listHorarioExcluidos(idAmbiente, fecha,sdfDisplay);
		

		
		List<String> horariosDisponibles = new ArrayList<String>();
		cal.add(Calendar.HOUR, (postMeridiano ? 12 : 0));
		if (hora_actual >= HORA_INICIO && hora_actual < HORA_FIN) {
			String line = "";
			for (int i = 0; i < iteracciones; i++) {
				line = sdfDisplay.format(cal.getTime()) + " - ";
				cal.add(Calendar.HOUR, 1);
				line += sdfDisplay.format(cal.getTime());
				if (!excluirRangoHora(line, excluidos) && (cal.getTime().getHours() - hora_actual) <3) {
					horariosDisponibles.add(line);
				}
				if(MAXIMO_TERACCIONES == horariosDisponibles.size()) {
					break;
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
	
	
	private List<String> listHorarioExcluidos(int idAmbiente, String fecha, SimpleDateFormat sdfDisplay){
		
		List<FechaEvento> noDisponibles = ambienteDAO.eventosPorAmbiente(idAmbiente, fecha);

		
		
		List<String> excluidos = new ArrayList<String>();
		
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
		return excluidos;
	};

}
