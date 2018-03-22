package com.udep.siga.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udep.siga.bean.Ambiente;
import com.udep.siga.bean.Unidad;
import com.udep.siga.service.AmbienteService;

@Controller
@RequestMapping("/json/ambientes/*")
public class AmbienteController {

	private static final Object TITULO_VIEW_AMBIENTES = "Ambientes Disponibles";
	private static final String LABEL_DATA = "data";
	@Autowired
	private AmbienteService ambienteService;

	@RequestMapping(value = "ambientesLibresList.json", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAmbientesList() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("title", TITULO_VIEW_AMBIENTES);
		return data;
	}

	@RequestMapping(value = "/data.json", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> listarAmbientes(Integer idUnidad) {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			List<Ambiente> listAmbienteDisponibles = ambienteService.getAmbientes(idUnidad);
			data.put(LABEL_DATA, listAmbienteDisponibles);
		} catch (Exception e) {
			e.printStackTrace();
			data.put(LABEL_DATA, null);
		}
		return data;
	}
	
	@RequestMapping(value = "/unidades.json", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> listarUnidades() {
		Map<String, Object> data = new HashMap<String, Object>();
		try {
			List<Unidad> unidades = ambienteService.getUnidadSede();
			data.put(LABEL_DATA, unidades);
		} catch (Exception e) {
			e.printStackTrace();
			data.put(LABEL_DATA, null);
		}
		return data;
	}

}
