package com.udep.siga.controller;

import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.AsignaturaSeccion;
import com.udep.siga.bean.Horario;
import com.udep.siga.bean.Profesor;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.PracticaProgramada;
import com.udep.siga.service.AsignaturaDictadaService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.ProfesorService;
import com.udep.siga.service.UsuarioService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
@RequestMapping("/json/buscar/*")
public class BuscarController {

    @Autowired
    private CommonsService commonsService;
    @Autowired
    private ProfesorService profesorService;
    @Autowired
    private AsignaturaDictadaService asignaturaDictadaService;
    @Autowired
    private UsuarioService usuarioService;
    
    @RequestMapping(value = "buscarProfesor.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> buscarProfesor(
            @RequestParam(value = "nombre") String nombre, 
            @RequestParam(value = "paterno") String apePaterno, 
            @RequestParam(value = "materno") String apeMaterno) {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> profesorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        for (Profesor profesor : profesorService.findByNombreApellidos(nombre, apePaterno, apeMaterno)) {
            item = new HashMap<String, Object>();
            item.put("id", usuarioService.addRefIndirecta(profesor.getId()));
            item.put("nombres", profesor.getNombres()); 
            item.put("apellidoPaterno", profesor.getApellidoPaterno());
            item.put("apellidoMaterno", profesor.getApellidoMaterno());
            item.put("centroAcademicoNombre",profesor.getCentroAcademicoNombre());
            item.put("departamentoNombre",profesor.getDepartamentoNombre());
            profesorList.add(item);
        }
        data.put("profesorList", profesorList);
       
        data.put("errores",new ArrayList<String>());
        return data;
    }
    
    @RequestMapping(value = "buscarAsignatura.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> buscarAsignatura(
            @RequestParam(value = "campus") Integer idCampus, 
            @RequestParam(value = "facultad") Integer idCentroAcademico, 
            @RequestParam(value = "pa") Integer idPeriodoAcademico,
            @RequestParam(value = "nombre") String nombre,
            @RequestParam(value = "sigla") String sigla) {
        Map<String, Object> data = new HashMap<String, Object>();
        List<String> errores = new ArrayList<String>();
        
        if(idCampus == 0){
            errores.add("El Campus es requerido.");
        }
        
        if(errores.isEmpty() && (!nombre.isEmpty() || !sigla.isEmpty())){
            List<Map<String, Object>> asignaturaList = new ArrayList<Map<String, Object>>();
        
        for (AsignaturaDictada asignaturaDictada : asignaturaDictadaService.findAsignaturaDictadaByCampusCAcadPeriodoAcademico(
                    idCampus, idCentroAcademico, idPeriodoAcademico, sigla, nombre)) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("id", usuarioService.addRefIndirecta(asignaturaDictada.getId()));
            item.put("sigla", asignaturaDictada.getSigla()); 
            item.put("nombre", asignaturaDictada.getNombre());
            item.put("creditos", asignaturaDictada.getCreditos());
            item.put("campus", asignaturaDictada.getCampus());
            item.put("estudio", asignaturaDictada.getEstudio());
            item.put("totalMatriculado", asignaturaDictada.getTotalMatriculado());
            
            Map<String, Object> itemAsignaturaSeccion = new HashMap<String, Object>();
            itemAsignaturaSeccion.put("idSeccion", usuarioService.addRefIndirecta(asignaturaDictada.getAsignaturaSeccion().getIdSeccion()));
            itemAsignaturaSeccion.put("nombreSeccion",asignaturaDictada.getAsignaturaSeccion().getNombreSeccion());
            item.put("asignaturaSeccion", itemAsignaturaSeccion);
            
            Map<String, Object> itemPeriodoAcademico = new HashMap<String, Object>();
            
            itemPeriodoAcademico.put("nombre",asignaturaDictada.getPeriodoAcademico().getNombre());
            item.put("periodoAcademico", itemPeriodoAcademico);
            
            asignaturaList.add(item);
        }
        data.put("asignaturaList", asignaturaList);
        }else{
            data.put("asignaturaList", new ArrayList<AsignaturaDictada>());
        }
        data.put("errores", errores);
        return data;
    }
    
    @RequestMapping(value = "centroAcademicoList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getCentroAcademicoList(
            @RequestParam(value = "idCampus", required = true) int idCampus) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("centroAcademicoList", commonsService.getCentroAcademicoList(idCampus));
        return data;
    }
    
    @RequestMapping(value = "peridoAcademicoList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPeridoAcademicoList(
            @RequestParam(value = "idCentroAcademico", required = true) int idCentroAcademico) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("peridoAcademicoList", commonsService.getPeriodoAcademicoByCentroAcademicoList(idCentroAcademico));
        return data;
    }
    
    /*
     * Detalle de una Asignatura
     */
    @RequestMapping(value = "asignaturaDetalle.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getAsignaturaDetalle(
            @RequestParam ("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam ("idSeccion") String idSeccionRandom){
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        AsignaturaDictada asignaturaDictada = asignaturaDictadaService
                .getAsignaturaDictadaDetalleB(idAsignaturaDictada, idSeccion);
        data.put("title", "Asignaturas");
        
        Map<String, Object> item;
        Map<String, Object> itemAsignaturaSeccion;
        Map<String, Object> itemPeriodoAcademico;
        
        item = new HashMap<String, Object>();
        item.put("id",usuarioService.addRefIndirecta(asignaturaDictada.getId()));
        item.put("sigla",asignaturaDictada.getSigla());
        item.put("campus",asignaturaDictada.getCampus());
        item.put("creditos",asignaturaDictada.getCreditos());
        item.put("estudio",asignaturaDictada.getEstudio());
        item.put("nombre",asignaturaDictada.getNombre());
        item.put("seccionAcademica",asignaturaDictada.getSeccionAcademica());
        
        AsignaturaSeccion asignaturaSeccion=asignaturaDictada.getAsignaturaSeccion();
        itemAsignaturaSeccion = new HashMap<String, Object>();
        itemAsignaturaSeccion.put("idSeccion",usuarioService.addRefIndirecta(asignaturaSeccion.getIdSeccion()));
        itemAsignaturaSeccion.put("nombreSeccion",asignaturaSeccion.getNombreSeccion());
        itemAsignaturaSeccion.put("descripcion",asignaturaSeccion.getDescripcion());
        item.put("asignaturaSeccion", itemAsignaturaSeccion);
        
        List<Map<String, Object>> profesorList = new ArrayList<Map<String, Object>>();
        Map<String, Object> itemProfesor;
        for (Persona profesor : asignaturaDictada.getProfesorList()) {
            itemProfesor = new HashMap<String, Object>();
            itemProfesor.put("id", usuarioService.addRefIndirecta(profesor.getId()));
            itemProfesor.put("nombres", profesor.getNombres());
            itemProfesor.put("apellidoPaterno", profesor.getApellidoPaterno());
            itemProfesor.put("apellidoMaterno", profesor.getApellidoMaterno());     
            profesorList.add(itemProfesor);
        }
        item.put("profesorList", profesorList);
        
        itemPeriodoAcademico = new HashMap<String, Object>();
        itemPeriodoAcademico.put("nombre", asignaturaDictada.getPeriodoAcademico().getNombre());
        item.put("periodoAcademico", itemPeriodoAcademico);
       
        data.put("asignatura", item);
        
        data.put("countAlumnos", asignaturaDictadaService.countAlumnosAsignaturaDictada(asignaturaDictada));
        
        boolean isAlumno = asignaturaDictadaService.isAlumnoAsignatura
                (idAsignaturaDictada, idSeccion);
        data.put("isAlumno", isAlumno);
        
        return data;
    }
    
    @RequestMapping(value = "asignatura-horario.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getHorarioAsignatura(
            @RequestParam ("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam ("idSeccion") String idSeccionRandom){
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Map<String, Object> data = new HashMap<String, Object>();

        AsignaturaDictada asignaturaDictada=asignaturaDictadaService.getHorarioAsignaturaDictadaPregrado(idAsignaturaDictada, idSeccion);
        List<Map<String, Object>> listHorarioList;
        List<Map<String, Object>> listPracticaProgramadaList;
   
        Map<String, Object> item;
        Map<String, Object> itemAsignaturaSeccion;
        Map<String, Object> itemHorarioList;
        Map<String, Object> itemPracticaProgramadaList;
        item = new HashMap<String, Object>();
            item.put("id",usuarioService.addRefIndirecta(asignaturaDictada.getId()));
            item.put("sigla",asignaturaDictada.getSigla());
            AsignaturaSeccion asignaturaSeccion=asignaturaDictada.getAsignaturaSeccion();
            itemAsignaturaSeccion = new HashMap<String, Object>();
            itemAsignaturaSeccion.put("idSeccion",usuarioService.addRefIndirecta(asignaturaSeccion.getIdSeccion()));
            itemAsignaturaSeccion.put("nombreSeccion",asignaturaSeccion.getNombreSeccion());
            itemAsignaturaSeccion.put("descripcion",asignaturaSeccion.getDescripcion());
            item.put("asignaturaSeccion", itemAsignaturaSeccion);
            listHorarioList = new ArrayList<Map<String, Object>>();
            for (Horario horario : asignaturaDictada.getHorarioList()) {
                itemHorarioList = new HashMap<String, Object>();
                itemHorarioList.put("bloqueHorario", horario.getBloqueHorario());
                itemHorarioList.put("dia", horario.getDia());
                itemHorarioList.put("aula", horario.getAula());
                listHorarioList.add(itemHorarioList);   
            }
            item.put("horarioList",listHorarioList);
            listPracticaProgramadaList = new ArrayList<Map<String, Object>>();
            for (PracticaProgramada practica : asignaturaDictada.getPracticaProgramadaList()) {
                itemPracticaProgramadaList = new HashMap<String, Object>();
                itemPracticaProgramadaList.put("bloqueHorario", practica.getBloqueHorario());
                itemPracticaProgramadaList.put("dia", practica.getDia());
                itemPracticaProgramadaList.put("grupoPractica", practica.getGrupoPractica());
                listPracticaProgramadaList.add(itemPracticaProgramadaList);   
            }
            item.put("practicaProgramadaList",listPracticaProgramadaList);
        data.put("asignatura", item);
        
        return data;
    }
    
    @RequestMapping(value = "asignatura-material.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getMaterialAsignatura(
            @RequestParam ("idAsignaturaDictada") String idAsignaturaDictadaRandom,
            @RequestParam ("idSeccion") String idSeccionRandom){
        Integer idAsignaturaDictada = (Integer)usuarioService.getRefDirecta(idAsignaturaDictadaRandom);
        Integer idSeccion = (Integer)usuarioService.getRefDirecta(idSeccionRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        
        data.put("tipos", asignaturaDictadaService.getMaterialDisponibleList
                (idAsignaturaDictada, idSeccion));
        
        return data;
    }
}
