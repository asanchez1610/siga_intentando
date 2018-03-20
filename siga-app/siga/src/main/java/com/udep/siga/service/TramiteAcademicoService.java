package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Solicitud;
import com.udep.siga.bean.enumeration.CategoriaSolicitud;
import com.udep.siga.dao.TramiteAcademicoDAO;
import com.udep.siga.util.Rol;
import com.udep.siga.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("tramiteAcademicoService")
public class TramiteAcademicoService {

    @Autowired
    private TramiteAcademicoDAO tramiteAcademicoDAO;
    @Autowired
    private CommonsService commonsService;
    @Autowired
    private UsuarioService usuarioService;

    public List<Solicitud> getPendientesList(int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);

        return tramiteAcademicoDAO.getPendientes(alumnoEstudio.getAlumno().getId(),
                alumnoEstudio.getEdicionestudio().getId());
    }

    public List<Solicitud> getFinalizadosList(int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        return tramiteAcademicoDAO.getFinalizados(alumnoEstudio.getAlumno().getId(),
                alumnoEstudio.getEdicionestudio().getId());

    }

    public List<Map<String, Object>> getCategoriaSolicitudList() {
        List<Rol> rolList = Util.getRolesUsuario();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> item;
        for (CategoriaSolicitud categoriaSolicitud : CategoriaSolicitud.values()) {
            item = new HashMap<String, Object>();
            item.put("nombre", categoriaSolicitud.getNombre());
            item.put("solicitudes", tramiteAcademicoDAO.getTipoSolicitudesByCategoria(
                    categoriaSolicitud, rolList));
            list.add(item);
        }
        return list;
    }

    public List<String> saveTramiteAcademico(Solicitud solicitud, int idEdicionestudio) {
        List<String> errores = new ArrayList<String>();
        if (solicitud.getObsAlumno().trim().isEmpty()) {
            errores.add("El cuerpo de la solicitud es requerido.");
        }
        if (errores.isEmpty()) {
            AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
            tramiteAcademicoDAO.saveTramiteAcademico(solicitud, alumnoEstudio);
            return errores;
        }

        return errores;
    }

    public int getRespuestasNuevas() {
        Alumno alumno = usuarioService.getInfoUsuario();
        return tramiteAcademicoDAO.getTotalRespuestasNuevas(alumno.getId());
    }
    
    public List<String> getResumen() {
        List<String> list = new ArrayList<String>();
        int dato;

        dato = this.getRespuestasNuevas();
        if (dato > 0) {
            list.add("<a href=\"#/tramites\">Nueva respuesta a solicitud</a>");
        }
        
        return list;
    }
    
}
