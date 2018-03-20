package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.Solicitud;
import com.udep.siga.bean.TipoSolicitud;
import com.udep.siga.util.Rol;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface DocumentoOficialDAO {

    public List<Solicitud> getPendientes(AlumnoEstudio alumnoEstudio);

    public List<Solicitud> getFinalizados(AlumnoEstudio alumnoEstudio);

    public String getVoucher(int idSolicitud);

    public void saveVoucher(int idSolicitud, String pathFile);

    public String getSiglaTipoDocOficial(int idTipo);

    public Solicitud generarCodigos(Solicitud solicitud);

    public int saveDocumentoOficial(Solicitud solicitud);

    public List<TipoSolicitud> getTipoDocOficialesList(List<Rol> rolList, int idCampus);

    public int getCountHistorialEstado(int idAlumno);
}
