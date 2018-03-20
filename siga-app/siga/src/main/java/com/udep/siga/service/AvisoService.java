package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.AsignaturaDictada;
import com.udep.siga.bean.Aviso;
import com.udep.siga.bean.Destacado;
import com.udep.siga.bean.Persona;
import com.udep.siga.bean.enumeration.TipoEstudio;
import com.udep.siga.dao.AsignaturaDictadaDAO;
import com.udep.siga.dao.AvisoDAO;
import com.udep.siga.util.Util;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author Wilfredo Atoche
 */
@Service("avisoService")
public class AvisoService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AvisoDAO avisoDAO;
    @Autowired
    private AsignaturaDictadaDAO asignaturaDictadaDAO;
    private static final Logger log = Logger.getLogger(AvisoService.class);

    public List<Aviso> getAvisos(boolean caducados, int limit) {
       
        Alumno alumno = usuarioService.getInfoUsuario();
        
        List<Aviso> list = avisoDAO.getAvisos(alumno.getId());
        for (Aviso aviso : list) {
            aviso.setPersona(usuarioService.getPersonaById(aviso.getPersona().getId()));
            if (!aviso.getRutaArchivo().isEmpty()) {
                String[] array = aviso.getRutaArchivo().split("/");
                aviso.setRutaArchivo(array[array.length - 1]);
            }
        }
        return list;
    }

    public Destacado getDestacado() {
        List<AlumnoEstudio> alumnoEstudioList
                = usuarioService.getInfoUsuario().getAlumnoEstudioList();
        List<Destacado> destacadoList = avisoDAO.getDestacado();
        if (destacadoList != null) {
            for (Destacado destacado : destacadoList) {
                if (destacado != null) {
                    if (!destacado.getVerEn().isEmpty()) {
                        String[] array = destacado.getVerEn().split(";");
                        String para;
                        if (alumnoEstudioList != null) {
                            for (AlumnoEstudio alumnoEstudio : alumnoEstudioList) {
                                if (alumnoEstudio.getEdicionestudio().getEstudio().getTipoEstudio().getId() == TipoEstudio.Pregrado.getId()) {
                                    para = alumnoEstudio.getEdicionestudio().getEstudio().
                                            getCentroAcademico().getId() + "," + alumnoEstudio.getCampus().getId();
                                    for (String ver : array) {
                                        if (ver.equals(para)) {
                                            return destacado;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public String getRutaDownload(int idAviso) throws FileNotFoundException {
        String fullPathArchivo = avisoDAO.getAviso(idAviso).getRutaArchivo();

        if (!fullPathArchivo.isEmpty()) {
            return fullPathArchivo;
        } else {
            return "";
        }
    }

    public int getTotalAvisosNuevos() {
        String arrayEstudioCampus = "'0,0'";
        String arrayCampus = "0";

        Alumno alumno = usuarioService.getInfoUsuario();
        for (AlumnoEstudio alumnoEstudio : alumno.getAlumnoEstudioList()) {
            arrayEstudioCampus += String.format(",'%s,%s'",
                    alumnoEstudio.getEdicionestudio().getEstudio().getId(),
                    alumnoEstudio.getCampus().getId());
            arrayCampus += String.format(",%s",
                    alumnoEstudio.getCampus().getId());
        }
        return avisoDAO.getTotalAvisosNuevos(arrayEstudioCampus, arrayCampus);
    }

    /*
     * Obtiene lista de avisos de asignatura-seccion para mostrar a los alumnos
     */
    public List<Map<String, Object>> getAvisoAsignaturaList(int idAsignatura, int idSeccion) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        List<Aviso> listAvisos = avisoDAO.getAvisoAsignaturaList(idAsignatura, idSeccion);
        for (Aviso aviso : listAvisos) {
            Persona persona = usuarioService.getPersonaById(aviso.getPersona().getId());
            int id = 0;
            id = persona.getId();
            Boolean esAsistente = false;
            esAsistente = usuarioService.isAsistente(id, idAsignatura, idSeccion);
            if (esAsistente == false) {
                persona.setId(0);
                persona.setDni(null);
                persona.setFoto(null);
                persona.setTipoDocIdentidad(null);
                //quien es quien
                aviso.setPersona(persona);
            } else {
                int idprofesor = usuarioService.getIdProfesor(id, idAsignatura, idSeccion);
                persona = usuarioService.getPersonaById(idprofesor);
                persona.setId(0);
                persona.setDni(null);
                persona.setFoto(null);
                persona.setTipoDocIdentidad(null);
                //quien es quien
                aviso.setPersona(persona);
            }
            item = new HashMap<String, Object>();
            item.put("general", aviso.isGeneral());
            item.put("id", aviso.getId());
            item.put("fecha", Util.dateToStringLong(aviso.getFecha()));
            item.put("titulo", aviso.getTitulo());
            item.put("persona", aviso.getPersona());
            item.put("descripcion", aviso.getDescripcion());
            list.add(item);
        }
        return list;
    }

    public int getTotalAvisosCursos() {
        Alumno alumno = usuarioService.getInfoUsuario();
        List<AsignaturaDictada> asignaturaDictadaList = asignaturaDictadaDAO
                .getAsignaturaDictadaList(alumno.getId());

        int count = 0;
        for (AsignaturaDictada asignaturaDictada : asignaturaDictadaList) {
            count += avisoDAO.getTotalAvisosAsignaturaNuevos(asignaturaDictada.getId(),
                    asignaturaDictada.getAsignaturaSeccion().getIdSeccion());
        }
        return count;
    }

    public List<String> getResumen() {
        List<String> list = new ArrayList<String>();
        int avisos;

        avisos = this.getTotalAvisosNuevos();
        if (avisos > 0) {
            list.add("<a href=\"#/avisos\"> " + avisos + " Nuevos avisos publicados</a>");
        }
        avisos = this.getTotalAvisosCursos();
        if (avisos > 0) {
            list.add("<a href=\"#/cursos\"> " + avisos + " Nuevos anuncios de cursos</a>");
        }

        return list;
    }
}
