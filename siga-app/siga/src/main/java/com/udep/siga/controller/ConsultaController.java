package com.udep.siga.controller;

import com.udep.siga.bean.CategoriaConsulta;
import com.udep.siga.bean.Consulta;
import com.udep.siga.service.ConsultaService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Administrador
 */
@Controller
@RequestMapping("/json/consulta/*")
public class ConsultaController {
    
    @Autowired
    private ConsultaService consultaService;
    
    @RequestMapping(value = "pendientesList.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getPendientesList() {        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Reclamos, consultas y sugerencias");
        data.put("solicitudes", consultaService.getPendientes());
        return data;
    }
    
    @RequestMapping(value = "finalizadosList.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getFinalizadosList() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Reclamos, consultas y sugerencias");
        data.put("solicitudes", consultaService.getFinalizados());
        return data;
    }
    
    @RequestMapping(value = "nuevo.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> nuevo(){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Nuevo reclamo,consulta y/o sugerencia");
        data.put("categorias", consultaService.getCategorias());
        return data;
    }
    
    @RequestMapping(value = "saveConsulta.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveConsulta(
            @RequestParam("cuerpo") String cuerpo, @RequestParam("categoria") Integer idCategoria){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Errores");
        Consulta consulta = new Consulta();
        consulta.setCategoria(consultaService.getCategoriaById(idCategoria));
        consulta.setConsulta(cuerpo);
        data.put("errores", consultaService.saveConsulta(consulta));
        return data;
    }
}
