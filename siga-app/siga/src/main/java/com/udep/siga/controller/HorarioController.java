package com.udep.siga.controller;

import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.AsignaturaSeccion;
import com.udep.siga.bean.Horario;
import com.udep.siga.bean.HorarioEvento;
import com.udep.siga.bean.PracticaProgramada;
import com.udep.siga.service.AsignaturaDictadaService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.Rol;
import com.udep.siga.util.Util;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
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
@RequestMapping("/json/horario/*")
public class HorarioController {

    @Autowired
    private AsignaturaDictadaService asignaturaDictadaService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @RequestMapping(value = "horario2.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getHorario2(@RequestParam(value = "id", required = false) Integer id,@RequestParam(value = "sufix", required = false) String sufix,HttpSession httpSession) {
        Map<String, Object> data = new HashMap<String, Object>();
         Util util = new Util();
        data.put("title", "Horario general");
        data.put("pregrado", Util.isUsuarioAuthority(Rol.ALUMNO_PREGRADO.getRol()));
        boolean isPosgrado =  Util.isUsuarioAuthority(Rol.ALUMNO_POSGRADO.getRol());
        
        data.put("posgrado", isPosgrado);
        if(isPosgrado){
            data.put("posgradoRegularList", asignaturaDictadaService.getHorarioPosgradoList(true));
            data.put("posgradoPracticaList", asignaturaDictadaService.getHorarioPosgradoList(false));
        }
        boolean ishorarioevento=asignaturaDictadaService.isHorarioevento();
        
        data.put("ishorarioevento",ishorarioevento);
        if(ishorarioevento){
            //sesion
           if(sufix.trim().equals("prev")){
                 data.put("sufix","next");
            }else {
                 
                 data.put("sufix","prev");
            }
            
           if(id!=null && id==0){
            httpSession.setAttribute("fecha",util.dateeventoToString());
           }
            data.put("listafechas",asignaturaDictadaService.getHorarioFechas(httpSession));
        }
        return data;
    } 
    

    @RequestMapping(value = "horario.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getHorario(HttpSession httpSession) {
        Map<String, Object> data = new HashMap<String, Object>();
         Util util = new Util();
        data.put("title", "Horario general");
        data.put("pregrado", Util.isUsuarioAuthority(Rol.ALUMNO_PREGRADO.getRol()));
        boolean isPosgrado =  Util.isUsuarioAuthority(Rol.ALUMNO_POSGRADO.getRol());
        
        data.put("posgrado", isPosgrado);
        if(isPosgrado){
            data.put("posgradoRegularList", asignaturaDictadaService.getHorarioPosgradoList(true));
            data.put("posgradoPracticaList", asignaturaDictadaService.getHorarioPosgradoList(false));
        }
        boolean ishorarioevento=asignaturaDictadaService.isHorarioevento();
        
        data.put("ishorarioevento",ishorarioevento);
        if(ishorarioevento){
            //sesion
           
            httpSession.setAttribute("fecha",util.dateeventoToString());
           
            data.put("listafechas",asignaturaDictadaService.getHorarioFechas(httpSession));
        }
        return data;
    } 
    
    @RequestMapping(value = "horarioList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getHorarioList(HttpSession httpSession) throws ParseException {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> listHorarioList;
        List<Map<String, Object>> listPracticaProgramadaList;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
         List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
          Util util = new Util();
        Map<String, Object> item;
        Map<String, Object> itemAsignaturaSeccion;
        Map<String, Object> itemHorarioList;
        Map<String, Object> itemPracticaProgramadaList;
         //verificar si es horario evento
          boolean ishorarioevento=asignaturaDictadaService.isHorarioevento();
        
        data.put("ishorarioevento",ishorarioevento);
         data.put("horarioList",Collections.EMPTY_LIST);
        data.put("asignaturaList",Collections.EMPTY_LIST);
        if(ishorarioevento){
            
            httpSession.setAttribute("fecha",util.dateeventoToString());
           
            List<HorarioEvento> horarioeventos=asignaturaDictadaService.getHorarioEventos(0,httpSession);
            
            if (horarioeventos != null) {

                for (HorarioEvento horario : horarioeventos) {
                    item = new HashMap<String, Object>();
                    item.put("id", usuarioService.addRefIndirecta(horario.getIdasignatura()));
                    item.put("sigla", horario.getSigla());
                    item.put("retiroCurso", horario.isRetirocurso());
                    item.put("idSeccion", usuarioService.addRefIndirecta(horario.getIdseccion()));
                    item.put("nombreSeccion", horario.getSeccion());
                    item.put("dia", horario.getIddia());
                    item.put("orden", horario.getIdhora());
                    item.put("aula", horario.getAmbiente());

                    list.add(item);
                }
                data.put("horarioList", list);
                
                //obtenemos las practicas de las asignaturas
                
               for (AsignaturaDictada asignaturaDictada : asignaturaDictadaService.getHorarioAsignaturaDictadaPregradoList()) {
            item = new HashMap<String, Object>();
            item.put("id", usuarioService.addRefIndirecta(asignaturaDictada.getId()));
            item.put("sigla", asignaturaDictada.getSigla());
            item.put("retiroCurso", asignaturaDictada.isRetiroCurso());
            AsignaturaSeccion asignaturaSeccion = asignaturaDictada.getAsignaturaSeccion();
            itemAsignaturaSeccion = new HashMap<String, Object>();
            itemAsignaturaSeccion.put("idSeccion", usuarioService.addRefIndirecta(asignaturaSeccion.getIdSeccion()));
            itemAsignaturaSeccion.put("nombreSeccion", asignaturaSeccion.getNombreSeccion());
            itemAsignaturaSeccion.put("descripcion", asignaturaSeccion.getDescripcion());
            item.put("asignaturaSeccion", itemAsignaturaSeccion);
            listHorarioList = new ArrayList<Map<String, Object>>();
            for (Horario horario : asignaturaDictada.getHorarioList()) {
                itemHorarioList = new HashMap<String, Object>();
                itemHorarioList.put("bloqueHorario", horario.getBloqueHorario());
                itemHorarioList.put("dia", horario.getDia().getId());
                itemHorarioList.put("aula", horario.getAula());
                listHorarioList.add(itemHorarioList);
            }
            item.put("horarioList", listHorarioList);
            
            listPracticaProgramadaList = new ArrayList<Map<String, Object>>();
            for (PracticaProgramada horario : asignaturaDictada.getPracticaProgramadaList()) {
                itemPracticaProgramadaList = new HashMap<String, Object>();
                itemPracticaProgramadaList.put("bloqueHorario", horario.getBloqueHorario());
                itemPracticaProgramadaList.put("dia", horario.getDia().getId());
                itemPracticaProgramadaList.put("grupoPractica", horario.getGrupoPractica());
                listPracticaProgramadaList.add(itemPracticaProgramadaList);
            }
            item.put("practicaProgramadaList", listPracticaProgramadaList);
            list2.add(item);
        }
      
        data.put("asignaturaList", list2);
            }
        }
        else {
        for (AsignaturaDictada asignaturaDictada : asignaturaDictadaService.getHorarioAsignaturaDictadaPregradoList()) {
            item = new HashMap<String, Object>();
            item.put("id", usuarioService.addRefIndirecta(asignaturaDictada.getId()));
            item.put("sigla", asignaturaDictada.getSigla());
            item.put("retiroCurso", asignaturaDictada.isRetiroCurso());
            AsignaturaSeccion asignaturaSeccion = asignaturaDictada.getAsignaturaSeccion();
            itemAsignaturaSeccion = new HashMap<String, Object>();
            itemAsignaturaSeccion.put("idSeccion", usuarioService.addRefIndirecta(asignaturaSeccion.getIdSeccion()));
            itemAsignaturaSeccion.put("nombreSeccion", asignaturaSeccion.getNombreSeccion());
            itemAsignaturaSeccion.put("descripcion", asignaturaSeccion.getDescripcion());
            item.put("asignaturaSeccion", itemAsignaturaSeccion);
            listHorarioList = new ArrayList<Map<String, Object>>();
            for (Horario horario : asignaturaDictada.getHorarioList()) {
                itemHorarioList = new HashMap<String, Object>();
                itemHorarioList.put("bloqueHorario", horario.getBloqueHorario());
                itemHorarioList.put("dia", horario.getDia().getId());
                itemHorarioList.put("aula", horario.getAula());
                listHorarioList.add(itemHorarioList);
            }
            item.put("horarioList", listHorarioList);
            listPracticaProgramadaList = new ArrayList<Map<String, Object>>();
            for (PracticaProgramada horario : asignaturaDictada.getPracticaProgramadaList()) {
                itemPracticaProgramadaList = new HashMap<String, Object>();
                itemPracticaProgramadaList.put("bloqueHorario", horario.getBloqueHorario());
                itemPracticaProgramadaList.put("dia", horario.getDia().getId());
                itemPracticaProgramadaList.put("grupoPractica", horario.getGrupoPractica());
                listPracticaProgramadaList.add(itemPracticaProgramadaList);
            }
            item.put("practicaProgramadaList", listPracticaProgramadaList);
            list.add(item);
        }
      
        data.put("asignaturaList", list);
        }
        return data;
    }
    
    @RequestMapping(value = "horarioList2.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getHorarioList2(@RequestParam(value = "id", required = false) Integer id,@RequestParam(value = "sufix", required = false) String sufix,HttpSession httpSession) throws ParseException {
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> listHorarioList;
        List<Map<String, Object>> listPracticaProgramadaList;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
         List<Map<String, Object>> list2 = new ArrayList<Map<String, Object>>();
          Util util = new Util();
        Map<String, Object> item;
        Map<String, Object> itemAsignaturaSeccion;
        Map<String, Object> itemHorarioList;
        Map<String, Object> itemPracticaProgramadaList;
         //verificar si es horario evento
          boolean ishorarioevento=asignaturaDictadaService.isHorarioevento();
        
        data.put("ishorarioevento",ishorarioevento);
        data.put("horarioList",Collections.EMPTY_LIST);
        data.put("asignaturaList",Collections.EMPTY_LIST);
        data.put("sufix","");
        if(ishorarioevento){
             if(sufix.trim().equals("prev")){
                 data.put("sufix","next");
            }else {
                 
                 data.put("sufix","prev");
            }
             if(id!=null && id==0){
            httpSession.setAttribute("fecha",util.dateeventoToString());
           }
            List<HorarioEvento> horarioeventos=asignaturaDictadaService.getHorarioEventos(id,httpSession);
            
            if (horarioeventos != null) {

                for (HorarioEvento horario : horarioeventos) {
                    item = new HashMap<String, Object>();
                    item.put("id", usuarioService.addRefIndirecta(horario.getIdasignatura()));
                    item.put("sigla", horario.getSigla());
                    item.put("retiroCurso", horario.isRetirocurso());
                    item.put("idSeccion", usuarioService.addRefIndirecta(horario.getIdseccion()));
                    item.put("nombreSeccion", horario.getSeccion());
                    item.put("dia", horario.getIddia());
                    item.put("orden", horario.getIdhora());
                    item.put("aula", horario.getAmbiente());

                    list.add(item);
                }
                data.put("horarioList", list);
                
                //obtenemos las practicas de las asignaturas
                
               for (AsignaturaDictada asignaturaDictada : asignaturaDictadaService.getHorarioAsignaturaDictadaPregradoList()) {
            item = new HashMap<String, Object>();
            item.put("id", usuarioService.addRefIndirecta(asignaturaDictada.getId()));
            item.put("sigla", asignaturaDictada.getSigla());
            item.put("retiroCurso", asignaturaDictada.isRetiroCurso());
            AsignaturaSeccion asignaturaSeccion = asignaturaDictada.getAsignaturaSeccion();
            itemAsignaturaSeccion = new HashMap<String, Object>();
            itemAsignaturaSeccion.put("idSeccion", usuarioService.addRefIndirecta(asignaturaSeccion.getIdSeccion()));
            itemAsignaturaSeccion.put("nombreSeccion", asignaturaSeccion.getNombreSeccion());
            itemAsignaturaSeccion.put("descripcion", asignaturaSeccion.getDescripcion());
            item.put("asignaturaSeccion", itemAsignaturaSeccion);
            listHorarioList = new ArrayList<Map<String, Object>>();
            for (Horario horario : asignaturaDictada.getHorarioList()) {
                itemHorarioList = new HashMap<String, Object>();
                itemHorarioList.put("bloqueHorario", horario.getBloqueHorario());
                itemHorarioList.put("dia", horario.getDia().getId());
                itemHorarioList.put("aula", horario.getAula());
                listHorarioList.add(itemHorarioList);
            }
            item.put("horarioList", listHorarioList);
            
            listPracticaProgramadaList = new ArrayList<Map<String, Object>>();
            for (PracticaProgramada horario : asignaturaDictada.getPracticaProgramadaList()) {
                itemPracticaProgramadaList = new HashMap<String, Object>();
                itemPracticaProgramadaList.put("bloqueHorario", horario.getBloqueHorario());
                itemPracticaProgramadaList.put("dia", horario.getDia().getId());
                itemPracticaProgramadaList.put("grupoPractica", horario.getGrupoPractica());
                listPracticaProgramadaList.add(itemPracticaProgramadaList);
            }
            item.put("practicaProgramadaList", listPracticaProgramadaList);
            list2.add(item);
        }
      
        data.put("asignaturaList", list2);
            }
        }
        else {
        for (AsignaturaDictada asignaturaDictada : asignaturaDictadaService.getHorarioAsignaturaDictadaPregradoList()) {
            item = new HashMap<String, Object>();
            item.put("id", usuarioService.addRefIndirecta(asignaturaDictada.getId()));
            item.put("sigla", asignaturaDictada.getSigla());
            item.put("retiroCurso", asignaturaDictada.isRetiroCurso());
            AsignaturaSeccion asignaturaSeccion = asignaturaDictada.getAsignaturaSeccion();
            itemAsignaturaSeccion = new HashMap<String, Object>();
            itemAsignaturaSeccion.put("idSeccion", usuarioService.addRefIndirecta(asignaturaSeccion.getIdSeccion()));
            itemAsignaturaSeccion.put("nombreSeccion", asignaturaSeccion.getNombreSeccion());
            itemAsignaturaSeccion.put("descripcion", asignaturaSeccion.getDescripcion());
            item.put("asignaturaSeccion", itemAsignaturaSeccion);
            listHorarioList = new ArrayList<Map<String, Object>>();
            for (Horario horario : asignaturaDictada.getHorarioList()) {
                itemHorarioList = new HashMap<String, Object>();
                itemHorarioList.put("bloqueHorario", horario.getBloqueHorario());
                itemHorarioList.put("dia", horario.getDia().getId());
                itemHorarioList.put("aula", horario.getAula());
                listHorarioList.add(itemHorarioList);
            }
            item.put("horarioList", listHorarioList);
            listPracticaProgramadaList = new ArrayList<Map<String, Object>>();
            for (PracticaProgramada horario : asignaturaDictada.getPracticaProgramadaList()) {
                itemPracticaProgramadaList = new HashMap<String, Object>();
                itemPracticaProgramadaList.put("bloqueHorario", horario.getBloqueHorario());
                itemPracticaProgramadaList.put("dia", horario.getDia().getId());
                itemPracticaProgramadaList.put("grupoPractica", horario.getGrupoPractica());
                listPracticaProgramadaList.add(itemPracticaProgramadaList);
            }
            item.put("practicaProgramadaList", listPracticaProgramadaList);
            list.add(item);
        }
      
        data.put("asignaturaList", list);
        }
        return data;
    }
}
