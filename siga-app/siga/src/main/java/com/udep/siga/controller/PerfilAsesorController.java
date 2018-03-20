package com.udep.siga.controller;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AsesorSugerido;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.enumeration.Configuracion;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.MensajeService;
import com.udep.siga.service.ProfesorService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.Rol;
import com.udep.siga.util.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.WordUtils;
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
@RequestMapping("/json/asesor/*")
public class PerfilAsesorController {

    @Autowired
    private AlumnoEstudioService alumnoEstudioService;
    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ProfesorService profesorService;
    @Autowired
    private CommonsService commonsService;

    @RequestMapping(value = "perfil.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> perfil(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, Object> itemAsesor;
        data.put("title", "Mi asesor");
        Persona asesor = alumnoEstudioService.getAsesorAlumno();

        if (asesor != null) {
            itemAsesor = new HashMap<String, Object>();
            itemAsesor.put("id", usuarioService.addRefIndirecta(asesor.getId()));
            itemAsesor.put("nombres", asesor.getNombres());
            itemAsesor.put("apellidoPaterno", asesor.getApellidoPaterno());
            itemAsesor.put("apellidoMaterno", asesor.getApellidoMaterno());
            itemAsesor.put("foto", asesor.getFoto());
            data.put("asesor", itemAsesor);
            DatoPersonal datoPersonal = usuarioService.getDatoPersonal(asesor.getId());
            Map<String, Object> infoAcademica = profesorService.getInfoAcademico(asesor.getId());
            
            data.put("fechaCumpleanios", "-");
            if (datoPersonal != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM", new Locale("ES"));
                if (datoPersonal.getFechaNacimiento() != null) {
                    data.put("fechaCumpleanios", WordUtils.capitalize(dateFormat.format(datoPersonal.getFechaNacimiento())));
                } 
            }
            List<Email> asesorEmailList = mensajeService.getEmailList(asesor.getId());
            if (!asesorEmailList.isEmpty()) {
                data.put("asesorEmail", asesorEmailList.get(0).getEmail());
            } else {
                data.put("asesorEmail", null);
            }

            data.putAll(infoAcademica);

            if (infoAcademica.get("oficina") == null) {
                data.put("oficina", "");
            }
            if (infoAcademica.get("edificio") == null) {
                data.put("edificio", "");
            }
            if (infoAcademica.get("anexo") == null) {
                data.put("anexo", "");
            }
            if (infoAcademica.get("departamento") == null) {
                data.put("departamento", "");
            }
            if (infoAcademica.get("centroacademico") == null) {
                data.put("centroacademico", "");
            }
        } else {
            data.put("asesor", null);
        }
        data.put("isSugeridos", false);
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null && alumnoEstudio.getCampus() != null
                && alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio() != null ){             
            if(alumnoEstudioService.isPregrado(alumnoEstudio) && 
                    usuarioService.getConfiguracion(Configuracion.ASESOR_SUGERIDO_ACTIVO).equals("SI")
                    && !alumnoEstudioService.isCachimbo(alumnoEstudio)){
                data.put("isSugeridos", true);
            }
        }    
        return data;
    }

    @RequestMapping(value = "horario.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> horario() {
        Map<String, Object> data = new HashMap<String, Object>();
        Persona asesor = alumnoEstudioService.getAsesorAlumno();
        List<Map<String, Object>> asignaturaList = new ArrayList<Map<String, Object>>();
        if (asesor != null) {
            for (AsignaturaDictada asignaturaDictada : profesorService.getHorarioList(asesor.getId())) {
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

                item.put("horarioList", asignaturaDictada.getHorarioList());
                item.put("practicaProgramadaList", asignaturaDictada.getPracticaProgramadaList());

                asignaturaList.add(item);
            }
            data.put("asignaturaList", asignaturaList);
            data.put("asesoriaList", profesorService.getHorarioAsesoriaList(asesor.getId()));
        }
        
        return data;
    }

    @RequestMapping(value = "entrevistas.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> entrevistas(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Mi asesor");
        Persona asesor = alumnoEstudioService.getAsesorAlumno();
        if (asesor != null) {

            data.put("id", usuarioService.addRefIndirecta(asesor.getId()));
            data.put("nombre", String.format("%s %s, %s", asesor.getApellidoPaterno(), asesor.getApellidoMaterno(),
                    asesor.getNombres()));

            Alumno alumno = usuarioService.getInfoUsuario();

            data.put("historialList", profesorService.getAsesoriaInteraccionList(alumno.getId(), asesor.getId()));
            data.put("pendienteList", profesorService.getAsesoriaFuturaList(alumno.getId(), asesor.getId()));
        }
        data.put("isSugeridos", false);
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null && alumnoEstudio.getCampus() != null
                && alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio() != null ){ 
            if(alumnoEstudioService.isPregrado(alumnoEstudio) && 
                    usuarioService.getConfiguracion(Configuracion.ASESOR_SUGERIDO_ACTIVO).equals("SI")
                    && !alumnoEstudioService.isCachimbo(alumnoEstudio)){
                data.put("isSugeridos", true);
            }
        }  
        return data;
    }
    
    @RequestMapping(value = "sugeridos.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sugeridos(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        
        Map<String, Object> data = new HashMap<String, Object>();
        Persona asesor = alumnoEstudioService.getAsesorAlumno();
        
        boolean isSugerido = false;
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null && alumnoEstudio.getCampus() != null
                && alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio() != null ){  
            if(alumnoEstudioService.isPregrado(alumnoEstudio) && 
                    usuarioService.getConfiguracion(Configuracion.ASESOR_SUGERIDO_ACTIVO).equals("SI")
                    && !alumnoEstudioService.isCachimbo(alumnoEstudio)){
                isSugerido = true;
            }
        }
        data.put("isSugeridos", isSugerido);
        
        data.put("title", "Selección de asesores");
        data.put("periodo", "");
        data.put("idAsesor", "");
        data.put("nombreAsesor", "");
        data.put("fotoAsesor", "");
        data.put("sugeridosList",null);
        if(isSugerido){
            if (asesor != null) {
                data.put("idAsesor", usuarioService.addRefIndirecta(asesor.getId()));
                data.put("nombreAsesor", String.format("%s %s, %s", asesor.getApellidoPaterno(), asesor.getApellidoMaterno(),
                        asesor.getNombres()));            
                data.put("fotoAsesor", asesor.getFoto());
            }
            if(alumnoEstudio.getPeriodoAcademicoVigente() != null){
                data.put("periodo", alumnoEstudio.getPeriodoAcademicoVigente().getNombre());
            }
            data.put("sugeridosList", alumnoEstudioService.getSugeridosList(alumnoEstudio.getAlumno().getId(), 
                    alumnoEstudio.getPeriodoAcademicoVigente().getId()) );

        }
        return data;
    }
    
    @RequestMapping(value = "sugeridosupdated.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sugeridos_updated(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        
        Map<String, Object> data = new HashMap<String, Object>();
        Persona asesor = alumnoEstudioService.getAsesorAlumno();
        
        boolean isSugerido = true;
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null && alumnoEstudio.getCampus() != null
                && alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio() != null ){  
            if(alumnoEstudioService.isPregrado(alumnoEstudio) && 
                    usuarioService.getConfiguracion(Configuracion.ASESOR_SUGERIDO_ACTIVO).equals("SI")
                    && !alumnoEstudioService.isCachimbo(alumnoEstudio)){
                isSugerido = true;
            }
        }
        data.put("isSugeridos", isSugerido);
        
        data.put("title", "Selección de asesores");
        data.put("periodo", "");
        data.put("idAsesor", "");
        data.put("nombreAsesor", "");
        data.put("fotoAsesor", "");
        data.put("sugeridosList",null);
        if(isSugerido){
            if (asesor != null) {
                data.put("idAsesor", usuarioService.addRefIndirecta(asesor.getId()));
                data.put("nombreAsesor", String.format("%s %s, %s", asesor.getApellidoPaterno(), asesor.getApellidoMaterno(),
                        asesor.getNombres()));            
                data.put("fotoAsesor", asesor.getFoto());
            }
            if(alumnoEstudio.getPeriodoAcademicoVigente() != null){
                data.put("periodo", alumnoEstudio.getPeriodoAcademicoVigente().getNombre());
            }
            data.put("sugeridosList", alumnoEstudioService.getSugeridosList(alumnoEstudio.getAlumno().getId(), 
                    alumnoEstudio.getPeriodoAcademicoVigente().getId()) );

        }
        return data;
    }
    
    @RequestMapping(value = "saveAsesorSugerido.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveAsesorSugerido(
            @RequestParam("sugerido_1") String idSugerido_1Random,
            @RequestParam("sugerido_2") String idSugerido_2Random,
            @RequestParam("idedicionestudio") String idEdicionestudioRandom) {
        
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        Integer idSugerido_1 = (Integer)usuarioService.getRefDirecta(idSugerido_1Random);
        Integer idSugerido_2 = (Integer)usuarioService.getRefDirecta(idSugerido_2Random);
        if(idSugerido_1== null)idSugerido_1=0;
        if(idSugerido_2== null)idSugerido_2=0;
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        ArrayList<String> errores = new ArrayList<String>();
        if(idSugerido_1 == idSugerido_2){
            errores.add("No pueden haber asesores repetidos");
        }
        if(idSugerido_1 == 0 || idSugerido_2 == 0){
            errores.add("Selecciones un asesor válido");
        }
        if(errores.isEmpty()){
            AsesorSugerido asesores = new AsesorSugerido();
            asesores.setAsesor_sugerido_1(new Persona(idSugerido_1));
            asesores.setAsesor_sugerido_2(new Persona(idSugerido_2));
            alumnoEstudioService.saveAsesorSugerido
                    (alumnoEstudio.getAlumno().getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId(), asesores);
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("errores", errores);
        return data;
    }
    
    @RequestMapping(value = "getFoto.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getFoto(@RequestParam(value = "idAsesorSugerido", required = true) String idAsesorRandom) {
         Map<String, Object> data = new HashMap<String, Object>();
        if(idAsesorRandom.equals("0")){
            data.put("fotoAsesor", AppConstants.PATH_FOTO_MASCULINO_DEFAULT);
            data.put("title", "Selección de asesores");
            return data;
        }
        Integer idAsesor = (Integer)usuarioService.getRefDirecta(idAsesorRandom);
        Persona asesor = usuarioService.getPersonaById(idAsesor);
        
        data.put("title", "Selección de asesores");
        data.put("fotoAsesor", "");
        if (asesor != null) {          
            data.put("fotoAsesor", asesor.getFoto());
        }
        
        return data;
    }
     @RequestMapping(value = "yavoto.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getYaVoto(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom)  {
       Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        boolean Pregrado=alumnoEstudioService.isPregrado(alumnoEstudio);
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        
        boolean yaEligioasesor=usuarioService.yaeligioasesor(alumno.getId(),alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId());
         int tipoalumno=alumno.getAlumnoEstudioList().get(0).getEstadoAlumno().getId();
       if(tipoalumno!=1)Pregrado=true;
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("yaEligioasesor",yaEligioasesor);
        data.put("Pregrado",Pregrado);
        return data;
    }
       @RequestMapping(value = "asesoressugeridos.json",method = RequestMethod.POST)
        public @ResponseBody
    Map<String, Object> asesoressugeridos(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        if(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || 
                        Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol())){
            alumnoEstudio.setPeriodoAcademicoVigente(null);
        }
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        
      
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        
        boolean isSugerido = false;
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null && alumnoEstudio.getCampus() != null
                && alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio() != null ){             
            if(alumnoEstudioService.isPregrado(alumnoEstudio) && 
                    usuarioService.getConfiguracion(Configuracion.ASESOR_SUGERIDO_ACTIVO).equals("SI")
                    && !alumnoEstudioService.isCachimbo(alumnoEstudio)){
                isSugerido = true;
            }
        }
       
        
        
        data.put("sugeridosList",null);
        if(isSugerido){
            
             int tipoalumno=alumno.getAlumnoEstudioList().get(0).getEstadoAlumno().getId();
              if(tipoalumno!=1)
            data.put("sugeridosList", null );
              else  data.put("sugeridosList", alumnoEstudioService.getSugeridosList(alumnoEstudio.getAlumno().getId(), 
                    alumnoEstudio.getPeriodoAcademicoVigente().getId()) );

        }
       
        return data;
    }
     @RequestMapping(value = "sugeridoseleccion.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> sugeridoseleccion(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer)usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio) commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        if(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || 
                        Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol())){
            alumnoEstudio.setPeriodoAcademicoVigente(null);
        }
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        
        boolean yaEligioasesor=usuarioService.yaeligioasesor(alumno.getId(),alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId());
        
        Map<String, Object> data = new HashMap<String, Object>();
        Persona asesor = alumnoEstudioService.getAsesorAlumno();
        
        boolean isSugerido = false;
        if(alumnoEstudio.getPeriodoAcademicoVigente() != null && alumnoEstudio.getCampus() != null
                && alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio() != null ){             
            if(alumnoEstudioService.isPregrado(alumnoEstudio) && 
                    usuarioService.getConfiguracion(Configuracion.ASESOR_SUGERIDO_ACTIVO).equals("SI")
                    && !alumnoEstudioService.isCachimbo(alumnoEstudio)){
                isSugerido = true;
            }
        }
        data.put("isSugeridos", isSugerido);
        
        data.put("title", "Selección de asesores");
        data.put("periodo", "");
        data.put("idAsesor", "");
        data.put("nombreAsesor", "");
        data.put("fotoAsesor", "");
        data.put("sugeridosList",null);
        if(isSugerido){
            if (asesor != null) {
                data.put("idAsesor", usuarioService.addRefIndirecta(asesor.getId()));
                data.put("nombreAsesor", String.format("%s %s, %s", asesor.getApellidoPaterno(), asesor.getApellidoMaterno(),
                        asesor.getNombres()));            
                data.put("fotoAsesor", asesor.getFoto());
            }
            if(alumnoEstudio.getPeriodoAcademicoVigente() != null){
                data.put("periodo", alumnoEstudio.getPeriodoAcademicoVigente().getNombre());
            }
             int tipoalumno=alumno.getAlumnoEstudioList().get(0).getEstadoAlumno().getId();
              if(tipoalumno!=1)
            data.put("sugeridosList", null );
              else  data.put("sugeridosList", alumnoEstudioService.getSugeridosList(alumnoEstudio.getAlumno().getId(), 
                    alumnoEstudio.getPeriodoAcademicoVigente().getId()) );

        }
        data.put("yaEligioasesor",yaEligioasesor);
        return data;
    }
    
}
