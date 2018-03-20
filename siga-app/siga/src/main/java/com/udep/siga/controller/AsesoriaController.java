/*
 * Copyright (c) 2014, Universidad de Piura. All rights reserved.
 * UDEP PROPRIETARY/CONFIDENTIAL.
 *
 */
package com.udep.siga.controller;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AsesorSugerido;
import com.udep.siga.bean.Persona;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.UsuarioService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author gary.ayala
 */
@Controller
@RequestMapping(value = "/asesoria")
public class AsesoriaController {
    private @Autowired UsuarioService usuarioService;
    private @Autowired AlumnoEstudioService alumnoEstudioService;
    private @Autowired AlumnoDAO alumnoDAO;
    private @Autowired CommonsService commonsService;
    
    @RequestMapping(value = "/elegirAsesor.htm", method = RequestMethod.GET)
    public ModelAndView elegirAsesor() {
        ModelAndView mav = new ModelAndView("eleccion_asesor");
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        //enviar info de asesor y lista de asesores
        int idestudio = alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getId();
        Persona asesor = alumnoEstudioService.getAsesorAlumno();

        mav.addObject("periodo", "");
        mav.addObject("idAsesor", "");
        mav.addObject("nombreAsesor", "");
        mav.addObject("fotoAsesor", "");
        mav.addObject("sugeridosList", null);

        if (asesor != null) {
            mav.addObject("idAsesor", usuarioService.addRefIndirecta(asesor.getId()));
            mav.addObject("id", asesor.getId());
            mav.addObject("nombreAsesor", String.format("%s %s, %s", asesor.getApellidoPaterno(), asesor.getApellidoMaterno(),
                    asesor.getNombres()));
            mav.addObject("fotoAsesor", asesor.getFoto());
        }

        mav.addObject("periodo", alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getNombre());
        String errores = "Por favor selecciona tus opciones de asesor.";
        List< Map<String, Object>> sugeridosList = alumnoEstudioService.getSugeridosList(alumno.getId(),
                alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId());
        mav.addObject("sugeridosList", sugeridosList);
        // mav.addAllObjects(sugeridosList);
        mav.addObject("currentPlan", usuarioService.addRefIndirecta(idestudio));
        mav.addObject("nombre", "Selección de asesores");
        mav.addObject("errores", errores);
                
        return mav;
    }
    
    @RequestMapping(value = "/mantenerAsesor.json", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> mantenerAsesor() {
        Map<String,Object> data = new HashMap<String,Object>();
        
        try{
            commonsService.registrarAsesoriaHistorial(1);
            
            data.put("status", true);
        }catch(Exception ex){
            ex.printStackTrace();
            
            data.put("status", false);
        }
        
        return data;
    }
    
    @RequestMapping(value = "/guardarinfoasesor.json", method = RequestMethod.POST)
    public @ResponseBody Map<String,Object> guardarasesores(@RequestParam("sugerido_1") String idSugerido_1Random,
            @RequestParam("sugerido_2") String idSugerido_2Random,
            @RequestParam("idedicionestudio") String idEdicionestudioRandom
    ) {
        Map<String,Object> data = new HashMap<String,Object>();
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        Integer idSugerido_1 = (Integer) usuarioService.getRefDirecta(idSugerido_1Random);
        Integer idSugerido_2 = (Integer) usuarioService.getRefDirecta(idSugerido_2Random);

        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);

        AsesorSugerido asesores = new AsesorSugerido();
        asesores.setAsesor_sugerido_1(new Persona(idSugerido_1));
        asesores.setAsesor_sugerido_2(new Persona(idSugerido_2));
        
        try{
            alumnoEstudioService.saveAsesorSugerido(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId(), asesores);
        }catch(Exception ex){
            ex.printStackTrace();
            data.put("status", false);
            
            return data;
        }

        try{
            commonsService.registrarAsesoriaHistorial(2);
            data.put("status", true);
        }catch(Exception ex){
            ex.printStackTrace();
            data.put("status", false);
        }
        
        return data;

    }
}
