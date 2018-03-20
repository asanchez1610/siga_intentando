package com.udep.siga.controller;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Distrito;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.TipoEmail;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.MensajeService;
import com.udep.siga.service.UsuarioService;
import java.util.ArrayList;
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
@RequestMapping("/json/personal/*")
public class PerfilPersonalController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private CommonsService commonsService;
    @Autowired
    private AlumnoEstudioService alumnoEstudioService;

    @RequestMapping(value = "info.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getInfo(@RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Mi perfil personal");
        Alumno alumno = usuarioService.getInfoUsuario();
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio);

        alumno.setDatoPersonal(usuarioService.getDatoPersonal(alumno.getId()));
        data.put("alumno", alumno);
        data.put("emailList", mensajeService.getEmailList(alumno.getId()));
        data.put("mostrarDatosPersonales", alumnoEstudio.getCampus()==Campus.Piura?true:false);
        return data;
    }

    @RequestMapping(value = "infoEdit.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getInfoEdit() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Mi perfil personal");
        Alumno alumno = usuarioService.getInfoUsuario();
        alumno.setDatoPersonal(usuarioService.getDatoPersonal(alumno.getId()));
        data.put("alumno", alumno);
        List<Email> emailList = mensajeService.getEmailList(alumno.getId());
        data.put("oficial", "");
        data.put("personal", "");
        for (Email email : emailList) {
            if (email.getTipoEmail().equals(TipoEmail.OFICIAL)) {
                data.put("oficial", email.getEmail());
            } else {
                data.put("personal", email.getEmail());
            }
        }
        return data;
    }

    @RequestMapping(value = "saveInfoEdit.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> saveInfoEdit(@RequestParam("distrito") String distrito,
            @RequestParam("direccion") String direccion, @RequestParam("telefonoCasa") String telefono,
            @RequestParam("telefonoCelular") String telefonoCelular, @RequestParam("emailPersonal") String emailPersonal, @RequestParam("pension_distrito") String distritoPension,
            @RequestParam("pensionDireccion") String direccionPension, @RequestParam("pensionTelefono") String telefonoPension,
            @RequestParam("emergenciaNombre") String contactoEmergencia, @RequestParam("emergenciaTelefono") String telefonoEmergencia) {

        List<String> errores = new ArrayList<String>();

        int idDistrito = 0;
        int idDistritoPension = 0;
        try {
            idDistrito = Integer.parseInt(distrito);
            idDistritoPension = Integer.parseInt(distritoPension);
        } catch (NumberFormatException ex) {
            errores.add("Los datos son incorrectos.");
        }

        Map<String, Object> data = new HashMap<String, Object>();
        if (errores.isEmpty()) {
            Alumno alumno = usuarioService.getInfoUsuario();

            DatoPersonal datoPersonal = new DatoPersonal();

            datoPersonal.setDistrito(new Distrito(idDistrito));
            datoPersonal.setDireccion(direccion);
            datoPersonal.setTelefono(telefono);
            datoPersonal.setTelefonoMovil(telefonoCelular);
            datoPersonal.setDistritoPension(new Distrito(idDistritoPension));
            datoPersonal.setDireccionPension(direccionPension);
            datoPersonal.setTelefonoPension(telefonoPension);
            datoPersonal.setContactoEmergencia(contactoEmergencia);
            datoPersonal.setTelefonoEmergencia(telefonoEmergencia);

            List<Email> emailList = new ArrayList<Email>();
            Email email = new Email();
            email.setTipoEmail(TipoEmail.PERSONAL);
            email.setEmail(emailPersonal);
            emailList.add(email);
            errores = alumnoEstudioService.updateInfo(alumno.getId(), datoPersonal, emailList);
        }
        data.put("errores", errores);

        return data;
    }
    
    /*
     * Receptor de Correspondencia
     */
    @RequestMapping(value = "receptorCorrespondencia.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> receptorCorrespondencia(
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Receptor de correspondencia");
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio);
        
        data.put("receptor", usuarioService.infoAlumnoReceptorCorrespondencia(alumnoEstudio.getCampus(), alumnoEstudio.getCarne()));

        return data;
    }
    
    /*
     * Ver Información de miembro
     */
    @RequestMapping(value = "verInfoMiembro.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> verInfoMiembro(
            @RequestParam(value = "idTipo", required = true) Integer idTipo,
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        
        Map<String, Object> data = new HashMap<String, Object>();
        
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio);
        
        data.put("miembro", usuarioService.infoAlumnoFamiliaMiembroById(alumnoEstudio.getCampus(), alumnoEstudio.getCarne(), idTipo));

        return data;
    }
    
    /*
     * Actualiza Información de miembro
     */
    @RequestMapping(value = "updateInfoMiembro.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updateInfoMiembro(
            @RequestParam(value = "id", required = true) Integer id,
            @RequestParam(value = "email", required = true) String email, 
            @RequestParam(value = "direccion", required = true) String direccion, 
            @RequestParam(value = "telefonofijo", required = true) String telefonofijo, 
            @RequestParam(value = "telefonocelular", required = true) String telefonocelular,
            @RequestParam(value = "idEdicionestudio", required = true) String idEdicionestudioRandom) {
        
        Integer idEdicionestudio = (Integer) usuarioService.getRefDirecta(idEdicionestudioRandom);
        AlumnoEstudio alumnoEstudio = (AlumnoEstudio)commonsService.getAlumnoEstudioSession(idEdicionestudio);
        
        Map<String, Object> data = new HashMap<String, Object>();
        boolean isUpdate = usuarioService.infoAlumnoFamiliaMiembroUpdateById(alumnoEstudio.getCampus(), 
                id, email, direccion, telefonofijo, telefonocelular);
        List<String> errores = new ArrayList<String>();
        if(!isUpdate){
            errores.add("Error al actualizar miembro de la familia.");
        }
        data.put("errores", errores);

        return data;
    }

    /*
     * Carga de Departamentos
     */
    @RequestMapping(value = "departamentoList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getDepartamentoList() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("departamentoList", commonsService.getDepartamentoList());
        return data;
    }

    /*
     * Carga de Provincias
     */
    @RequestMapping(value = "provinciaList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getProvinciaList(
            @RequestParam(value = "idDepartamento", required = true) Integer idDepartamento) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("provinciaList", commonsService.getProvinciaList(idDepartamento));
        return data;
    }

    /*
     * Carga de Distritos
     */
    @RequestMapping(value = "distritoList.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> getDistritoList(
            @RequestParam(value = "idProvincia", required = true) Integer idProvincia) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("distritoList", commonsService.getDistritoList(idProvincia));
        return data;
    }
}
