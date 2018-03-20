package com.udep.siga.controller;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Solicitud;
import com.udep.siga.bean.TipoSolicitud;
import com.udep.siga.bean.enumeration.EstadoSolicitud;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.TramiteAcademicoService;
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

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
@RequestMapping("/json/tramiteacademico/*")
public class TramiteAcademicoController {

    @Autowired
    private TramiteAcademicoService tramiteAcademicoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CommonsService commonsService;
    
    @RequestMapping(value = "pendientesList.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getPendientesList(
            @RequestParam("idEdicionestudio") String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Trámites académicos");
        data.put("moroso", usuarioService.isAlumnoMoroso(alumnoEstudio));
        data.put("estadoFin", EstadoSolicitud.SOLICITUD_FINALIZADA.getNombre());
        data.put("solicitudes", tramiteAcademicoService.getPendientesList(idEdicionestudio));
        return data;
    }
    
    @RequestMapping(value = "finalizadosList.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getFinalizadosList(
            @RequestParam("idEdicionestudio") String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Trámites finalizados");
        List<Solicitud> list = tramiteAcademicoService.getFinalizadosList(idEdicionestudio);        
        data.put("estadoFin", EstadoSolicitud.SOLICITUD_FINALIZADA.getNombre());
        data.put("moroso", usuarioService.isAlumnoMoroso(alumnoEstudio));
        data.put("solicitudes", list);
        return data;
    }
    
    @RequestMapping(value = "nuevo.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> nuevo(){
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Nuevo trámite");
        data.put("categorias", tramiteAcademicoService.getCategoriaSolicitudList());
        return data;
    }
    
    @RequestMapping(value = "saveTramite.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> saveTramite(
            @RequestParam("cuerpo") String cuerpo, @RequestParam("solicitud") Integer idTipoSolicitud,
            @RequestParam("idedicionestudio") String idEdicionestudioRandom){
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Errores");
        Solicitud solicitud = new Solicitud();
        solicitud.setTipoSolicitud(new TipoSolicitud(idTipoSolicitud));
        solicitud.setObsAlumno(cuerpo);
        data.put("errores", tramiteAcademicoService.saveTramiteAcademico(solicitud, idEdicionestudio));
        return data;
    }
}
