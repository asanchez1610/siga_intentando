package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.Mensaje;
import com.udep.siga.bean.Email;
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
@Service("mensajeService")
public class MensajeService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MensajeriaDAO mensajeriaDAO;
    @Autowired
    private MailService mailService;

    public List<Mensaje> getInbox(boolean all) {
        Alumno alumno = usuarioService.getInfoUsuario();
        List<Mensaje> list = mensajeriaDAO.getInbox(alumno.getId(), all);

        for (Mensaje mensaje : list) {
            mensaje.setPersonaDe(usuarioService
                    .getPersonaById(mensaje.getPersonaDe().getId()));
        }
        return list;
    }

    public List<Mensaje> getOutbox() {
        Alumno alumno = usuarioService.getInfoUsuario();
        List<Mensaje> list = mensajeriaDAO.getOutbox(alumno.getId());

        for (Mensaje mensaje : list) {
            mensaje.setPersonaPara(usuarioService
                    .getPersonaById(mensaje.getPersonaPara().getId()));
        }
        return list;
    }

    public Mensaje getMensaje(int id) {
        Mensaje mensaje = mensajeriaDAO.getMensaje(id);
        return mensaje;
    }

    public void updateLeido(int id, boolean actualizaFechaLeido) {
        mensajeriaDAO.updateLeido(id, actualizaFechaLeido);
    }

    public ArrayList<String> saveMensaje(Mensaje mensaje) {
        ArrayList<String> error = new ArrayList<String>();
        if (mensaje.getAsunto() == null || mensaje.getAsunto().equals("")) {
            error.add("Debe ingresar un asunto.");
        }
        if (mensaje.getMensaje() == null || mensaje.getMensaje().equals("")) {
            error.add("Debe ingresar un mensaje.");
        }
        if (error.isEmpty()) {
            Alumno alumno = usuarioService.getInfoUsuario();
            mensaje.setPersonaDe(alumno);
            mensajeriaDAO.saveMensajeria(mensaje);

            List<Email> paraList = mensajeriaDAO.getEmailValidSendList(mensaje.getPersonaPara().getId());
            List<Email> deList = mensajeriaDAO.getEmailValidSendList(mensaje.getPersonaDe().getId());
            String emailDe = "";
            if(!deList.isEmpty()){
                emailDe = "\nEmail: " + deList.get(0).getEmail(); 
            }
            
            String body = String.format(AppConstants.MAIL_BODY_ASESOR,
                    mensaje.getPersonaDe().getNombres(),
                    mensaje.getPersonaDe().getApellidoPaterno(),
                    mensaje.getPersonaDe().getApellidoMaterno(),
                    emailDe,
                    mensaje.getAsunto(),
                    mensaje.getMensaje());

            for (Email email : paraList) {                
                mailService.send(email.getEmail(), AppConstants.MAIL_SUBJECT_ASESOR, body);     
            }
        }
        return error;
    }

    public int getCantidadMensajesSinLeer() {
        Alumno alumno = usuarioService.getInfoUsuario();
        return mensajeriaDAO.getCantidadMensaje(alumno.getId(), false);
    }

    public int getCantidadMensajesNuevos() {
        Alumno alumno = usuarioService.getInfoUsuario();
        return mensajeriaDAO.getCantidadMensaje(alumno.getId(), true);
    }

    public List<Email> getEmailList(int idPersona) {
        return mensajeriaDAO.getEmailList(idPersona);
    }

    public List<String> getResumen() {
        List<String> list = new ArrayList<String>();
        int numero;

        numero = this.getCantidadMensajesSinLeer();
        if (numero > 0) {
            list.add("<a href=\"#/mensajes/inbox\"> " + numero + " mensajes sin leer</a>");
        }

        numero = this.getCantidadMensajesNuevos();
        if (numero > 0) {
            list.add("<a href=\"#/mensajes/inbox\"> " + numero + " mensajes nuevos</a>");
        }

        return list;
    }
}
