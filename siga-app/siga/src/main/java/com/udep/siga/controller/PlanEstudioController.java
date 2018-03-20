package com.udep.siga.controller;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Especialidad;
import com.udep.siga.bean.PlanEstudio;
import com.udep.siga.bean.PlanEstudioAsignatura;
import com.udep.siga.bean.enumeration.TipoAsignatura;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.EspecialidadService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.SessionConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
@RequestMapping("/planestudio/*")
public class PlanEstudioController {

    @Autowired
    private AlumnoEstudioService alumnoEstudioService;     
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EspecialidadService especialidadService;
    @Autowired
    private MessageSource messageSource;
    
    /*
     * Página de Avance de Plan
     */
    @RequestMapping(value = "/avancePlan.htm", method = RequestMethod.GET)
    public ModelAndView avancePlan(
            @RequestParam(value = "id", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);        
        ModelAndView mav = new ModelAndView(); 
        AlumnoEstudio alumnoEstudio = alumnoEstudioService.getAlumnoEstudio(idEdicionestudio);
        PlanEstudio planEstudio = alumnoEstudioService.getPlanEstudioAlumnoEstudioActivoByEstudio(idEdicionestudio);
        Map<String, Object> datosOtros = new HashMap<String, Object>();
        Map<String, Object> obligatoria = alumnoEstudioService.getPlanEstudioAsignaturaEstado(
                alumnoEstudio.getAlumno().getId(), planEstudio.getId(), TipoAsignatura.OBLIGATORIA);
        Map<String, Object> electiva = alumnoEstudioService.getPlanEstudioAsignaturaEstado(
                alumnoEstudio.getAlumno().getId(), planEstudio.getId(), TipoAsignatura.ELECTIVA);
        Map<String, Object> libre = alumnoEstudioService.getPlanEstudioAsignaturaEstado(
                alumnoEstudio.getAlumno().getId(), planEstudio.getId(), TipoAsignatura.LIBRE_CONFIGURACION);        
        
        mav.addObject("planNombre", planEstudio.getNombre());
        mav.addObject("estudioNombre", alumnoEstudio.getEdicionestudio().getEstudio().getNombre());
        mav.addObject("obligatoria",obligatoria );
        mav.addObject("electiva",electiva );
        mav.addObject("libre", libre );
        
        int otrosObligatoria = alumnoEstudioService.getCreditosActuales(alumnoEstudio, TipoAsignatura.OBLIGATORIA.getId());
        int CredObligatoria = (Integer) obligatoria.get("credCumplidos");
        int otrosElectiva = alumnoEstudioService.getCreditosActuales(alumnoEstudio, TipoAsignatura.ELECTIVA.getId());
        int CredElectiva = (Integer) electiva.get("credCumplidos");
        int otrosLibre = alumnoEstudioService.getCreditosActuales(alumnoEstudio, TipoAsignatura.LIBRE_CONFIGURACION.getId());
        int CredLibre = (Integer) libre.get("credCumplidos");
        
        datosOtros.put("obligatoria", 0);
        datosOtros.put("electiva", 0);
        datosOtros.put("libre", 0);
        if ( otrosObligatoria > CredObligatoria) {
            datosOtros.put("obligatoria", otrosObligatoria - CredObligatoria );
        }
        if ( otrosElectiva > CredElectiva) {
            datosOtros.put("electiva", otrosElectiva - CredElectiva );
        }
        //if(CredObligatoria + CredElectiva + CredLibre > otrosObligatoria + otrosElectiva){
            if ( otrosLibre > CredLibre) {
                datosOtros.put("libre", otrosLibre - CredLibre );
            }
        //}
        
        mav.addObject("otros", datosOtros);
        mav.setViewName("planestudio.avanceplan");
        
        return mav;
    }
    
    /*
     * Página de Malla
     */
    @RequestMapping(value = "/malla.htm", method = RequestMethod.GET)
    public ModelAndView malla(@RequestParam(value = "id", required = false) String idEdicionestudioRandom) {
        if( idEdicionestudioRandom == null ){
            idEdicionestudioRandom = (String)usuarioService.getGeneralSession(SessionConstants.ID_EDICIONESTUDIO);
        }
        
        Integer idEdicionEstudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        
        if( especialidadService.checkEspecialidadConfig(idEdicionEstudio) ){
            return new ModelAndView("redirect:./eleccionEspecialidad.htm?id="+idEdicionestudioRandom);
        }
        
        ModelAndView mav = new ModelAndView();   
        mav.setViewName("planestudio.malla");
        
        if( usuarioService.getGeneralSession(SessionConstants.MENSAJE) != null ){
            mav.addObject("mensaje", usuarioService.getGeneralSession(SessionConstants.MENSAJE));
            usuarioService.removeGeneralSession(SessionConstants.MENSAJE);
        }
        
        return mav;
    }
    
    /*
     * Elección de especialidad
     */
    @RequestMapping(value = "/eleccionEspecialidad.htm", method = RequestMethod.GET)
    public ModelAndView elegirEspecialidad( @RequestParam(value = "id", required = false) String idEdicionestudioRandom, 
                                            @RequestParam(value = "fl", required = false) boolean fromlogin) {
        if( idEdicionestudioRandom == null ){
            idEdicionestudioRandom = (String)usuarioService.getGeneralSession(SessionConstants.ID_EDICIONESTUDIO);
        }
        
        Integer idEdicionEstudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);     
        
        if ( especialidadService.checkEspecialidadEleccion(idEdicionEstudio) || !especialidadService.checkEspecialidadConfig(idEdicionEstudio) ){
            return new ModelAndView("redirect:./malla.htm?id="+idEdicionestudioRandom);
        }
        
        ModelAndView mav = new ModelAndView();   
        mav.setViewName("planestudio.eleccion.especialidad");
        
        AlumnoEstudio alumnoEstudio = alumnoEstudioService.getAlumnoEstudio(idEdicionEstudio);
        PlanEstudio planEstudio = alumnoEstudioService.getPlanEstudioAlumnoEstudioActivoByEstudio(idEdicionEstudio);
        
        mav.addObject("titulo", String.format("%s - Plan de Estudios: %s", alumnoEstudio.getEdicionestudio().getEstudio().getNombre(), planEstudio.getNombre()));
        mav.addObject("especialidades", especialidadService.getEspecialidadByPlanEstudio(planEstudio.getId()));
        mav.addObject("numPeriodos", planEstudio.getNumPeriodos());
        mav.addObject("idEdicionEstudio", idEdicionestudioRandom);
        mav.addObject("idPlanEstudio", usuarioService.addRefIndirecta(planEstudio.getId()));
        mav.addObject("configuracionMensaje", especialidadService.getEspecialidadConfigMensaje(idEdicionEstudio));
        mav.addObject("fromlogin", fromlogin);
        
        if( usuarioService.getGeneralSession(SessionConstants.MENSAJE) != null ){
            mav.addObject("mensaje", usuarioService.getGeneralSession(SessionConstants.MENSAJE));
            usuarioService.removeGeneralSession(SessionConstants.MENSAJE);
        }
        
        return mav;
    }
    
    /*
     * Malla
     */
    @RequestMapping(value = "cargarMalla.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> cargarMalla(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        AlumnoEstudio alumnoEstudio = alumnoEstudioService.getAlumnoEstudio(idEdicionestudio);
        PlanEstudio planEstudio = alumnoEstudioService.getPlanEstudioAlumnoEstudioActivoByEstudio(idEdicionestudio);
        data.put("planNombre", planEstudio.getNombre());
        data.put("estudioNombre", alumnoEstudio.getEdicionestudio().getEstudio().getNombre());
        data.put("numPeriodos", planEstudio.getNumPeriodos());
        data.put("areaConocimientosList", alumnoEstudioService.getAreasDeConocimiento(
                planEstudio.getId(), idEdicionestudio));
        return data;
    }
    
    /**
     * Malla Especialidad
     * @param idEdicionEstudioRandom
     * @param idPlanEstudioRandom
     * @param idEspecialidadRandom
     * @return 
     */
    @RequestMapping(value = "cargarMallaEspecialidad.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> cargarMallaEspecialidad(@RequestParam(value = "idEdicionEstudio", required = true) String idEdicionEstudioRandom,
                                                                     @RequestParam(value = "idPlanEstudio"   , required = true) String idPlanEstudioRandom,
                                                                     @RequestParam(value = "idEspecialidad"  , required = true) String idEspecialidadRandom){
        Integer idEdicionEstudio    = (Integer) usuarioService.getRefDirecta(idEdicionEstudioRandom);
        Integer idPlanEstudio       = (Integer) usuarioService.getRefDirecta(idPlanEstudioRandom);
        Integer idEspecialidad      = (Integer) usuarioService.getRefDirecta(idEspecialidadRandom);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("areaConocimientosList", especialidadService.getAreasConocimientoByEspecialidad(idPlanEstudio, idEspecialidad, idEdicionEstudio));
        return data;
    }
    
    @RequestMapping(value = "guardarEspecialidad", method = RequestMethod.POST)
    public ModelAndView guardarEspecialidadByAlumno(@RequestParam(value = "idEdicionEstudio", required = true) String idEdicionEstudioRandom,
                                                    @RequestParam(value = "idEspecialidad"  , required = true) String idEspecialidadRandom,
                                                    @RequestParam(value = "fl"              , required = false) boolean fromlogin,
                                                    HttpServletRequest request){
        
        Integer idEdicionEstudio    = (Integer) usuarioService.getRefDirecta(idEdicionEstudioRandom);
        Integer idEspecialidad      = (Integer) usuarioService.getRefDirecta(idEspecialidadRandom);
        
        boolean resp = especialidadService.guardarEspecialidadAlumno(idEdicionEstudio, idEspecialidad);
        
        if(resp){
            Especialidad especialidad = especialidadService.getEspecialidadByAlumno(idEdicionEstudio);
            usuarioService.setGeneralSession(SessionConstants.MENSAJE, messageSource.getMessage("especialidad.eleccion.ok", new Object[] { especialidad.getNombre() }, Locale.getDefault()));
            if(fromlogin){
                return new ModelAndView("redirect:./");
            }
            return new ModelAndView("redirect:./malla.htm?id="+idEdicionEstudioRandom);
        }else{
            usuarioService.setGeneralSession(SessionConstants.MENSAJE, messageSource.getMessage("especialidad.eleccion.error", null, Locale.getDefault()));
            if(fromlogin){
                return new ModelAndView("redirect:./");
            }
            return new ModelAndView("redirect:./eleccionEspecialidad.htm?id="+idEdicionEstudioRandom);
        }
    }
    
    @RequestMapping(value = "cargarDataAsignatura.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> cargarDataAsignatura(@RequestParam(value = "idEdicionEstudio"  , required = true) String idEdicionEstudioRandom,
                                                                  @RequestParam(value = "idPlanEstudio"     , required = true) String idPlanEstudioRandom,
                                                                  @RequestParam(value = "idEspecialidad"    , required = true) String idEspecialidadRandom,
                                                                  @RequestParam(value = "idAreaConocimiento", required = true) int idAreaConocimiento,
                                                                  @RequestParam(value = "idAsignatura"      , required = true) int idAsignatura){
        Integer idEdicionEstudio    = (Integer) usuarioService.getRefDirecta(idEdicionEstudioRandom);
        Integer idPlanEstudio       = (Integer) usuarioService.getRefDirecta(idPlanEstudioRandom);
        Integer idEspecialidad      = (Integer) usuarioService.getRefDirecta(idEspecialidadRandom);
                
        PlanEstudioAsignatura planEstudioAsignatura = alumnoEstudioService.findAsignaturaById_AreaConocimientoPlanEstudioIdEspecialidad(idAreaConocimiento, idPlanEstudio, idAsignatura, idEspecialidad, idEdicionEstudio);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("data", planEstudioAsignatura);
        return data;
    }
}
