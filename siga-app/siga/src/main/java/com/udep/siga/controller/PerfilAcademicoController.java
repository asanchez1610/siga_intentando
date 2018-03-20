package com.udep.siga.controller;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AlumnoEstudioPeriodoAcademico;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.CLCCategoriaAlumno;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.Especialidad;
import com.udep.siga.bean.PeriodoAcademico;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.PlanEstudio;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.AsignaturaDictadaService;
import com.udep.siga.service.CLCService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.EspecialidadService;
import com.udep.siga.service.MensajeService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.Rol;
import com.udep.siga.util.SessionConstants;
import com.udep.siga.util.Util;
import com.udep.siga.util.UtilReplacePlantilla;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
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
@RequestMapping("/json/perfilacademico/*")
public class PerfilAcademicoController {

    @Autowired
    private CommonsService commonsService;
    @Autowired
    private AlumnoEstudioService alumnoEstudioService;
    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private AsignaturaDictadaService asignaturaDictadaService;
    @Autowired
    private CLCService clcService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private EspecialidadService especialidadService;
    private static Logger log = Logger.getLogger(PerfilAcademicoController.class);

    @RequestMapping(value = "perfilAcademico.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPerfilAcademico(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        usuarioService.setGeneralSession(SessionConstants.ID_EDICIONESTUDIO, idEdicionestudioRandom);
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Mi perfil académico");
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        
        Especialidad especialidad = especialidadService.getEspecialidadByAlumno(idEdicionestudio);
        String especialidadAlumno = "";
        
        if(especialidad != null){
            especialidadAlumno = String.format("(%s)", especialidad.getNombre());
        }
        
        
        if(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol())){
            alumnoEstudio.setPeriodoAcademicoVigente(null);
        }

        data.put("alumnoEstudio", alumnoEstudio);
        
        Map<String,Object> datosAlumnoEstudio = alumnoEstudioService.infoAlumnoEstudio(alumnoEstudio);
        data.put("colegioProcedencia", datosAlumnoEstudio.get("colegio"));
        data.put("periodoIngreso", datosAlumnoEstudio.get("periodoIngreso"));
        data.put("moroso", Boolean.parseBoolean(datosAlumnoEstudio.get("moroso").toString()));
        

        if (alumnoEstudio.getPeriodoAcademicoVigente() != null) {
            String becado = alumnoEstudioService.getTipoBeca(alumnoEstudio);
            data.put("anulaCiclo", alumnoEstudioService.anuloCicloByAlumnoEstudioPeriodoAcademico(idEdicionestudio));
            data.put("becado", becado.trim().length()>0?becado:"No");
            data.putAll(alumnoEstudioService.isReincorporado(alumnoEstudio));
        } else {
            data.put("becado", "-");
            data.put("anulaCiclo", false);
            data.put("reincorporado", false);
            data.put("condicion", "");
        }
        
        PlanEstudio planEstudio = alumnoEstudioService.getPlanEstudioAlumnoEstudioActivoByEstudio(idEdicionestudio);
        data.put("planEstudio", "-");
        if (planEstudio != null) {
            data.put("planEstudio", planEstudio.getNombre());
        }
        Persona asesor = alumnoEstudioService.getAsesorAlumno();

        data.put("asesorEmail", null);
        data.put("asesor", null);
        if (asesor != null) {
            Map<String, Object> itemAsesor = new HashMap<String, Object>();
            itemAsesor.put("id", usuarioService.addRefIndirecta(asesor.getId()));
            itemAsesor.put("nombres", asesor.getNombres());
            itemAsesor.put("apellidoPaterno", asesor.getApellidoPaterno());
            itemAsesor.put("apellidoMaterno", asesor.getApellidoMaterno());
            data.put("asesor", itemAsesor);

            List<Email> asesorEmailList = mensajeService.getEmailList(asesor.getId());
            if (!asesorEmailList.isEmpty()) {
                data.put("asesorEmail", asesorEmailList.get(0).getEmail());
            } else {
                data.put("asesorEmail", null);
            }
        }
        
        data.put("especialidad", especialidadAlumno);
        return data;
    }

    @RequestMapping(value = "periodoActual.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getPeriodoActual(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        List<Map<String, Object>> listProfesorList;
        Map<String, Object> itemProfesor;
        List<Map<String, Object>> listAsignaturaDictadaList;
        Map<String, Object> itemAsignaturaDictada;
        Map<String, Object> itemAsignaturaSeccion;

        data.put("title", "Mi perfil académico");
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        data.put("periodo", alumnoEstudio.getPeriodoAcademicoVigente().getNombre());
//        data.put("moroso", alumnoEstudio.isMoroso());
        data.put("moroso", usuarioService.isAlumnoMoroso(alumnoEstudio));
        List<AsignaturaDictada> asignaturaList = asignaturaDictadaService.getAsignaturaDictadaList(idEdicionestudio,usuarioService.isAlumnoMoroso(alumnoEstudio));
        listAsignaturaDictadaList = new ArrayList<Map<String, Object>>();
        for (AsignaturaDictada asignaturaDictada : asignaturaList) {
            itemAsignaturaDictada = new HashMap<String, Object>();
            itemAsignaturaDictada.put("id", usuarioService.addRefIndirecta(asignaturaDictada.getId()));
            itemAsignaturaDictada.put("nombre", asignaturaDictada.getNombre());
            itemAsignaturaDictada.put("creditos", asignaturaDictada.getCreditos());
            itemAsignaturaDictada.put("promedio","No Disponible");
            if(!usuarioService.isAlumnoMoroso(alumnoEstudio)){
            itemAsignaturaDictada.put("promedio", asignaturaDictada.getPromedio());
            }
            itemAsignaturaDictada.put("periodoAcademico", asignaturaDictada.getPeriodoAcademico());
            itemAsignaturaDictada.put("retiroCurso", asignaturaDictada.isRetiroCurso());
            itemAsignaturaDictada.put("seccionAcademica", asignaturaDictada.getSeccionAcademica());
            itemAsignaturaDictada.put("seccionAcademicaId", asignaturaDictada.getSeccionAcademicaId());
            itemAsignaturaDictada.put("sigla", asignaturaDictada.getSigla());
            itemAsignaturaDictada.put("tipoAsignatura", asignaturaDictada.getTipoAsignatura());
            itemAsignaturaDictada.put("totalMatriculado", asignaturaDictada.getTotalMatriculado());


            itemAsignaturaSeccion = new HashMap<String, Object>();
            itemAsignaturaSeccion.put("idSeccion", usuarioService.addRefIndirecta(asignaturaDictada.getAsignaturaSeccion().getIdSeccion()));
            itemAsignaturaSeccion.put("descripcion", asignaturaDictada.getAsignaturaSeccion().getDescripcion());
            itemAsignaturaSeccion.put("nombreSeccion", asignaturaDictada.getAsignaturaSeccion().getNombreSeccion());

            itemAsignaturaDictada.put("asignaturaSeccion", itemAsignaturaSeccion);

            listProfesorList = new ArrayList<Map<String, Object>>();
            for (Persona profesor : asignaturaDictadaService.findProfesores(asignaturaDictada)) {
                itemProfesor = new HashMap<String, Object>();
                itemProfesor.put("id", usuarioService.addRefIndirecta(profesor.getId()));
                itemProfesor.put("nombres", profesor.getNombres());
                itemProfesor.put("apellidoPaterno", profesor.getApellidoPaterno());
                listProfesorList.add(itemProfesor);
            }
            itemAsignaturaDictada.put("profesorList", listProfesorList);
            itemAsignaturaDictada.put("evaluacionList", asignaturaDictadaService.getEvaluacionesByAsignaturaDictada(asignaturaDictada.getId(), asignaturaDictada.getAsignaturaSeccion().getIdSeccion(),usuarioService.isAlumnoMoroso(alumnoEstudio)));

            listAsignaturaDictadaList.add(itemAsignaturaDictada);
        }
        data.put("asignaturas", listAsignaturaDictadaList);
        data.put("anulaCiclo", alumnoEstudioService.anuloCicloByAlumnoEstudioPeriodoAcademico(
                idEdicionestudio));
        return data;
    }

    @RequestMapping(value = "indicadores.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getIndicadores(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Mi perfil académico");        
        AlumnoEstudio aeSession = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)aeSession.clone();
        
        if(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol())){
            alumnoEstudio.setPeriodoAcademicoVigente(null);
        }
        
        data.put("idTipoEstudio", alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio().getId());

        PlanEstudio planEstudio = alumnoEstudioService.getPlanEstudioAlumnoEstudioActivoByEstudio(idEdicionestudio);
        data.put("planEstudio", "-");
        if (planEstudio != null) {
            data.put("planEstudio", planEstudio.getNombre());
        }

        data.put("periodoAcademicoVigente", alumnoEstudio.getPeriodoAcademicoVigente());
        PeriodoAcademico periodo = alumnoEstudioService.getPeriodoAnterior(alumnoEstudio, 1);
        if (periodo != null) {             
            AlumnoEstudioPeriodoAcademico aepa = alumnoEstudioService.getPeriodoAcadAlumnoEstudioByPeriodo(alumnoEstudio, periodo);
            boolean showCreditos = alumnoEstudioService.showAvance(alumnoEstudio.getCampus().getId(), alumnoEstudio.getEdicionestudio().getEstudio().getCentroAcademico().getId(), alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio().getId());
            if(!showCreditos){
                aepa.setCreditosTotalesMatriculados(0);
                aepa.setCreditosTotalesAprobados(0);
                aepa.setCreditosTotalesDesaprobados(0);
                aepa.setCreditosTotalesConvalidados(0);
                aepa.setCreditosTotalesCumplidos(0);
            }
            data.put("showCreditos", showCreditos);
            data.put("aepa", aepa);
            data.put("periodo", periodo.getNombre());
            data.put("ordenMerito", alumnoEstudioService.getOrdenMeritoAlumno(
                aepa.getOrdenMeritoTotal(), aepa.getOrdenMerito(), aepa.isQuinto(),
                aepa.isTercio()));
            data.put("rendimiento", alumnoEstudioService.getRendimientoAlumnoIndicadores(
                aepa.getCreditosTotalesAprobados(), aepa.getCreditosTotalesDesaprobados()));
        } else {
            data.put("idTipoEstudio",2);
            data.put("aepa", null);
            data.put("periodo", "");
            data.put("ordenMerito", "");
            data.put("rendimiento", "");
        }
        data.put("biperiodo", alumnoEstudioService.getBiPeriodoAnterior(alumnoEstudio,1));
        
        List gradoList = new ArrayList();
        Map<String, Object> grado = new HashMap<String, Object>();
        grado.put("titulo", "Bachiller");
        grado.put("requisitoList", alumnoEstudioService.getListRequisitosPlanEstudio(aeSession.getAlumno().getId(), aeSession.getEdicionestudio().getId()));
        gradoList.add(grado);
        grado = new HashMap<String, Object>();
        grado.put("titulo", "Título");
        grado.put("requisitoList", alumnoEstudioService.getListRequisitosTitulo(aeSession.getAlumno().getId(), aeSession.getEdicionestudio().getId()));
        gradoList.add(grado);
        data.put("grados", gradoList);

        return data;
    }

    @RequestMapping(value = "historial.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getHistorial(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        
        if(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || 
                        Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol())){
            alumnoEstudio.setPeriodoAcademicoVigente(null);
        }

        Map<String, Object> data = new HashMap<String, Object>();

        PlanEstudio planEstudio = alumnoEstudioService.getPlanEstudioAlumnoEstudioActivoByEstudio(idEdicionestudio);
        data.put("planEstudio", "-");
        if (planEstudio != null) {
            data.put("planEstudio", planEstudio.getNombre());
        }

        data.put("estudioActivo", true);
        if (alumnoEstudio.getPeriodoAcademicoVigente() == null) {
            data.put("estudioActivo", false);
        }

        List<AlumnoEstudioPeriodoAcademico> periodos = alumnoEstudioService.getEstudiosHistorialByAlumno(alumnoEstudio);
        data.put("title", "Mi perfil académico");        
        data.put("periodos",periodos );
        List<CLCCategoriaAlumno> list = clcService.getCategoriasByAlumnoList(alumnoEstudio);
        data.put("clcList", list);
        int countCLC = 0;
        if(list.size() > 0){            
            for (CLCCategoriaAlumno clc : list) {
                countCLC += clc.getClcObtenidos();
            }
        }
        data.put("clcObtenidosCount", countCLC);
        data.put("actividadArtisticaList", clcService
                .getCategActividadArtisticaDestacada(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadInvestigacionList", clcService
                .getCategActividadInvestigacion(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadProySocialAsistList", clcService
                .getCategActividadProyeSocialAsist(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadProySocialProfList", clcService
                .getCategActividadProyeSocialProf(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadDeportivaList", clcService
                .getCategActividadDeportivaDest(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadOtrosList", clcService
                .getClcOtrosPlanesDeEstudio(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadExtraConvenioList", clcService
                .getCategActividadExtraConvenio(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadDisneyList", clcService
                .getCategActividadDisney(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadIntercambioList", clcService
                .getCategAsigIntercambioEstud(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadCapacitProfList", clcService
                .getCategCapacitacionProfesional(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadEstudioDirigidoList", clcService
                .getCategEstudioDirigido(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadIdiomaList", clcService
                .getCategoriaIdioma(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));
        data.put("actividadPracticasList", clcService
                .getCategHorasPractProfesionales(alumnoEstudio.getAlumno().getId(), alumnoEstudio.getEdicionestudio().getId()));

        return data;
    }
    
    @RequestMapping(value = "descargaHistorialToPDF/{id}", method = RequestMethod.GET)
    public @ResponseBody String
            descargaHistorialToPDF(@PathVariable(value = "id") String idEdicionestudioRandom,
            HttpServletResponse response) {
        try {
            Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
            AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
            List<AlumnoEstudioPeriodoAcademico> periodos = alumnoEstudioService.getEstudiosHistorialByAlumno(alumnoEstudio);
            alumnoEstudio.setAlumno(alumnoEstudioService.getById(alumnoEstudio.getAlumno().getId()));
            UtilReplacePlantilla utils = new UtilReplacePlantilla();
            utils.generaHistorialToPdf(alumnoEstudio, periodos, response);  
        } catch (Exception e) {
            System.out.println("Error al descargar historial: " + e.getMessage());
        }
        return "ok";
    }
    
    /*
     * Grafico
     */
    @RequestMapping(value = "graficoRendimiento.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getGraficoRendimiento(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("datos", alumnoEstudioService.getPeriodosAcademicosAlumnoList(idEdicionestudio));
        return data;
    }
    
    @RequestMapping(value = "historial-idiomas.json", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> getHistorialIdiomas(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Map<String, Object> data = new HashMap<String, Object>();
        try{
            Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
            AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
            
            data.put("title", "Mi perfil académico"); 
            data.put("estudioActivo", true);
            if (alumnoEstudio.getPeriodoAcademicoVigente() == null) {
                data.put("estudioActivo", false);
            }

            data.put("historial_idiomas",alumnoEstudioService.obtenerHistorialIdiomas(alumnoEstudio.getCampus()).get("historial"));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return data;
    }
}
