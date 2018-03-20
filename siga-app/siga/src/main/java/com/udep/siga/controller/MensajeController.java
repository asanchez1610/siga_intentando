package com.udep.siga.controller;

import com.udep.siga.bean.Mensaje;
import com.udep.siga.bean.PeriodoAcademico;
import com.udep.siga.bean.Persona;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.MensajeService;
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
@RequestMapping("/json/mensaje/*")
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private AlumnoEstudioService alumnoEstudioService;

    @RequestMapping(value = "inboxList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getInboxList(@RequestParam("all") Boolean all) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Mensajes");
        data.put("mensajes", mensajeService.getInbox(all));
        return data;
    }

    @RequestMapping(value = "outboxList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getOutboxList() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Mensajes");
        data.put("mensajes", mensajeService.getOutbox());
        return data;
    }

    @RequestMapping(value = "nuevos.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getMensajesNuevosList() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "CantidadMensajes");
        data.put("cantidadMensajes", mensajeService.getCantidadMensajesSinLeer());
        return data;
    }

    @RequestMapping(value = "marcarleido.json", method = RequestMethod.POST)
    public @ResponseBody
    Boolean marcarLeido(@RequestParam("id") int id, @RequestParam("fechaleido") String fechaLeido) {   
        if (fechaLeido.isEmpty()){
            mensajeService.updateLeido(id, true);
        }else{
            mensajeService.updateLeido(id, false);
        }
        return true;
    }

    @RequestMapping(value = "mensajeNuevo.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getAsesor() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Nuevo mensaje");
        data.put("asesor", alumnoEstudioService.getAsesorAlumno());
        return data;
    }

    @RequestMapping(value = "verifySaveMensaje.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> verifySaveMensaje(@RequestParam("asunto") String asunto, 
        @RequestParam("cuerpo") String cuerpo, @RequestParam("idasesor") Integer idAsesor,
        @RequestParam("idperiodoacademicoasesor") Integer idPeriodoAcademicoAsesor) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Errores");
        Mensaje mensaje = new Mensaje(new Persona(idAsesor), asunto, cuerpo);
        mensaje.setPeriodoAcademico(new PeriodoAcademico(idPeriodoAcademicoAsesor));
        data.put("errores", mensajeService.saveMensaje(mensaje));
        return data;
    }
}
