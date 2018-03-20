package com.udep.siga.service;

import com.udep.siga.bean.Alumno;
import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Solicitud;
import com.udep.siga.bean.TipoSolicitud;
import com.udep.siga.bean.enumeration.Campus;
import com.udep.siga.bean.enumeration.EstadoAlumno;
import com.udep.siga.bean.enumeration.Idioma;
import com.udep.siga.dao.AlumnoEstudioDAO;
import com.udep.siga.dao.DocumentoOficialDAO;
import com.udep.siga.util.Rol;
import com.udep.siga.util.Util;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Wilfredo Atoche
 */
@Service("documentoOficial")
public class DocumentoOficialService {

    @Autowired
    private DocumentoOficialDAO documentoOficialDAO;
    @Autowired
    private CommonsService commonsService;
    @Autowired
    private AlumnoEstudioDAO alumnoEstudioDAO;
    @Autowired
    private UsuarioService usuarioService;

    public List<Solicitud> getPendientes(int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        List<Solicitud> list = documentoOficialDAO.getPendientes(alumnoEstudio);
        for (Solicitud solicitud : list) {
            solicitud.setIdRandom(usuarioService.addRefIndirecta(solicitud.getId()));
            
            if(solicitud.isVoucher()){
                String url = documentoOficialDAO.getVoucher(solicitud.getId());
                if(!url.isEmpty()){
                    solicitud.setUrlVoucher(
                            usuarioService.addRefIndirecta(url));
                }
            }
            solicitud.setId(0);
        }
        return list;
    }

    public int getTotalPendientes() {
        Alumno alumno = usuarioService.getInfoUsuario();
        int count = 0;
        List<AlumnoEstudio> alumnoEstudioList = alumnoEstudioDAO.getEstudiosByAlumno(alumno.getId(), EstadoAlumno.Alumno);
        for (AlumnoEstudio alumnoEstudio : alumnoEstudioList) {
            count += documentoOficialDAO.getPendientes(alumnoEstudio).size();
        }
        return count;
    }

    public int getTotalEstadoActualizado() {
        Alumno alumno = usuarioService.getInfoUsuario();
        return documentoOficialDAO.getCountHistorialEstado(alumno.getId());
    }

    public List<Solicitud> getFinalizados(int idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        List<Solicitud> list = documentoOficialDAO.getFinalizados(alumnoEstudio);
        for (Solicitud solicitud : list) {
            solicitud.setIdRandom(usuarioService.addRefIndirecta(solicitud.getId()));
            
            if(solicitud.isVoucher()){
                String url = documentoOficialDAO.getVoucher(solicitud.getId());
                if(!url.isEmpty()){
                    solicitud.setUrlVoucher(
                            usuarioService.addRefIndirecta(url));
                }
            }
            solicitud.setId(0);
        }
        return list;
    }

    public List<TipoSolicitud> getTipoDocOficialesList(int idCampus) {
        List<Rol> rolList = Util.getRolesUsuario();
        return documentoOficialDAO.getTipoDocOficialesList(rolList, idCampus);
    }

    public int saveDocumentoOficial(Integer idTipoDocumento, Integer idCampus,
            Integer idIdioma, String detalle, Integer idEdicionestudio) {
        AlumnoEstudio alumnoEstudio = commonsService.getAlumnoEstudioSession(idEdicionestudio);
        Solicitud solicitud = new Solicitud();
        solicitud.setTipoSolicitud(new TipoSolicitud(idTipoDocumento));
        solicitud.setCampusEntrega(Campus.parse(idCampus));
        solicitud.setIdioma(Idioma.parse(idIdioma));
        solicitud.setObsAlumno(detalle);
        solicitud.setVoucher(false);
        solicitud.setAlumno(alumnoEstudio.getAlumno());
        solicitud.setEdicionestudio(alumnoEstudio.getEdicionestudio());
        return documentoOficialDAO.saveDocumentoOficial(solicitud);
    }

    public void saveVoucher(int idSolicitud, String pathFile) {
        documentoOficialDAO.saveVoucher(idSolicitud, pathFile);
    }

    public List<String> getResumen() {
        List<String> list = new ArrayList<String>();
        int dato;

        dato = this.getTotalPendientes();
        if (dato > 0) {
            list.add("<a href=\"#/docs/pendientes\">" + dato + " Documentos pendientes</a>");
        }
        dato = this.getTotalEstadoActualizado();
        if (dato > 0) {
            list.add("<a href=\"#/docs/pendientes\">Actualización de estado para un documento oficial</a>");
        }

        return list;
    }
}
