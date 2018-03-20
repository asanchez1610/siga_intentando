package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.CategoriaConsulta;
import com.udep.siga.bean.Consulta;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.enumeration.TipoEstudio;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.dao.ConsultaDAO;
import com.udep.siga.dao.MensajeriaDAO;
import com.udep.siga.util.AppConstants;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrador
 */
@Service("consultaService")
public class ConsultaService {
    
    @Autowired
    private ConsultaDAO consultaDAO;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MailService mailService;
    @Autowired
    private AlumnoDAO alumnoDAO;
    @Autowired
    private MensajeriaDAO mensajeriaDAO;
    
    public List<Consulta> getPendientes(){
        Alumno alumno = usuarioService.getInfoUsuario();
        return consultaDAO.getPendientes(alumno.getId());
    }
    
    public List<Consulta> getFinalizados (){
        Alumno alumno = usuarioService.getInfoUsuario();
        return consultaDAO.getFinalizados(alumno.getId());
    }
    
    public CategoriaConsulta getCategoriaById (int idCategoria){
        return consultaDAO.getCategoriaById(idCategoria);
    }
    
    public List<CategoriaConsulta> getCategorias(){
        return consultaDAO.getCategorias();
    }
    
    public ArrayList<String> saveConsulta(Consulta consulta){
        ArrayList<String> error = new ArrayList<String>();
        if (consulta.getConsulta() == null || consulta.getConsulta().equals("")) {
            error.add("Debe ingresar su reclamo y/o sugerencia.");
        }
        if (error.isEmpty()) {
            Alumno alumno = usuarioService.getInfoUsuario();         
            
            consultaDAO.saveConsulta(consulta,alumno.getId());
            String asunto = String.format(AppConstants.MAIL_SUBJECT_CONSULTA,
                    consulta.getCategoria().getNombre());

            String body = String.format(AppConstants.MAIL_BODY_CONSULTA,
                    alumno.getNombres(),
                    alumno.getApellidoPaterno(),
                    alumno.getApellidoMaterno(),
                    consulta.getCategoria().getNombre(),
                    consulta.getConsulta());
            
            mailService.send(AppConstants.MAIL_SIGA, asunto, body);
            
            for(AlumnoEstudio alumnoEstudio : alumno.getAlumnoEstudioList()){
                if(alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio().getId() == TipoEstudio.Pregrado.getId()){
                    List<Email> secretarioList = mensajeriaDAO.getEmailValidSendList
                            (alumnoDAO.getSecretarioFacultad(alumnoEstudio.getEdicionestudio().getEstudio()
                            .getCentroAcademico().getId(),alumnoEstudio.getCampus().getId()).getId());
                    for (Email email : secretarioList) {                
                        mailService.send(email.getEmail(), asunto, body);     
                    }    
                }
            }
        }
        return error;
    }
    
    public ArrayList<String> EditConsulta(Consulta consulta){
        ArrayList<String> error = new ArrayList<String>();
        if (consulta.getConsulta() == null || consulta.getConsulta().equals("")) {
            error.add("Debe ingresar su reclamo y/o sugerencia.");
        }
        if (error.isEmpty()) {
            Alumno alumno = usuarioService.getInfoUsuario();
            consultaDAO.EditConsulta(consulta,alumno.getId());

            String asunto = String.format(AppConstants.MAIL_SUBJECT_CONSULTA,
                    consulta.getCategoria().getNombre());

            String body = String.format(AppConstants.MAIL_BODY_CONSULTA,
                    alumno.getNombres(),
                    alumno.getApellidoPaterno(),
                    alumno.getApellidoMaterno(),
                    consulta.getCategoria().getNombre(),
                    consulta.getConsulta());
            
            mailService.send(AppConstants.MAIL_SIGA, asunto, body);
            
            for(AlumnoEstudio alumnoEstudio : alumno.getAlumnoEstudioList()){
                if(alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio().getId() == TipoEstudio.Pregrado.getId()){
                    List<Email> secretarioList = mensajeriaDAO.getEmailValidSendList
                            (alumnoDAO.getSecretarioFacultad(alumnoEstudio.getEdicionestudio().getEstudio()
                            .getCentroAcademico().getId(),alumnoEstudio.getCampus().getId()).getId());
                    for (Email email : secretarioList) {                
                        mailService.send(email.getEmail(), asunto, body);     
                    }    
                }
            }
        }
        return error;
    }
}
