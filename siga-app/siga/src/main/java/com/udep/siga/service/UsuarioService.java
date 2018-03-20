package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Edicionestudio;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.Usuario;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.Configuracion;
import com.udep.siga.bean.enumeration.EstadoAlumno;
import com.udep.siga.bean.enumeration.TipoEstudio;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.dao.AlumnoEstudioDAO;
import com.udep.siga.dao.ConfiguracionDAO;
import com.udep.siga.dao.MensajeriaDAO;
import com.udep.siga.dao.PeriodoAcademicoDAO;
import com.udep.siga.dao.UsuarioDAO;
import com.udep.siga.dao.UtilDAO;
import com.udep.siga.dao.WebServiceArgusDAO;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.Rol;
import com.udep.siga.util.SessionConstants;
import com.udep.siga.util.Util;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.owasp.esapi.errors.AccessControlException;
import org.owasp.esapi.reference.RandomAccessReferenceMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("usuarioService")
public class UsuarioService {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private AlumnoDAO alumnoDAO;
    @Autowired
    private AlumnoEstudioDAO alumnoEstudioDAO;
    @Autowired
    private PeriodoAcademicoDAO periodoAcademicoDAO;
    @Autowired
    private UtilDAO utilDAO;
    @Autowired(required = true)
    private HttpServletRequest request;
    @Autowired
    private RandomAccessReferenceMap randomAccessReferenceMap;
    @Autowired
    private MailService mailService;
    @Autowired
    private MensajeriaDAO mensajeriaDAO;
    @Autowired
    private ConfiguracionDAO configuracionDAO;
    @Autowired
    private WebServiceArgusDAO webServiceArgusDAO;
   
    private static final Logger logger = ESAPI.getLogger(UsuarioService.class);

    /*
     * Recupera la variable de session SessionConstants.INFOUSUARIO
     */
    public Alumno getInfoUsuario() {
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            HttpSession httpSession = request.getSession();
            if (httpSession.getAttribute(SessionConstants.INFOUSUARIO) == null) {
                Usuario usuario = usuarioDAO.getUsuarioByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
                //Cargamos al Alumno
                Alumno alumno = alumnoDAO.getById(usuario.getId());
                //Cargando AlumnoEstudio
                List<AlumnoEstudio> alumnoEstudioList = alumnoEstudioDAO.getEstudiosByAlumno(alumno.getId(), EstadoAlumno.Alumno);
                alumno.setAlumnoEstudioList(alumnoEstudioList);
                
                //Cargando AlumnoEstudio - Egresado y Exalumno
                if(Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol())){
                    List<AlumnoEstudio> alumnoEstudioEgExList = alumnoEstudioDAO.getEstudiosEgExByAlumno(alumno.getId());
                    alumno.setAlumnoEstudioList(alumnoEstudioEgExList);
                }
                
                httpSession.setAttribute(SessionConstants.INFOUSUARIO, alumno);
                return alumno;
            } else {
                return ((Alumno) httpSession.getAttribute(SessionConstants.INFOUSUARIO));
            }
        } else {
            return null;
        }
    }

    public Usuario getUsuario() {
        Usuario usuario = new Usuario();
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            HttpSession httpSession = request.getSession();
            if (httpSession.getAttribute(SessionConstants.INFOUSUARIO) == null) {
                usuario = usuarioDAO.getUsuarioByLogin(
                        SecurityContextHolder.getContext().getAuthentication().getName());
            }
        }
        return usuario;
    }
    /*
     * Recuperar una persona por el ID
     */

    public Persona getPersonaById(int id) {
        return usuarioDAO.getPersonaById(id);
    }
    
    public Boolean isAsistente(int id,int idasignatura,int idseccion) {
        return usuarioDAO.isAsistente(id, idasignatura, idseccion);
    }
    
     public Integer getIdProfesor(int id,int idasignatura,int idseccion) {
        return usuarioDAO.getIdProfesor(id, idasignatura, idseccion);
    }

    public Alumno llenarEdicionEstudioSession() {
        Alumno alumno = getInfoUsuario();
        if (alumno != null) {
            if (alumno.isLlenarEdicionEstudio()) {
                for (AlumnoEstudio alumnoEstudio : alumno.getAlumnoEstudioList()) {
                    Edicionestudio edicionestudio = alumnoEstudioDAO
                            .getEdicionestudioById(alumnoEstudio.getEdicionestudio().getId());
                    alumnoEstudio.setEdicionestudio(edicionestudio);                    
                    
                    if (alumnoEstudio.getPeriodoAcademicoIngreso().getId() != 0) {
                        alumnoEstudio.setPeriodoAcademicoIngreso(
                                periodoAcademicoDAO.getPA(alumnoEstudio.getPeriodoAcademicoIngreso().getId()));
                    }
                    
                    if(alumnoEstudio.getEstadoAlumno() == EstadoAlumno.Alumno){
                        alumnoEstudio.setPeriodoAcademicoVigente(
                                periodoAcademicoDAO.getPAVigenteByAlumnoEstudio(alumnoEstudio));
                        
                        if(alumnoEstudio.getPeriodoAcademicoVigente() == null){
                            alumnoEstudio.setPeriodoAcademicoVigente(
                                periodoAcademicoDAO.getPeriodoAnterior(alumnoEstudio.getAlumno().getId(), 
                                alumnoEstudio.getEdicionestudio().getId(), 0));
                        }
                    }else{
                        alumnoEstudio.setPeriodoAcademicoVigente(
                                periodoAcademicoDAO.getPeriodoAnterior(alumnoEstudio.getAlumno().getId(), 
                                alumnoEstudio.getEdicionestudio().getId(), 0));
                    }
                }                
                alumno.setLlenarEdicionEstudio(false);
            }
        }
        return alumno;
    }

    public DatoPersonal getDatoPersonal(int idPersona) {
        DatoPersonal datoPersonal = usuarioDAO.getDatoPersonalById(idPersona);
        if (datoPersonal != null) {
            if (datoPersonal.getDistrito().getId() != 0) {
                datoPersonal.setDistrito(usuarioDAO
                        .loadProvinciaDepartamentoByIdDistrito(datoPersonal.getDistrito().getId()));
            }
            if (datoPersonal.getDistritoPension().getId() != 0) {
                datoPersonal.setDistritoPension(usuarioDAO
                        .loadProvinciaDepartamentoByIdDistrito(datoPersonal.getDistritoPension().getId()));
            }
        }
        return datoPersonal;
    }

    /*
     * Cambio de Contraseña
     */
    public ArrayList<String> updatePassword(String pass, String newPass, String repeatNewPass) {
        ArrayList<String> errores = new ArrayList<String>();
        if (utilDAO.getContieneEspaciosEnBlanco(newPass)) {
            errores.add("La contraseña no puede contener espacios en blanco");
        }

        if (!utilDAO.getContieneMayusculas(newPass)) {
            errores.add("La contraseña nueva debe contener como mínimo una letra mayúscula");
        }

        if (!utilDAO.getContieneMinusculas(newPass)) {
            errores.add("La contraseña nueva debe contener como mínimo una letra minúscula");
        }

        if (!utilDAO.getContieneNumeros(newPass)) {
            errores.add("La contraseña nueva debe contener como mínimo un número");
        }

        if (newPass.length() < 8) {
            errores.add("La nueva contraseña debe contener al menos 8 caracteres");
        }

        if (!newPass.equals(repeatNewPass)) {
            errores.add("La nueva contraseña y su repetición no coinciden");
        }
        if (errores.isEmpty()) {
            errores = usuarioDAO.updatePassword(SecurityContextHolder.getContext().getAuthentication().getName(),
                    pass, newPass, repeatNewPass);
        }
        return errores;
    }
    
    /*
     * Mapeo de Variables a Referencias Indirectas
     */
    public String addRefIndirecta(Object refDirecta){
        String refIndirecta = randomAccessReferenceMap.getIndirectReference(refDirecta);
        if(refIndirecta == null){
            refIndirecta = randomAccessReferenceMap.addDirectReference(refDirecta);
            return refIndirecta;
        }
        return refIndirecta;
    }
    
    public Object getRefDirecta(String refIndirecta){
        try {
            Object refDirecta = randomAccessReferenceMap.getDirectReference(refIndirecta);
            return refDirecta;
        } catch (AccessControlException ex) {
            Alumno alumno = getInfoUsuario();
            String msg = "Ref. Indirecta invalida.";
            if(alumno != null){
                msg += " - ID Usuario: " + alumno.getId();
                msg += " - Nombre: " + alumno.getApellidoPaterno() + " " + alumno.getNombres();
            }
            msg += " - Dato: " + refIndirecta;
            msg += " - URL: " + request.getRequestURI();
            msg += " - IP: " + request.getRemoteAddr();
            
            logger.error(Logger.EVENT_FAILURE, "AUDITORIA: " + msg);
            
            if(alumno != null){
                List<Email> paraList = mensajeriaDAO.getEmailValidSendList(alumno.getId());

                String body = String.format(AppConstants.MAIL_BODY_AUDITORIA, alumno.getNombres(),
                        alumno.getApellidoPaterno(), alumno.getApellidoMaterno());

                for (Email email : paraList) {
                    mailService.send(email.getEmail(), AppConstants.MAIL_SUBJECT_AUDITORIA, body);
                }
            }
        }
        return null;
    }
    public String getConfiguracion(Configuracion configuracion){
        return configuracionDAO.getConfiguracion(configuracion);
    }
     public String getConfiguracion1(Configuracion configuracion){
        return configuracionDAO.getConfiguracion1(configuracion);
    }
    public Map<String, Object> infoAlumnoReceptorCorrespondencia(Campus campus, String carne){
        return webServiceArgusDAO.infoAlumnoReceptorCorrespondencia(campus, carne);
    }
    
    public Map<String, Object> infoAlumnoFamiliaMiembroById(Campus campus, String carne, int tipoMiembroId){
        return webServiceArgusDAO.infoAlumnoFamiliaMiembroById(campus, carne, tipoMiembroId);
    }
    
    public boolean infoAlumnoFamiliaMiembroUpdateById(Campus campus, int id, 
        String email, String direccion, String telfijo, String telcelular){
        return webServiceArgusDAO.infoAlumnoFamiliaMiembroUpdateById(campus, id, email, direccion, telfijo, telcelular);
    }
    public boolean yaeligioasesor(int idalumno,int idperiodoacademico)
    {
        return alumnoDAO.yaeligioasesor(idalumno, idperiodoacademico);
    }
     public boolean yaActualizoDatos(int idalumno)
    {
        return alumnoDAO.yaActualizoDatos(idalumno);
    }
     public boolean visualizarporCentroyCampus()
    {
       List<AlumnoEstudio> alumnoEstudioList
                = this.getInfoUsuario().getAlumnoEstudioList();
        String destacado = this.getConfiguracion1(Configuracion.ACTUALIZA_DATOS_PERSONALES);
       
           
                    if (!destacado.isEmpty()) {
                        String[] array = destacado.split(";");
                        String para;
                        if (alumnoEstudioList != null) {
                            for (AlumnoEstudio alumnoEstudio : alumnoEstudioList) {
                                if (alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio().getId() == TipoEstudio.Pregrado.getId()) {
                                    para = alumnoEstudio.getEdicionestudio().getEstudio().
                                            getCentroAcademico().getId() + "," + alumnoEstudio.getCampus().getId();
                                    for (String ver : array) {
                                        if (ver.equals(para)) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }    
            }
        
        return false;
    }
    
    public boolean isAlumnoMoroso(AlumnoEstudio alumnoEstudio){
        boolean moroso = true;
        
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
            HttpSession httpSession = request.getSession(false);
            
            if(null == httpSession.getAttribute(SessionConstants.ALUMNO_MOROSO)){
                moroso = webServiceArgusDAO.isMoroso(alumnoEstudio.getCampus(), alumnoEstudio.getCarne()
                                                    , alumnoEstudio.getEdicionestudio().getEstudio().getId());
                
                httpSession.setAttribute(SessionConstants.ALUMNO_MOROSO, moroso);
            }
            moroso = (Boolean)httpSession.getAttribute(SessionConstants.ALUMNO_MOROSO);
        }
        return moroso;
    }
    
    public void setGeneralSession(String sessionConstant, Object value){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute(sessionConstant, value);
        }
    }
    
    public void removeGeneralSession(String sessionConstant){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            HttpSession httpSession = request.getSession();
            httpSession.removeAttribute(sessionConstant);
        }
    }
    
    public Object getGeneralSession(String sessionConstant){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            HttpSession httpSession = request.getSession();
            try{
                return httpSession.getAttribute(sessionConstant);
            }catch(NullPointerException npe){
                return null;
            }
        }
        
        return null;
    }
}
