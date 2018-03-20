package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.PagoCuota;
import com.udep.siga.bean.PensionAlumno;
import java.util.List;

/**
 *
 * @author Administrador
 */
public interface PensionesDAO {
    public List<PagoCuota> getCronogramaPagosPensionesList
            (String carne, int idEdicionEstudio, int idCampus,int idPeriodoAcademico);
    public short getCsem(int idCampus, int idPeriodoAcademico);
    public String getCpacString(int idEdicionEstudio);
    public List<PensionAlumno> getPensionesListByAlumno(AlumnoEstudio alumnoEstudio);
    
}
