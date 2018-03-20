package com.udep.siga.controller;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.Material;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.Silabo;
import com.udep.siga.bean.enumeration.EstadoAlumno;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.AsignaturaDictadaService;
import com.udep.siga.service.AvisoService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.Rol;
import com.udep.siga.util.SessionConstants;
import com.udep.siga.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
@RequestMapping("/json/asignatura/*")
public class AsignaturaController {
    @Autowired
    private AlumnoEstudioService alumnoEstudioService;
    @Autowired
    private AsignaturaDictadaService asignaturaDictadaService;
    @Autowired
    private CommonsService commonsService;
    @Autowired
    private AvisoService avisoService;
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "estudioVigenteList.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getEstudioVigenteList(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute(SessionConstants.ESTUDIOVIGENTE) == null) {
            Map<String, Object> item;
            for (AlumnoEstudio alumnoEstudio : alumnoEstudioService.getEstudiosAlumnoList()) {
                item = new HashMap<String, Object>();
                item.put("id", usuarioService.addRefIndirecta(alumnoEstudio.getEdicionestudio().getId()));
                item.put("nombre", alumnoEstudio.getEdicionestudio().getEstudio().getNombre());
                item.put("pa", alumnoEstudio.getPeriodoAcademicoVigente().getNombre());
                list.add(item);
            }
            httpSession.setAttribute(SessionConstants.ESTUDIOVIGENTE, list);
        }else{
            list = (List<Map<String, Object>>) httpSession.getAttribute(SessionConstants.ESTUDIOVIGENTE);
        }

        data.put("estudios", list);
        data.put("add", "");
        
        return data;
    }
    
    @RequestMapping(value = "estudioAllList.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getEstudioAllList(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute(SessionConstants.ESTUDIOALL) == null) {
            Map<String, Object> item;
            for (AlumnoEstudio alumnoEstudio : alumnoEstudioService.getEstudioAllList()) {
                item = new HashMap<String, Object>();
                item.put("id", usuarioService.addRefIndirecta(alumnoEstudio.getEdicionestudio().getId()));
                item.put("nombre", alumnoEstudio.getEdicionestudio().getEstudio().getNombre());
                if(alumnoEstudio.getPeriodoAcademicoVigente() != null && 
                        alumnoEstudio.getEstadoAlumno() == EstadoAlumno.Alumno){
                    item.put("pa", alumnoEstudio.getPeriodoAcademicoVigente().getNombre());
                }else{
                    item.put("pa", null);
                }
                list.add(item);
            }
            httpSession.setAttribute(SessionConstants.ESTUDIOALL, list);
        }else{
            list = (List<Map<String, Object>>) httpSession.getAttribute(SessionConstants.ESTUDIOALL);
        }

        data.put("estudios", list);
        data.put("add", " - (v)");
        return data;
    }
    
    @RequestMapping(value = "asignaturaDictadaList.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAsignaturaDictadaList(
            @RequestParam ("idEdicionestudio") String idEdicionestudioRandom) {     
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
         AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Mis asignaturas");

        if (idEdicionestudio != null 
                && !(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol()))){
            List<AsignaturaDictada> list = asignaturaDictadaService.getAsignaturaDictadaList(idEdicionestudio,usuarioService.isAlumnoMoroso(alumnoEstudio));
            List<Map<String, Object>> asignaturaList = new ArrayList<Map<String, Object>>();
            Map<String, Object> item;
            for (AsignaturaDictada asignaturaDictada : list) {
                item = new HashMap<String, Object>();
                item.put("id", usuarioService.addRefIndirecta(asignaturaDictada.getId()));
                
                item.put("sigla", asignaturaDictada.getSigla().replaceAll("[^\\w\\s]", ""));
                item.put("nombre", asignaturaDictada.getNombre());
                item.put("descripcionSeccion", asignaturaDictada.getAsignaturaSeccion().getDescripcion());
                item.put("idSeccion", usuarioService.addRefIndirecta(asignaturaDictada.getAsignaturaSeccion().getIdSeccion()));
                item.put("nombreSeccion", asignaturaDictada.getAsignaturaSeccion().getNombreSeccion());
                item.put("retiroCurso", asignaturaDictada.isRetiroCurso());
                
                Map<String, Boolean> notifyAsignatura = asignaturaDictadaService
                        .statusNotificacion(idEdicionestudio, asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion());
                item.put("aviso", notifyAsignatura.get("AVISO"));
                item.put("material", notifyAsignatura.get("MATERIAL"));
                item.put("evaluacion", notifyAsignatura.get("EVALUACION"));
                
                asignaturaList.add(item);
            }
            data.put("asignaturas", asignaturaList);
            data.put("anulaCiclo", alumnoEstudioService.anuloCicloByAlumnoEstudioPeriodoAcademico(
                    idEdicionestudio));
        }else{
            data.put("asignaturas", new ArrayList<AsignaturaDictada>());
            data.put("anulaCiclo", false);
        }
        return data;
    }
    
    @RequestMapping(value = "asignaturaDetalle.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAsignaturaDetalle(
            @RequestParam ("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam ("idSeccion") String idSeccionRandom){
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        
        Map<String, Object> data = new HashMap<String, Object>();
        AsignaturaDictada asignaturaDictada = asignaturaDictadaService
                .getAsignaturaDictadaDetalle(idAsignaturaDictada, idSeccion);
        data.put("title", "Mis asignaturas - " + asignaturaDictada.getSigla());
        
        data.put("id", idAsignaturaDictadaRandom);
        data.put("sigla", asignaturaDictada.getSigla());
        data.put("nombre", asignaturaDictada.getNombre());
        data.put("idSeccion", idSeccionRandom);
        data.put("nombreSeccion", asignaturaDictada.getAsignaturaSeccion().getNombreSeccion());
        List<Map<String, Object>> profesorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        for (Persona persona : asignaturaDictada.getProfesorList()) {
            item = new HashMap<String, Object>();
           
            int id = 0;
            id = persona.getId();
            Boolean esAsistente = false;
            esAsistente = usuarioService.isAsistente(id, idAsignaturaDictada, idSeccion);
            if(esAsistente==false)
            {
            item.put("id", usuarioService.addRefIndirecta(persona.getId()));
            item.put("nombre", persona.getNombres() + " " + persona.getApellidoPaterno()); 
            }
            profesorList.add(item);
        }
        data.put("profesorList", profesorList);
        
        data.put("countAlumnos", asignaturaDictadaService.countAlumnosAsignaturaDictada(asignaturaDictada));
        return data;
    }
    
    @RequestMapping(value = "asignatura-evaluaciones.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAsignaturaEvaluacionAlumnoList(
            @RequestParam ("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam ("idSeccion") String idSeccionRandom,
            @RequestParam ("idEdicionestudio") String idEdicionestudioRandom){
        
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);

        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        Map<String, Object> data = asignaturaDictadaService
                .getDatosAsistenciaAsignatura(idAsignaturaDictada, idSeccion,usuarioService.isAlumnoMoroso(alumnoEstudio));
        
//        data.put("moroso", alumnoEstudio.isMoroso());
        data.put("moroso", usuarioService.isAlumnoMoroso(alumnoEstudio));
        
        data.put("tipos", asignaturaDictadaService.getAsignaturaEvaluacionAlumnoList(
                idAsignaturaDictada, idSeccion,usuarioService.isAlumnoMoroso(alumnoEstudio)));
        
        data.put("promedio", asignaturaDictadaService.getPromedioAsignaturaDictadaByAlumno
                (idAsignaturaDictada, idSeccion,usuarioService.isAlumnoMoroso(alumnoEstudio)));
        
        data.putAll(asignaturaDictadaService.getFormulaPromedioAsignaturaDictada(idAsignaturaDictada, idSeccion));
        return data;
    }
    
    @RequestMapping(value = "evaluaciones-temas.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getEvaluacionTemasList(
            @RequestParam ("idEvaluacion") String idEvaluacionRandom){
        
        Integer idEvaluacion = (Integer)usuarioService.getRefDirecta(idEvaluacionRandom);

        Map<String, Object> data = new HashMap<String, Object>();
       
        data.putAll(asignaturaDictadaService.getEvaluacionTemasList(idEvaluacion));
               
        return data;
    }
    
    @RequestMapping(value = "asignatura-material.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAsignaturaMaterialList(
            @RequestParam ("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam ("idSeccion") String idSeccionRandom){
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Map<String, Object> data = new HashMap<String, Object>();        
        data.put("tipos", asignaturaDictadaService.getTipoMaterialList(idAsignaturaDictada, idSeccion));
        return data;
    }
    
    @RequestMapping(value = "downloadMaterial/{id}", method = RequestMethod.GET)
    public void downloadMaterial(@PathVariable(value = "id") String idRandom,
            HttpServletResponse response) {
        try {
            Integer id = (Integer)usuarioService.getRefDirecta(idRandom);
            Material material = asignaturaDictadaService.getMaterialDownload(id);            
            Util.getDownload(material.getRutaArchivo(), material.getNombreArchivo(), response);
        } catch (Exception e) {
            System.out.println("Error al descargar material: " + e.getMessage());
        }
    }
    
    @RequestMapping(value = "asignatura-asistencia.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAsignaturaAsistencia(
            @RequestParam ("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam ("idSeccion") String idSeccionRandom){
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Map<String, Object> data = asignaturaDictadaService.getAsistenciaAsignatura(idAsignaturaDictada, idSeccion);
        data.put("asistenciaList", asignaturaDictadaService
                .getAsistenciaAlumnoAsignaturaList(idAsignaturaDictada, idSeccion));
        return data;
    }
    
    @RequestMapping(value = "asignatura-avisos.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAsignaturaAvisos(
            @RequestParam ("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam ("idSeccion") String idSeccionRandom){
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Map<String, Object> data = new HashMap<String, Object>();        
        data.put("avisos", avisoService.getAvisoAsignaturaList(idAsignaturaDictada, idSeccion));
        return data;
    }
    
    @RequestMapping(value = "asignatura-alumnos.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getAsignaturaAlumnos(
            @RequestParam("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam("idSeccion") String idSeccionRandom) {
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("alumnos", asignaturaDictadaService.getAlumnosAsignaturaDictadaList(idAsignaturaDictada, idSeccion));
        return data;
    }
    
    @RequestMapping(value = "asignatura-silabo.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getAsignaturaSilabo(
            @RequestParam("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam("idSeccion") String idSeccionRandom,
            @RequestParam ("idEdicionestudio") String idEdicionestudioRandom) {
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        //datos 
        //System.out.println("idAsignaturaDictada"+idAsignaturaDictada+" idSeccion"+idSeccion+" idEdicionestudio"+idEdicionestudio);
        Silabo silabo = asignaturaDictadaService
                .getSilaboByAsignaturaDictada(idAsignaturaDictada, idSeccion, idEdicionestudio);
        
        Map<String, Object> data = new HashMap<String, Object>();
        List list = new ArrayList();
        
        if (silabo == null){
            data.put("secciones", list);
            return data;
        }
        
        data.put("id", usuarioService.addRefIndirecta(silabo.getId()));
                
        List itemsList = new ArrayList();
        Map<String, Object> seccion = new HashMap<String, Object>();
        seccion.put("titulo", "Datos informativos");
        seccion.put("descripcion", "");        
        Map<String, String> seccionItems = new HashMap<String, String>();
        seccionItems.put("titulo", "Asignatura");
        seccionItems.put("contenido", silabo.getAsignaturaDictada().getNombre());
        itemsList.add(seccionItems);
        seccionItems = new HashMap<String, String>();
        seccionItems.put("titulo", "Sección");
        seccionItems.put("contenido", silabo.getAsignaturaDictada().getAsignaturaSeccion().getNombreSeccion());
        itemsList.add(seccionItems);
        seccionItems = new HashMap<String, String>();
        seccionItems.put("titulo", "Sigla");
        seccionItems.put("contenido", silabo.getAsignaturaDictada().getSigla());
        itemsList.add(seccionItems);
        seccionItems = new HashMap<String, String>();
        seccionItems.put("titulo", "N° créditos");
        seccionItems.put("contenido", String.valueOf(silabo.getAsignaturaDictada().getCreditos()));
        itemsList.add(seccionItems);
        seccionItems = new HashMap<String, String>();
        seccionItems.put("titulo", "Semestre");
        seccionItems.put("contenido", silabo.getAsignaturaDictada().getPeriodoAcademico().getNombre());
        itemsList.add(seccionItems);
        seccionItems = new HashMap<String, String>();
        seccionItems.put("titulo", "Tipo de asignatura");
        seccionItems.put("contenido", silabo.getAsignaturaDictada().getTipoAsignatura());
        itemsList.add(seccionItems);
        seccionItems = new HashMap<String, String>();
        String profesores = "";
        for (Persona persona : silabo.getAsignaturaDictada().getProfesorList()) {
            profesores += String.format("%s %s/", persona.getNombres(), persona.getApellidoPaterno() );
        }
        if (!profesores.isEmpty()){
            profesores = profesores.substring(0, profesores.length() - 1);
        }
        seccionItems.put("titulo", "Profesor(es)");
        seccionItems.put("contenido", profesores);
        itemsList.add(seccionItems);
        seccion.put("items", itemsList);
        seccion.put("vinetas", new ArrayList());
        list.add(seccion);
        
        seccion = new HashMap<String, Object>();
        seccion.put("titulo", "Sumilla");
        seccion.put("descripcion", silabo.getSumilla());
        seccion.put("items", new ArrayList());
        seccion.put("vinetas", new ArrayList());
        list.add(seccion);
        
        seccion = new HashMap<String, Object>();
        seccion.put("titulo", "Objetivos");
        seccion.put("descripcion", "");
        seccion.put("items", new ArrayList());
        seccion.put("vinetas", silabo.getObjetivoList());
        list.add(seccion);
        
        data.put("secciones", list);
        return data;
    }
    
    @RequestMapping(value = "statusNotificacion.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> statusNotificacion(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);        
        
        Map<String, Boolean> result = asignaturaDictadaService.statusNotificacion(idEdicionestudio);
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        data.put("showNotify", false);
        
        if(result.get("AVISO")){
            data.put("showNotify", true);
        }else if(result.get("MATERIAL")){
            data.put("showNotify", true);
        }else if(result.get("EVALUACION")){
            data.put("showNotify", true);
        }
        
        return data;
    }
}
