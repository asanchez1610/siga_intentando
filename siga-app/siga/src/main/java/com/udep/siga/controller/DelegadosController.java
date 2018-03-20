package com.udep.siga.controller;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Alumnocandidato;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.Rol;
import com.udep.siga.util.Util;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author andy
 */
@Controller
@RequestMapping("/json/delegado/*")
public class DelegadosController {

    @Autowired
    private CommonsService commonsService;
    @Autowired
    private AlumnoEstudioService alumnoEstudioService;
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(value = "eleccion.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getListacandidatos(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Delegados");
        
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        boolean fechaInicio=alumnoEstudioService.isVer(alumnoEstudio.getPeriodoAcademicoVigente().getId() ,alumnoEstudio.getCampus().getId(),alumnoEstudio.getEdicionestudio().getId());
        if(fechaInicio==false){
            // String fechaDelegados=alumnoEstudioService.isFechaVotacion(alumnoEstudio.getPeriodoAcademicoVigente().getId() ,alumnoEstudio.getCampus().getId(),alumnoEstudio.getEdicionestudio().getId());
            data.put("fechaDelegados","-");
        }else
        {
          String fechaDelegados=alumnoEstudioService.isFechaVotacion(alumnoEstudio.getPeriodoAcademicoVigente().getId() ,alumnoEstudio.getCampus().getId(),alumnoEstudio.getEdicionestudio().getId());
          data.put("fechaDelegados",fechaDelegados);
        }
        data.put("fechaInicio",fechaInicio);
        boolean isDelegados=alumnoEstudioService.isDelegados(alumnoEstudio.getEdicionestudio().getId(), alumnoEstudio.getCampus().getId(), alumnoEstudio.getEdicionestudio().getPeriodicidadPlanEstudio().getId());
        boolean isNivel=alumnoEstudioService.verCandidatosporNivel(alumnoEstudio.getEdicionestudio().getId(), alumnoEstudio.getCampus().getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId() ,alumnoEstudio.getNivel());
        List<Alumnocandidato> candidatos=alumnoEstudioService.getAlumnoCandidatoLista(idEdicionestudio,  alumnoEstudio.getCampus().getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId(), alumnoEstudio.getNivel());
        if(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || 
           Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol()) ){
           isDelegados=true;
        }
        data.put("fechaResultados","-");
        boolean yaVoto=alumnoEstudioService.getAlumnoVoto(alumnoEstudio.getPeriodoAcademicoVigente().getId(), alumnoEstudio.getCampus().getId(), alumnoEstudio.getEdicionestudio().getId(),alumnoEstudio.getAlumno().getId());
        boolean verBoton=alumnoEstudioService.isVerBotonElegir(alumnoEstudio.getPeriodoAcademicoVigente().getId() ,alumnoEstudio.getCampus().getId(),alumnoEstudio.getEdicionestudio().getId());
        
        data.put("yaVoto",yaVoto);
        
        data.put("candidatos",candidatos );
        boolean  verresultadosvotacion=alumnoEstudioService.verFechaResultadosVotacion(alumnoEstudio.getEdicionestudio().getId(), alumnoEstudio.getCampus().getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId());
       if(yaVoto && !verresultadosvotacion)
       {
          verBoton=false;
        String fechaResultados=alumnoEstudioService.isFechaResultados(alumnoEstudio.getPeriodoAcademicoVigente().getId() ,alumnoEstudio.getCampus().getId(),alumnoEstudio.getEdicionestudio().getId());
         data.put("fechaResultados",fechaResultados); 
       }
        
        if(verresultadosvotacion)
        {
            List<Alumnocandidato> candidatosvotos=alumnoEstudioService.getAlumnoCandidatoVotos(idEdicionestudio,  alumnoEstudio.getCampus().getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId(), alumnoEstudio.getNivel());
            data.put("candidatos",candidatosvotos );
        }
        data.put("verresultadosvotacion",verresultadosvotacion);
        data.put("verBoton",verBoton );
        String Ciclos=commonsService.getCiclosbyNivel(alumnoEstudio.getNivel());
        data.put("alumnoEstudio", alumnoEstudio);
        data.put("isDelegados",isDelegados );
        data.put("isNivel",isNivel );
        data.put("Ciclos",Ciclos );
        

        return data;
    }
   @RequestMapping(value = "confirmar.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getConfirmacioncandidatos(
            @RequestParam(value = "idCandidato", required = true) Integer idAlumnocandidato
           ,@RequestParam(value = "idEdicionestudio", required = true) Integer idEdicionestudio
           ,@RequestParam(value = "idNivel", required = true) Integer idNivel
           , @RequestParam(value = "idalumnoCandidato", required = true) Integer idcandidato) 
    {
          Alumno candidato =alumnoEstudioService.getById(idAlumnocandidato);
          String apellidopaterno=candidato.getApellidoPaterno();
          StringTokenizer tokenizer = new StringTokenizer(candidato.getNombres());
         StringBuilder primernombre = new StringBuilder();
        while(tokenizer.hasMoreTokens())
        {
        primernombre.append(tokenizer.nextToken());break;
        }
           String nombre=primernombre.toString();
           AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
           Map<String, Object> data = new HashMap<String, Object>();  
           data.put("nombreyapellido",nombre+" "+apellidopaterno);
           data.put("estudio",alumnoEstudio.getEdicionestudio().getEstudio().getNombre());
           data.put("nivel",idNivel);
           data.put("idcandidato",idAlumnocandidato);
           data.put("idalumnocandidato",idcandidato);
           data.put("idestudio",idEdicionestudio);
            return data;
   }
   @RequestMapping(value = "saveInfo.json", method = RequestMethod.POST)
    public @ResponseBody 
    Map<String, Object> getConfirmacioncandidatos( @RequestParam(value = "idcandidato", required = true) Integer idCandidato
    ,@RequestParam(value = "idedicionestudio", required = true) Integer idEdicionestudio
    ,@RequestParam(value = "idalumnocandidato", required = true) Integer idalumnocandidato)
   {
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio).clone();
        int idcampus=alumnoEstudio.getCampus().getId();
        int idperiodoacademico=alumnoEstudio.getPeriodoAcademicoVigente().getId();
        int idalumno=alumnoEstudio.getAlumno().getId();
       
      int result= alumnoEstudioService.guardarvotaciondelegado( idcampus, idperiodoacademico,idalumnocandidato, idalumno,idEdicionestudio);
       Map<String, Object> data = new HashMap<String, Object>();
       data.put("errores","ninguno");
       return data;
   }
}
