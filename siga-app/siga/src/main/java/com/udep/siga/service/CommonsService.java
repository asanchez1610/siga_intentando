package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.CentroAcademico;
import com.udep.siga.bean.Departamento;
import com.udep.siga.bean.Distrito;
import com.udep.siga.bean.HorarioEvento;
import com.udep.siga.bean.Provincia;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.dao.PeriodoAcademicoDAO;
import com.udep.siga.dao.UtilDAO;
import com.udep.siga.util.Util;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("commonsService")
public class CommonsService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UtilDAO utilDAO;
    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;
    @Autowired
    private PeriodoAcademicoDAO periodoAcademicoDAO;
    private @Autowired AlumnoDAO alumnoDAO;

    /*
     * Recuperar un AlumnoEstudio de la session de un alumno.
     */
    public AlumnoEstudio getAlumnoEstudioSession(int idEdicionestudio) {
        Alumno alumno = usuarioService.getInfoUsuario();
        for (AlumnoEstudio alumnoEstudio : alumno.getAlumnoEstudioList()) {
            if (alumnoEstudio.getEdicionestudio().getId() == idEdicionestudio) {
                return alumnoEstudio;
            }
        }
        
        if(alumno.getAlumnoEstudioInactivoList() != null){
            for (AlumnoEstudio alumnoEstudio : alumno.getAlumnoEstudioInactivoList()) {
                if (alumnoEstudio.getEdicionestudio().getId() == idEdicionestudio) {
                    return alumnoEstudio;
                }
            }
        }
        return null;
    }
    
    /*
     * Carga de Distrito
     */
    public List<Distrito> getDistritoList(int idProvincia) {
        return utilDAO.getDistritoList(idProvincia);
    }
    /*
     * Carga de Provincia
     */

    public List<Provincia> getProvinciaList(int idDepartamento) {
        return utilDAO.getProvinciaList(idDepartamento);
    }
    /*
     * Carga de Departamento
     */

    public List<Departamento> getDepartamentoList() {
        return utilDAO.getDepartamentoList();
    }
    
    /*
     * Carga Centros Academico By Campus
     */
    public List<CentroAcademico> getCentroAcademicoList(int idCampus){
        return utilDAO.getCentroAcademicoList(idCampus);
    }
    
    public List<Map<String, Object>> getPeriodoAcademicoByCentroAcademicoList(int idCentroAcademico){
        return periodoAcademicoDAO.getPeriodoAcademicoByCentroAcademicoList(idCentroAcademico);
    }
    /*
     * Recuperando Mensajes de recursos 
     */
    public String getRecursoMensaje(String key, String[] params){
        try {
            return messageSource.getMessage(key, params, Locale.getDefault());
        } catch (NoSuchMessageException ex) {
            System.out.println("getRecursoMensaje: " + ex.getMessage());
            return "";
        }
    }
    public String getCiclosbyNivel(Integer nivel){
        String Ciclos="";
        switch(nivel){
            case 1:
                Ciclos="(1,2 ciclo)";
                break;
            case 2:
                Ciclos="(3,4 ciclo)";
                break;
            case 3:
                Ciclos="(5,6 ciclo)";
                break;
            case 4:
                Ciclos="(7,8 ciclo)";
                break;
            case 5:
                Ciclos="(9,10 ciclo)";
                break;
            case 6:
                Ciclos="(11,12 ciclo)"; 
                break;
        }
          return Ciclos;  
    }

    public void registrarAsesoriaHistorial(int tipo){
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        
        if(null == alumno || null == alumno.getAlumnoEstudioList()){
            return;
        }
        
        AlumnoEstudio alumnoEstudio = (alumno.getAlumnoEstudioList().size() > 0) ? alumno.getAlumnoEstudioList().get(0) : null ;
        
        try{
            alumnoDAO.registrarAsesoriaHistorial(alumnoEstudio.getCampus().getId(), alumno.getId(), alumnoEstudio.getPeriodoAcademicoVigente().getId(), tipo);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    boolean isHorarioevento() {
         Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        return utilDAO.isHorarioevento(alumno.getId());
    }

    public List<HorarioEvento> getHorarioEventoList(Integer valor,HttpSession httpSession) throws ParseException {
         Alumno alumno = usuarioService.llenarEdicionEstudioSession();
          AlumnoEstudio alumnoEstudio = (alumno.getAlumnoEstudioList().size() > 0) ? alumno.getAlumnoEstudioList().get(0) : null ;
          Util util = new Util();
         String fecha=(String)httpSession.getAttribute("fecha");
         
        return utilDAO.getHorarioeventoList(alumno.getId(),util.dateeventosumarestaToString(valor,fecha, httpSession),alumnoEstudio.getPeriodoAcademicoVigente().getId());
    }
     public List<String> getHorarioFechasList(String fecha) {
        return utilDAO.getHorarioFechasList(fecha);
    }
}
