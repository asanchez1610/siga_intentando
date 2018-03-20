package com.udep.siga.dao;

import com.udep.siga.bean.AlumnoEstudio;
import com.udep.siga.bean.PeriodoAcademico;
import com.udep.siga.bean.enumeration.PeriodicidadPlanEstudio;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wilfredo Atoche
 */
public interface PeriodoAcademicoDAO {

    public PeriodoAcademico getPA(int id);

    public PeriodoAcademico getPAVigente(PeriodicidadPlanEstudio periodicidadPlanEstudio);

    public PeriodoAcademico getPAVigenteByAlumnoEstudio(AlumnoEstudio alumnoEstudio);

    public PeriodoAcademico getPeriodoAnterior(int idAlumno, int idEdicionEstudio, int numero);
    
    public PeriodoAcademico getPeriodoAnterior2(int idAlumno, int idEdicionEstudio, int numero, int ordenPeriodoActual);

    public String getBiPeriodoAnterior(int idAlumno, int idEdicionEstudio, int numero, int ordenPeriodoActual);

    public List<Map<String, Object>> getPeriodosAcademicosAlumnoList(AlumnoEstudio alumnoEstudio);

    public List<Map<String, Object>> getPeriodoAcademicoByCentroAcademicoList(int idCentroAcademico);
    
    public boolean anuloCicloByAlumnoEstudioPeriodoAcademico(int idAlumno, int idEstudio, int idPeriodoAcademico);
    
    public PeriodoAcademico getUltimoPAByAlumnoEstudioInactivo(AlumnoEstudio alumnoEstudio);    
}
