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
import com.udep.siga.bean.enumeration.EstadoSolicitud;
import com.udep.siga.service.AmbienteService;
import com.udep.siga.service.UsuarioService;

@Controller
@RequestMapping("/json/ambientes/*")
public class AmbienteController {

    
    @Autowired
    private AmbienteService ambienteService;
	
	
	  @RequestMapping(value = "ambientesLibresList.json", method = RequestMethod.POST)
	    public @ResponseBody Map<String, Object> getAmbientesList() {
		  
		  
	        Map<String, Object> data = new HashMap<String, Object>();
	        List<Ambiente> listAmbienteDisponibles=ambienteService.getAmbientes();
	        data.put("title", "Ambientes Disponibles");
	        data.put("moroso", listAmbienteDisponibles);
	        return data;
	    }
	
}
