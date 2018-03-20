package com.udep.siga.controller;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEncuesta;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AsignaturaEncuesta;
import com.udep.siga.bean.DatoPersonal;
import com.udep.siga.bean.Distrito;
import com.udep.siga.bean.EleccionAsignatura;
import com.udep.siga.bean.Email;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.Configuracion;
import com.udep.siga.bean.enumeration.TipoEmail;
import com.udep.siga.dao.AlumnoDAO;
import com.udep.siga.service.AlumnoEstudioService;
import com.udep.siga.service.AsignaturaDictadaService;
import com.udep.siga.service.AvisoService;
import com.udep.siga.service.CommonsService;
import com.udep.siga.service.DocumentoOficialService;
import com.udep.siga.service.EspecialidadService;
import com.udep.siga.service.LlenarEncuestaService;
import com.udep.siga.service.MensajeService;
import com.udep.siga.service.PensionService;
import com.udep.siga.service.TramiteAcademicoService;
import com.udep.siga.service.UsuarioService;
import com.udep.siga.util.AppConstants;
import com.udep.siga.util.Rol;
import com.udep.siga.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Wilfredo Atoche
 */
@Controller
public class PrincipalController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private LlenarEncuestaService llenarencuestaService;
    @Autowired
    private CommonsService commonsService;
    @Autowired
    private AvisoService avisoService;
    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private AsignaturaDictadaService asignaturaDictadaService;
    @Autowired
    private DocumentoOficialService documentoOficialService;
    @Autowired
    private TramiteAcademicoService tramiteAcademicoService;
    @Autowired
    private AlumnoEstudioService alumnoEstudioService;
    private @Autowired
    PensionService pensionService;
    @Autowired
    private EspecialidadService especialidadService;
    @Autowired
    private PlanEstudioController planEstudioController;
    private @Autowired AlumnoDAO alumnoDAO;

    /*
     * Acceso a la pantalla principal del alumno (Pregrado y Posgrado)
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView principal(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        boolean yaEligioasesor = false;
        boolean yaActualizoDatos = false;
        boolean elegirEspecialidad = false;
        boolean yallenoencuesta = true;
        
        System.out.println(String.format("Usuario: %s - (%s)",
                SecurityContextHolder.getContext().getAuthentication().getName(),
                request.getRemoteAddr()));
        // Alumno alumno = usuarioService.getInfoUsuario();
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        if (alumno == null) {
            return new ModelAndView(new RedirectView("login.htm"));
        }
        
        mav.addObject("nombreAlumno", String.format("%s %s",alumno.getNombres().split(" ")[0], alumno.getApellidoPaterno()));

        yaEligioasesor = alumnoDAO.opcionAsesoramientoEscogida(alumno.getId(), alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId()
                                                                , alumno.getAlumnoEstudioList().get(0).getCampus().getId());
        //usuarioService.yaeligioasesor(alumno.getId(), alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId());
        
        if (Util.isUsuarioAuthority(Rol.EX_EG_PREGRADO.getRol()) || Util.isUsuarioAuthority(Rol.ALUMNO_POSGRADO.getRol())
                || Util.isUsuarioAuthority(Rol.EX_EG_POSGRADO.getRol())) {
            yaEligioasesor = true;
            yaActualizoDatos = true;
        }
        
        if (!alumnoEstudioService.isPregrado(alumno.getAlumnoEstudioList().get(0))
                || usuarioService.getConfiguracion(Configuracion.ASESOR_SUGERIDO_ACTIVO).equals("NO")
                || alumnoEstudioService.isCachimbo(alumno.getAlumnoEstudioList().get(0))) {
            yaEligioasesor = true;
            
        }
        
        if (usuarioService.getConfiguracion(Configuracion.ASESOR_SUGERIDO_ACTIVO).equals("SI")) {
            String verEn = usuarioService.getConfiguracion1(Configuracion.ASESOR_SUGERIDO_ACTIVO);
            boolean edicionEstudioConfigurado = false;
            
            String[] keys = StringUtils.split(verEn,";");

            if(null != keys){
                for(String key : keys){
                    String centro = (null != StringUtils.split(key,",") && StringUtils.split(key,",").length > 0) ? StringUtils.split(key,",")[0] : null;
                    String campus = (null != StringUtils.split(key,",") && StringUtils.split(key,",").length > 0) ? StringUtils.split(key,",")[1] : null;

                    if(null != campus && null != centro && (Integer.parseInt(centro) == alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getEstudio().getCentroAcademico().getId())
                           && ( Integer.parseInt(campus) == alumno.getAlumnoEstudioList().get(0).getCampus().getId()) ){
                        edicionEstudioConfigurado = true;
                        break;
                    }
                }
            }

            if(!edicionEstudioConfigurado){
                yaEligioasesor = true;
            }
        }
        
        if (usuarioService.getConfiguracion(Configuracion.ACTUALIZA_DATOS_PERSONALES).equals("NO")) {
            yaActualizoDatos = true;
        }
        /* boolean Pregrado=alumnoEstudioService.isPregrado(alumno.getAlumnoEstudioList().get(0));
         if(!Pregrado)yaEligioasesor=true;*/

        

        //Verfico si está activa la opción de elegir especialidad y si aplica para el alumno actual o si este ya ha elegido su especialidad.
        elegirEspecialidad = especialidadService.checkEspecialidadConfig(alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getId());
        //verificar si esta activo en la configuracion la opcion de llenar encuesta
        if (usuarioService.getConfiguracion(Configuracion.LLENARENCUESTAECONOMIA).equals("SI")) {
            //validamos si ya lleno encuesta o si es de estudio economia
            yallenoencuesta = llenarencuestaService.checkEspecialidadConfig(alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getId());

        }

        if (!yallenoencuesta) {
            //enviar a pagina de encuesta
            Campus campus = alumno.getAlumnoEstudioList().get(0).getCampus();
            String campus1 = "";
            if (campus.getId() == 1) {
                campus1 = "PIURA";
            }
            if (campus.getId() == 2) {
                campus1 = "LIMA";
            }
            mav.addObject("campus", campus1);

            mav.addObject("estudio", alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getEstudio().getNombre());
            mav.setViewName("encuesta.inicio");
           
            return mav;
        }
          //que siga con la logica actual  

        if (!yaEligioasesor) {
            //enviar info de asesor y lista de asesores
            int idestudio = alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getId();
            Persona asesor = alumnoEstudioService.getAsesorAlumno();

            mav.addObject("periodo", "");
            mav.addObject("idAsesor", "");
            mav.addObject("nombreAsesor", "");
            mav.addObject("fotoAsesor", "");
            mav.addObject("sugeridosList", null);

            if (asesor != null) {
                mav.addObject("idAsesor", usuarioService.addRefIndirecta(asesor.getId()));
                mav.addObject("nombreAsesor", String.format("%s %s, %s", asesor.getApellidoPaterno(), asesor.getApellidoMaterno(),
                        asesor.getNombres()));
                mav.addObject("fotoAsesor", asesor.getFoto());
            }

            mav.addObject("periodo", alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getNombre());
            String errores = "Por favor selecciona tus opciones de asesor.";
            List< Map<String, Object>> sugeridosList = alumnoEstudioService.getSugeridosList(alumno.getId(),
                    alumno.getAlumnoEstudioList().get(0).getPeriodoAcademicoVigente().getId());
            mav.addObject("sugeridosList", sugeridosList);
            // mav.addAllObjects(sugeridosList);
            mav.addObject("currentPlan", usuarioService.addRefIndirecta(idestudio));
            mav.addObject("nombre", "Selección de asesores");
            mav.addObject("errores", errores);
            mav.setViewName("principal_asesoria");
            return mav;
        }

        if (yaActualizoDatos && !elegirEspecialidad) {
            mav.setViewName("principal");
        } else {
            //verificamos si es de un centro y campus donde se aplicará el filtro de actualizacion de datos
            Boolean centroycampus = false;
            centroycampus = usuarioService.visualizarporCentroyCampus();

            if (centroycampus == false) {
                if (elegirEspecialidad) {
                    return planEstudioController.elegirEspecialidad(usuarioService.addRefIndirecta(alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getId()), true);
                } else {
                    mav.setViewName("principal");
                }
            } else {
                //verificamos si ya actualizo datos
                yaActualizoDatos = usuarioService.yaActualizoDatos(alumno.getId());

                if (yaActualizoDatos == true) {
                    if (elegirEspecialidad) {
                        return planEstudioController.elegirEspecialidad(usuarioService.addRefIndirecta(alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getId()), true);
                    } else {
                        mav.setViewName("principal");
                    }
                } else {//logica para actualizacion de datos
                    alumno = usuarioService.getInfoUsuario();
                    alumno.setDatoPersonal(usuarioService.getDatoPersonal(alumno.getId()));
                    mav.addObject("alumno", alumno);
                    List<Email> emailList = mensajeService.getEmailList(alumno.getId());
                    mav.addObject("oficial", "");
                    mav.addObject("personal", "");
                    for (Email email : emailList) {
                        if (email.getTipoEmail() != null) {
                            if (email.getTipoEmail().equals(TipoEmail.OFICIAL)) {
                                mav.addObject("oficial", email.getEmail());
                            } else {
                                mav.addObject("personal", email.getEmail());
                            }
                        }
                    }
                    mav.setViewName("principal_datospersonales");
                }
            }
        }
        return mav;

    }
    
    @RequestMapping(value = "/elegircurso.htm", method = RequestMethod.GET)
    public ModelAndView iniciarencuesta() {
        ModelAndView mav = new ModelAndView();
        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        List<AsignaturaEncuesta> asignaturas = llenarencuestaService.ListaAsignaturas(alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getEstudio().getId());
        //dividir en dos filas  
        List<AsignaturaEncuesta> asignaturas1 = new ArrayList<AsignaturaEncuesta>();

        List<AsignaturaEncuesta> asignaturas2 = new ArrayList<AsignaturaEncuesta>();
        int cantidad = asignaturas.size();

        int mitad = cantidad / 2 + ((cantidad % 2 > 0) ? 1 : 0);

        for (int i = 0; i < mitad; i++) {
            asignaturas1.add(asignaturas.get(i));

        }
        for (int i = mitad; i < cantidad; i++) {
            asignaturas2.add(asignaturas.get(i));

        }

        EleccionAsignatura eleccion = new EleccionAsignatura();

        mav.addObject("eleccion", eleccion);

        mav.addObject("asignaturas1", asignaturas1);
        mav.addObject("asignaturas2", asignaturas2);

        mav.setViewName("encuesta.elegircurso");
        return mav;
    }

    @RequestMapping(value = "/guardarcurso.htm", method = RequestMethod.POST)
    public ModelAndView guardarencuesta(@ModelAttribute EleccionAsignatura elecciones, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        System.out.println(elecciones.getElegidos());
         //guardar la eleccion

        Alumno alumno = usuarioService.llenarEdicionEstudioSession();
        AlumnoEncuesta alumnoencuesta = new AlumnoEncuesta();
        alumnoencuesta.setIdalumno(alumno.getId());
        alumnoencuesta.setIdestudio(alumno.getAlumnoEstudioList().get(0).getEdicionestudio().getEstudio().getId());
        if (elecciones.getEleccion() == true) {

            alumnoencuesta.setIdasignatura1(elecciones.getElegidos().get(0));
            alumnoencuesta.setIdasignatura2(elecciones.getElegidos().get(1));
        } else {

            alumnoencuesta.setIdasignatura1(0);
            alumnoencuesta.setIdasignatura2(0);
        }
        llenarencuestaService.GuardarEncuesta(alumnoencuesta);
        System.out.println(elecciones.getEleccion());

        //return this.principal(request);
        return new ModelAndView(new RedirectView("./"));
    }

    @RequestMapping(value = "/guardarinfodatospersonales.htm", method = RequestMethod.POST)
    public ModelAndView guardardatospersonales(@RequestParam("distrito") String distrito,
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
        return new ModelAndView(new RedirectView("./"));
    }

    /*
     * Resumen
     */

    @RequestMapping(value = "/resumen.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> resumen() {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Resumen");

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;

        Alumno alumno = usuarioService.getInfoUsuario();
        for (AlumnoEstudio alumnoEstudio : alumno.getAlumnoEstudioList()) {
//            if (alumnoEstudio.isMoroso()) {
            if (pensionService.isAlumnoMoroso(alumnoEstudio)) {
                item = new HashMap<String, Object>();
                item.put("tipo", "Pensiones");
                item.put("contenido", commonsService.getRecursoMensaje("resumen.pension.i01", null));
                list.add(item);
                break;
            }
        }
        //Avisos
        item = new HashMap<String, Object>();
        List<String> lista = avisoService.getResumen();
        item.put("tipo", "Avisos");
        if (lista.size() > 0) {
            item.put("contenido", lista);
        } else {
            item.put("contenido", "No hay avisos nuevos en los últimos "
                    + AppConstants.RESUMEN_DIAS + " días");
        }
        list.add(item);

        //Asignatura
        item = new HashMap<String, Object>();
        lista = asignaturaDictadaService.getResumen();
        item.put("tipo", "Asignaturas");
        if (lista.size() > 0) {
            item.put("contenido", lista);
        } else {
            item.put("contenido", "No hay noticias de asignaturas en los últimos "
                    + AppConstants.RESUMEN_DIAS + " días");
        }
        list.add(item);

        //Mensajes
        item = new HashMap<String, Object>();
        lista = mensajeService.getResumen();
        item.put("tipo", "Mensajes");
        if (lista.size() > 0) {
            item.put("contenido", lista);
        } else {
            item.put("contenido", "No hay mensajes nuevos en los últimos "
                    + AppConstants.RESUMEN_DIAS + " días");
        }
        list.add(item);

        //Tramites academicos
        item = new HashMap<String, Object>();
        lista = tramiteAcademicoService.getResumen();
        item.put("tipo", "Trámites académicos");
        if (lista.size() > 0) {
            item.put("contenido", lista);
        } else {
            item.put("contenido", "No hay eventos nuevos");
        }
        list.add(item);

        //Documentos oficiales
        item = new HashMap<String, Object>();
        lista = documentoOficialService.getResumen();
        item.put("tipo", "Documentos oficiales");
        if (lista.size() > 0) {
            item.put("contenido", lista);
        } else {
            item.put("contenido", "No hay documentos oficiales nuevos en los últimos "
                    + AppConstants.RESUMEN_DIAS + " días");
        }
        list.add(item);

        if (list.isEmpty()) {
            item = new HashMap<String, Object>();
            item.put("tipo", "");
            item.put("contenido", "No hay Eventos Nuevos");
            list.add(item);
        }

        data.put("eventos", list);
        return data;
    }

    @RequestMapping(value = "updatePassword.json", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> updatePassword(@RequestParam("actual") String pass,
            @RequestParam("pass1") String passNew, @RequestParam("pass2") String repeatPassNew) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("title", "Errores");
        data.put("errores", usuarioService.updatePassword(pass, passNew, repeatPassNew));
        return data;
    }

    @RequestMapping(value = "/guardarEspecialidad", method = RequestMethod.POST)
    public ModelAndView guardarEspecialidadByAlumno(@RequestParam(value = "idEdicionEstudio", required = true) String idEdicionEstudioRandom,
            @RequestParam(value = "idEspecialidad", required = true) String idEspecialidadRandom,
            @RequestParam(value = "fl", required = false) boolean fromlogin,
            HttpServletRequest request) {

        return planEstudioController.guardarEspecialidadByAlumno(idEdicionEstudioRandom, idEspecialidadRandom, fromlogin, request);
    }
}
