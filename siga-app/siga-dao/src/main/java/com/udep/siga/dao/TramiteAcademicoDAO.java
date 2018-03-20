package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Solicitud;
import com.udep.siga.bean.TipoSolicitud;
import com.udep.siga.bean.enumeration.CategoriaSolicitud;
import com.udep.siga.util.Rol;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface TramiteAcademicoDAO {
    
    public List<Solicitud> getPendientes(int idalumno, int idEdicionestudio);
    public List<Solicitud> getFinalizados(int idalumno, int idEdicionestudio);
    public List<TipoSolicitud> getTipoSolicitudesByCategoria(
            CategoriaSolicitud categoriaSolicitud, List<Rol> rolList);
    public void saveTramiteAcademico(Solicitud solicitud,AlumnoEstudio alumnoEstudio);    
    public int getTotalRespuestasNuevas(int idAlumno);
}
